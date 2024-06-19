package chess.engine.boards;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import chess.engine.common.Player;
import chess.engine.common.Square;
import chess.engine.common.Timer;
import chess.engine.pieces.Bishop;
import chess.engine.pieces.King;
import chess.engine.pieces.Knight;
import chess.engine.pieces.Pawn;
import chess.engine.pieces.Piece;
import chess.engine.pieces.Queen;
import chess.engine.pieces.Rook;
import chess.utils.Assets;
import chess.utils.Color;
import chess.utils.Position;

public class Board {
  public static int maxX = 8, maxY = 8;
  public static String imagePath = Assets.boardPath;
  private static Board boardInstance = null;

  private boolean playing = false;

  private Map<Color, King> kings;
  private Player whitePlayer, blackPlayer;
  private Player turn;

  private Square[][] squares;
  private Set<Piece> pieces;
  private Map<Color, List<Piece>> eaten;

  public static final int NORMAL = 0, CAPTURE = 1, CASTLING = 2, PROMOTION = 3, ILLEGAL = 4, CHECK = 5,
      CHECKMATE = 6, DRAW = 7, CHECK_NOT_RESOLVED = 8, NOT_PLAYING = 9;

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

    whitePlayer = new Player(Color.WHITE, whiteKing, new Timer(20, 0, 0));
    blackPlayer = new Player(Color.BLACK, blackKing, new Timer(20, 0, 0));

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

    whitePlayer.getTimer().resume();
    Thread whiteThread = new Thread(whitePlayer.getTimer());
    whiteThread.start();
    Thread blackThread = new Thread(blackPlayer.getTimer());
    blackThread.start();

    this.playing = true;
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

    cloneBoard.turn = new Player(this.turn.getColor(), this.kings.get(this.turn.getColor()), null);

    for (int i = 0; i < Board.maxX; i++) {
      for (int j = 0; j < Board.maxY; j++) {
        Position pos = new Position(i, j);

        if (!this.getSquare(pos).isEmpty()) {
          Piece clonedPiece = this.getPieceAtSquare(pos).clone(cloneBoard);
          if (clonedPiece instanceof King) {
            cloneBoard.kings.put(clonedPiece.getColor(), (King) clonedPiece);
          }

          cloneBoard.addPiece(clonedPiece, pos);
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
    this.turn.getTimer().stop();
    this.turn = (this.turn.equals(whitePlayer)) ? this.blackPlayer : this.whitePlayer;
    this.turn.getTimer().resume();
  }

  private int move(Position start, Position end, boolean simulation) {
    // Initial checks
    if (!this.playing) {
      return NOT_PLAYING;
    }

    if (!simulation && (this.getSquare(start).isEmpty() || !this.isInTurn(this.getPieceAtSquare(start)))) {
      return ILLEGAL;
    }

    int ret = this.getPieceAtSquare(start).isValidMove(end);

    if (ret == ILLEGAL) {
      return ILLEGAL;
    }

    // This throws CheckNotResolvedException if the movement ends in a check
    if (!simulation) {
      if (this.simulateMoveEndsInCheck(start, end)) {
        return CHECK_NOT_RESOLVED;
      }
    }

    // Check if the movement is legal. If true, catches the corresponding requests
    // for special movements
    switch (ret) {
      case NORMAL:
        this.performMove(start, end);
        break;

      case CAPTURE:
        this.performMove(start, end);
        break;

      case CASTLING:
        this.castling(start, end);
        break;

      case PROMOTION:
        this.promote(start, end);
        break;
    }

    if (!simulation) {
      int next = this.performNext();

      switch (next) {
        case NORMAL:
          break;

        default:
          ret = next;
      }
    }

    return ret;
  }

  public int move(Position start, Position end) {
    return (this.move(start, end, false));
  }

  private void performMove(Position start, Position end) {
    this.getSquare(end).setPiece(this.getPieceAtSquare(start));
    this.getSquare(start).setEmpty();
    this.getPieceAtSquare(end).constraintsRefresh();
  }

  private int performNext() {
    this.next();
    return this.checkMate();
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
    this.addPiece(new Queen(this.getPieceAtSquare(start).getColor()), end);
    this.delPiece(this.getPieceAtSquare(start));
  }

  private boolean simulateMoveEndsInCheck(Position start, Position end) {
    Board cloneBoard = this.clone();
    cloneBoard.move(start, end, true);

    return cloneBoard.kings.get(cloneBoard.turn.getColor()).isInCheck();
  }

  private int checkMate() {
    if (!this.turn.getKing().isInCheck()) {
      return NORMAL;
    }

    for (Piece p : this.getPieces()) {
      if (p.getColor().equals(this.turn.getKing().getColor())) {
        for (Position pos : p.moveSet()) {
          if (!this.simulateMoveEndsInCheck(p.getPosition(), pos)) {
            return CHECK;
          }
        }
      }
    }

    this.endGame();

    return CHECKMATE;
  }

  public List<Position> getMovements(Position position) {
    Piece piece = this.getPieceAtSquare(position);
    List<Position> list = new ArrayList<>(piece.moveSet());
    List<Position> toErase = new ArrayList<>();

    for (Position pos : list) {
      if (simulateMoveEndsInCheck(position, pos)) {
        toErase.add(pos);
      }
    }

    list.removeAll(toErase);

    return list;
  }

  public Player getTurn() {
    return this.turn;
  }

  public void endGame() {
    this.playing = false;
  }
}
