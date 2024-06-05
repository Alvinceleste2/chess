package chess.engine.boards;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import chess.engine.common.Player;
import chess.engine.common.Square;
import chess.engine.pieces.Bishop;
import chess.engine.pieces.King;
import chess.engine.pieces.Knight;
import chess.engine.pieces.Pawn;
import chess.engine.pieces.Piece;
import chess.engine.pieces.Queen;
import chess.engine.pieces.Rook;
import chess.engine.requests.CastlingReq;
import chess.engine.requests.PromoteReq;
import chess.engine.requests.Request;
import chess.exceptions.CheckMateException;
import chess.exceptions.CheckNotResolvedException;
import chess.exceptions.IllegalMovementException;
import chess.utils.Assets;
import chess.utils.Color;
import chess.utils.Position;

public class Board {
  public static int maxX = 8, maxY = 8;
  private static Board boardInstance = null;
  public static String imagePath = Assets.boardPath;

  private Map<Color, King> kings;
  private Player whitePlayer, blackPlayer;
  private Player turn;

  private Square[][] squares;
  private Set<Piece> pieces;
  private Map<Color, List<Piece>> eaten;

  private Board() {
    // Initialization of lists and sets
    this.pieces = new HashSet<>();
    this.squares = new Square[maxX][maxY];
    this.eaten = new HashMap<>();
    this.eaten.put(Color.WHITE, new ArrayList<>());
    this.eaten.put(Color.BLACK, new ArrayList<>());
    this.kings = new HashMap<>();

    for (int i = 0; i < maxX; i++) {
      for (int j = 0; j < maxY; j++) {
        this.squares[i][j] = new Square(null);
      }
    }
  }

  public static Board getInstance() {
    if (boardInstance == null) {
      boardInstance = new Board();
    }
    return boardInstance;
  }

  public void init() {
    // Creation of the players and their kings

    King whiteKing = new King(Color.WHITE, this);
    King blackKing = new King(Color.BLACK, this);

    this.kings.put(whiteKing.getColor(), whiteKing);
    this.kings.put(blackKing.getColor(), blackKing);

    whitePlayer = new Player(Color.WHITE, whiteKing);
    blackPlayer = new Player(Color.BLACK, blackKing);

    this.turn = this.whitePlayer;

    // We add the pawns
    for (int i = 0; i < maxX; i++) {
      boardInstance.addPiece(new Pawn(Color.WHITE), new Position(i, 1));
    }
    for (int i = 0; i < maxY; i++) {
      boardInstance.addPiece(new Pawn(Color.BLACK), new Position(i, 6));
    }

    // And now the rest of Pieces

    // White
    boardInstance.addPiece(new Rook(Color.WHITE), new Position(0, 0));
    boardInstance.addPiece(new Rook(Color.WHITE), new Position(7, 0));

    boardInstance.addPiece(new Knight(Color.WHITE), new Position(1, 0));
    boardInstance.addPiece(new Knight(Color.WHITE), new Position(6, 0));

    boardInstance.addPiece(new Bishop(Color.WHITE), new Position(2, 0));
    boardInstance.addPiece(new Bishop(Color.WHITE), new Position(5, 0));

    boardInstance.addPiece(new Queen(Color.WHITE), new Position(3, 0));
    boardInstance.addPiece(this.kings.get(Color.WHITE), new Position(4, 0));

    // Black
    boardInstance.addPiece(new Rook(Color.BLACK), new Position(0, 7));
    boardInstance.addPiece(new Rook(Color.BLACK), new Position(7, 7));

    boardInstance.addPiece(new Knight(Color.BLACK), new Position(1, 7));
    boardInstance.addPiece(new Knight(Color.BLACK), new Position(6, 7));

    boardInstance.addPiece(new Bishop(Color.BLACK), new Position(2, 7));
    boardInstance.addPiece(new Bishop(Color.BLACK), new Position(5, 7));

    boardInstance.addPiece(new Queen(Color.BLACK), new Position(3, 7));
    boardInstance.addPiece(this.kings.get(Color.BLACK), new Position(4, 7));
  }

  public Piece getPieceAtSquare(Position position) {
    return this.squares[position.x][position.y].getPiece();
  }

  public Square getSquare(Position position) {
    return this.squares[position.x][position.y];
  }

  public void addPiece(Piece piece, Position position) {
    this.squares[position.x][position.y].setPiece(piece);
    this.pieces.add(piece);
  }

  public Set<Piece> getPieces() {
    return this.pieces;
  }

  public Map<Color, List<Piece>> getEaten() {
    return this.eaten;
  }

  public void delPiece(Piece piece) {
    this.getSquare(piece.getPosition()).setEmpty();
    this.pieces.remove(piece);
  }

  public Board clone() {
    Board cloneBoard = new Board();

    cloneBoard.turn = new Player(this.turn.getColor(), this.kings.get(this.turn.getColor()));

    for (int i = 0; i < Board.maxX; i++) {
      for (int j = 0; j < Board.maxY; j++) {
        Position pos = new Position(i, j);

        if (!this.getSquare(pos).isEmpty()) {
          Piece clonedPiece = this.getPieceAtSquare(pos).clone(cloneBoard);
          if (clonedPiece instanceof King) {
            cloneBoard.kings.put(clonedPiece.getColor(), (King) clonedPiece);
          }

          cloneBoard.addPiece(clonedPiece, pos);
          System.out.println(cloneBoard.getPieceAtSquare(pos));
        }
      }
    }

    return cloneBoard;
  }

  public boolean isInTurn(Piece piece) {
    return (piece.getColor().equals(this.turn.getColor())) ? true : false;
  }

  public boolean checkCheck() {
    return this.turn.getKing().isInCheck();
  }

  public void next() {
    this.turn = (this.turn.equals(whitePlayer)) ? this.blackPlayer : this.whitePlayer;
  }

  private boolean move(Position start, Position end, boolean simulation)
      throws IllegalMovementException, CheckNotResolvedException, CheckMateException {
    boolean capture = false;
    // Initial checks
    if (!simulation && (this.getSquare(start).isEmpty() || !this.isInTurn(this.getPieceAtSquare(start)))) {
      throw new IllegalMovementException();
    }

    try {
      // This throws CheckNotResolvedException if the movement ends in a check
      if (!simulation) {
        this.simulateMove(start, end);
      }

      // Check if the movement is legal. If true, catches the corresponding requests
      // for special movements
      if (!this.getPieceAtSquare(start).isValidMove(end)) {
        throw new IllegalMovementException();
      }

      capture = this.getSquare(end).isEmpty() ? false : true;

      this.getSquare(end).setPiece(this.getPieceAtSquare(start));
      this.getSquare(start).setEmpty();
      this.getPieceAtSquare(end).constraintsRefresh();

    } catch (CastlingReq cr) {
      this.castling(start, end);
    } catch (PromoteReq pr) {
      this.promote(start, end);
    } catch (Request r) {
      System.out.println("This item should never be reached");
    }

    if (!simulation) {
      this.next();
      this.checkMate();
    }

    return capture;
  }

  public boolean move(Position start, Position end)
      throws IllegalMovementException, CheckNotResolvedException, CheckMateException {
    return (this.move(start, end, false));
  }

  private void castling(Position kingPos, Position rookPos) {
    double posXK = Math.ceil(((double) kingPos.x + (double) rookPos.x) / 2);
    double posXR = (kingPos.x > this.getPieceAtSquare(rookPos).getPosition().x) ? posXK + 1 : posXK - 1;

    this.getPieceAtSquare(kingPos).constraintsRefresh();
    this.getPieceAtSquare(rookPos).constraintsRefresh();

    this.getSquare(new Position((int) posXK, kingPos.y)).setPiece(this.getPieceAtSquare(kingPos));
    this.getSquare(new Position((int) posXR, kingPos.y)).setPiece(this.getPieceAtSquare(rookPos));
    this.getSquare(kingPos).setEmpty();
    this.getSquare(rookPos).setEmpty();
  }

  private void promote(Position start, Position end) {
    this.getSquare(end).setPiece(new Queen(this.getPieceAtSquare(start).getColor()));
    this.delPiece(this.getPieceAtSquare(start));
  }

  private void simulateMove(Position start, Position end) throws CheckNotResolvedException {
    Board cloneBoard = this.clone();
    try {
      cloneBoard.move(start, end, true);
    } catch (CheckMateException | IllegalMovementException e) {
      System.out.println("This situation might not be possible");
    }

    if (cloneBoard.kings.get(cloneBoard.turn.getColor()).isInCheck()) {
      throw new CheckNotResolvedException(cloneBoard.turn.getKing());
    }
  }

  private void checkMate() throws CheckMateException {
    if (!this.turn.getKing().isInCheck()) {
      return;
    }

    for (Piece p : this.getPieces()) {
      if (p.getColor().equals(this.turn.getKing().getColor())) {
        for (Position pos : p.moveSet()) {
          try {
            this.simulateMove(p.getPosition(), pos);
            return;
          } catch (CheckNotResolvedException e) {
            continue;
          }
        }
      }
    }

    throw new CheckMateException();
  }

  public List<Position> getMovements(Position position) {
    Piece piece = this.getPieceAtSquare(position);
    List<Position> list = new ArrayList<>(piece.moveSet());
    List<Position> toErase = new ArrayList<>();

    for (Position pos : list) {
      try {
        simulateMove(position, pos);
      } catch (CheckNotResolvedException e) {
        toErase.add(pos);
      }
    }

    list.removeAll(toErase);

    return list;
  }
}
