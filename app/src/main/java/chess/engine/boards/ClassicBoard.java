package chess.engine.boards;

import java.util.ArrayList;
import java.util.List;

import chess.engine.common.GameStatus;
import chess.engine.common.Player;
import chess.engine.common.Square;
import chess.engine.common.Timer;
import chess.engine.common.TimerConstraints;
import chess.engine.pieces.Bishop;
import chess.engine.pieces.King;
import chess.engine.pieces.Knight;
import chess.engine.pieces.Pawn;
import chess.engine.pieces.Piece;
import chess.engine.pieces.Queen;
import chess.engine.pieces.Rook;
import chess.utils.Color;
import chess.utils.Position;

public class ClassicBoard extends Board {
  protected ClassicBoard() {
    super();
    this.maxX = 8;
    this.maxY = 8;
    this.squares = new Square[maxX][maxY];

    for (int i = 0; i < this.maxX; i++) {
      for (int j = 0; j < this.maxY; j++) {
        this.squares[i][j] = new Square(null);
      }
    }
  }

  @Override
  public void init(TimerConstraints tc) {
    // Creation of the players and their kings
    this.initPlayers(tc);
    this.initPieces();
  }

  private void initPlayers(TimerConstraints tc) {
    // We create the Kings
    King whiteKing = new King(Color.WHITE);
    King blackKing = new King(Color.BLACK);

    this.players.add(new Player(Color.WHITE, whiteKing, new Timer(tc)));
    this.players.add(new Player(Color.BLACK, blackKing, new Timer(tc)));

    this.turn = this.players.get(0);

    this.players.get(0).getTimer().resume();
    Thread whiteThread = new Thread(this.players.get(0).getTimer());
    whiteThread.start();
    Thread blackThread = new Thread(this.players.get(1).getTimer());
    blackThread.start();
  }

  private void initPieces() {
    // We add the pawns
    for (int i = 0; i < maxX; i++) {
      this.addPiece(new Pawn(Color.WHITE), new Position(i, 1));
    }
    for (int i = 0; i < maxY; i++) {
      this.addPiece(new Pawn(Color.BLACK), new Position(i, 6));
    }

    // And now the rest of Pieces

    // White
    this.addPiece(new Rook(Color.WHITE), new Position(0, 0));
    this.addPiece(new Rook(Color.WHITE), new Position(7, 0));

    this.addPiece(new Knight(Color.WHITE), new Position(1, 0));
    this.addPiece(new Knight(Color.WHITE), new Position(6, 0));

    this.addPiece(new Bishop(Color.WHITE), new Position(2, 0));
    this.addPiece(new Bishop(Color.WHITE), new Position(5, 0));

    this.addPiece(new Queen(Color.WHITE), new Position(3, 0));
    this.addPiece(this.players.get(0).getKing(), new Position(4, 0));

    // Black
    this.addPiece(new Rook(Color.BLACK), new Position(0, 7));
    this.addPiece(new Rook(Color.BLACK), new Position(7, 7));

    this.addPiece(new Knight(Color.BLACK), new Position(1, 7));
    this.addPiece(new Knight(Color.BLACK), new Position(6, 7));

    this.addPiece(new Bishop(Color.BLACK), new Position(2, 7));
    this.addPiece(new Bishop(Color.BLACK), new Position(5, 7));

    this.addPiece(new Queen(Color.BLACK), new Position(3, 7));
    this.addPiece(this.players.get(1).getKing(), new Position(4, 7));
  }

  @Override
  public GameStatus move(Position start, Position end) {
    return (this.move(start, end, false));
  }

  private GameStatus move(Position start, Position end, boolean simulation) {
    // Initial checks
    if (this.status == GameStatus.CHECKMATE || this.status == GameStatus.DRAW) {
      return GameStatus.ENDED;
    }

    if (!simulation && (this.getSquare(start).isEmpty() || !this.isInTurn(this.getPieceAtSquare(start)))) {
      return GameStatus.ILLEGAL;
    }

    GameStatus ret = this.getPieceAtSquare(start).isValidMove(end);

    if (ret == GameStatus.ILLEGAL) {
      return GameStatus.ILLEGAL;
    }

    // This throws CheckNotResolvedException if the movement ends in a check
    if (!simulation) {
      if (this.simulateMoveEndsInCheck(start, end)) {
        return GameStatus.CHECK_NOT_RESOLVED;
      }
    }

    // Check if the movement is legal. If true, catches the corresponding requests
    // for special movements
    switch (ret) {
      case GameStatus.NORMAL:
        this.performMove(start, end);
        break;

      case GameStatus.CAPTURE:
        this.performMove(start, end);
        break;

      case GameStatus.CASTLING:
        this.castling(start, end);
        break;

      case GameStatus.PROMOTION:
        this.promote(start, end);
        break;

      default:
        break;
    }

    if (!simulation) {
      GameStatus next = this.performNext();

      switch (next) {
        case GameStatus.NORMAL:
          break;

        default:
          ret = next;
      }
    }

    return ret;
  }

  private void performMove(Position start, Position end) {
    this.getSquare(end).setPiece(this.getPieceAtSquare(start));
    this.getSquare(start).setEmpty();
    this.getPieceAtSquare(end).constraintsRefresh();
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

  private void next() {
    this.turn.getTimer().stop();
    this.turn = this.players.get((this.players.indexOf(this.turn) + 1) % this.players.size());
    this.turn.getTimer().resume();
  }

  private GameStatus performNext() {
    this.next();
    return this.checkMate();
  }

  @Override
  public ClassicBoard clone() {
    ClassicBoard cloneBoard = new ClassicBoard();

    cloneBoard.turn = new Player(this.turn.getColor(), this.turn.getKing(), null);

    for (int i = 0; i < this.maxX; i++) {
      for (int j = 0; j < this.maxY; j++) {
        Position pos = new Position(i, j);

        if (!this.getSquare(pos).isEmpty()) {
          cloneBoard.addPiece(this.getPieceAtSquare(pos).clone(), pos);
        }
      }
    }

    return cloneBoard;
  }

  private boolean simulateMoveEndsInCheck(Position start, Position end) {
    ClassicBoard cloneBoard = (ClassicBoard) this.clone();
    cloneBoard.move(start, end, true);

    return this.turn.getKing().isInCheck();
  }

  private GameStatus checkMate() {
    if (!this.turn.getKing().isInCheck()) {
      return GameStatus.NORMAL;
    }

    for (Piece p : this.getPieces()) {
      if (p.getColor().equals(this.turn.getKing().getColor())) {
        for (Position pos : p.moveSet()) {
          if (!this.simulateMoveEndsInCheck(p.getPosition(), pos)) {
            return GameStatus.CHECK;
          }
        }
      }
    }

    this.endGame();

    return GameStatus.CHECKMATE;
  }

  @Override
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
}
