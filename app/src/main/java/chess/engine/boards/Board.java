package chess.engine.boards;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import chess.engine.common.GameStatus;
import chess.engine.common.Movement;
import chess.engine.common.Player;
import chess.engine.common.Square;
import chess.engine.common.TimerConstraints;
import chess.engine.pieces.Piece;
import chess.ui.table.TablePanel;
import chess.ui.table.TilesPanel;
import chess.utils.Position;
import chess.utils.Sound;

public abstract class Board {
  // Static variables
  private static Board boardInstance = null;

  // Game status
  protected GameStatus status;

  // Player variables
  protected List<Player> players;
  protected Player turn;
  protected List<Movement> movements;

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
    this.movements = new ArrayList<>();

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

  public abstract List<Position> getLegalMovements(Position position);

  // COMMON METHODS //

  public boolean isInTurn(Piece piece) {
    return (piece.getColor().equals(this.turn.getColor())) ? true : false;
  }

  public void killPlayer(Player player) {
    this.players.remove(player);

    if (this.players.size() < 2) {
      this.endGame(this.players.get(0));
    }
  }

  public void endGame(Player winner) {
    this.status = GameStatus.ENDED;
    Sound.playEnd();
    TilesPanel.setSelectedTile(null);
    TilesPanel.refresh();
    TablePanel.showVictoryWin(winner);
  }

  protected void next() {
    this.turn.getTimer().stop();
    this.turn = this.players.get((this.players.indexOf(this.turn) + 1) % this.players.size());
    this.turn.getTimer().resume();
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

  public GameStatus getStatus() {
    return this.status;
  }

  public List<Movement> getMovements() {
    return this.movements;
  }

  // FACTORY METHODS //

  public static ClassicBoard createClassicBoard() {
    boardInstance = new ClassicBoard();
    return (ClassicBoard) boardInstance;
  }

  public static ChaturajiBoard createChaturajiBoard() {
    boardInstance = new ChaturajiBoard();
    return (ChaturajiBoard) boardInstance;
  }
}
