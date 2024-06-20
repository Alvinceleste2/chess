package chess.engine.boards;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import chess.engine.common.GameStatus;
import chess.engine.common.Player;
import chess.engine.common.Square;
import chess.engine.common.TimerConstraints;
import chess.engine.pieces.Piece;
import chess.utils.Position;

public abstract class Board {
  // Static variables
  private static Board boardInstance = null;

  // Game status
  protected GameStatus status;

  // Player variables
  protected List<Player> players;
  protected Player turn;

  // Physics variables
  public int maxX, maxY;
  protected Square[][] squares;
  protected Set<Piece> pieces;

  // Visuals
  protected String imagePath;

  protected Board() {
    // Initialization of lists and sets
    this.pieces = new HashSet<>();
    this.players = new ArrayList<>();

    this.status = GameStatus.NOT_STARTED;
  }

  // MAIN METHODS //

  public static Board getInstance() {
    return boardInstance;
  }

  public static void reset() {
    boardInstance = null;
  }

  // ABSTRACT METHODS //

  public abstract void init(TimerConstraints tc);

  public abstract GameStatus move(Position start, Position end);

  public abstract List<Position> getMovements(Position position);

  // COMMON METHODS //

  public boolean isInTurn(Piece piece) {
    return (piece.getColor().equals(this.turn.getColor())) ? true : false;
  }

  public void endGame() {
    this.status = GameStatus.ENDED;
  }

  // SETTERS AND GETTERS //

  public void addPiece(Piece piece, Position position) {
    this.squares[position.x][position.y].setPiece(piece);
    this.pieces.add(piece);
    piece.setBoard(this);
  }

  public void delPiece(Piece piece) {
    this.getSquare(piece.getPosition()).setEmpty();
    this.pieces.remove(piece);
  }

  public Piece getPieceAtSquare(Position position) {
    return this.squares[position.x][position.y].getPiece();
  }

  public Square getSquare(Position position) {
    return this.squares[position.x][position.y];
  }

  public Set<Piece> getPieces() {
    return this.pieces;
  }

  public Player getTurn() {
    return this.turn;
  }

  public List<Player> getPlayers() {
    return this.players;
  }

  // FACTORY METHODS //

  public static ClassicBoard createClassicBoard() {
    boardInstance = new ClassicBoard();
    return (ClassicBoard) boardInstance;
  }
}
