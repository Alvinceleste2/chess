package chess.engine;

import chess.exceptions.InvalidPositionException;
import chess.exceptions.InvalidPositionToAddPieceException;
import chess.table.Board;
import chess.table.pieces.King;
import chess.table.pieces.Piece;
import chess.utils.Color;

public class GameEngine {
  private static GameEngine instance;
  private Player whitePlayer, blackPlayer;
  private Player turn;
  private boolean check, mate;
  private Board board = Board.getInstance();

  private GameEngine() {
    whitePlayer = new Player(Color.WHITE, new King(Color.WHITE));
    blackPlayer = new Player(Color.BLACK, new King(Color.BLACK));
  }

  public static GameEngine getInstance() {
    if (instance == null) {
      instance = new GameEngine();
    }

    return instance;
  }

  public void start() {
    turn = whitePlayer;
  }

  public void init() throws InvalidPositionException, InvalidPositionToAddPieceException {
    this.board.boardInit(this.whitePlayer.getKing(), this.blackPlayer.getKing());
  }

  public boolean checkTurn(Piece piece) {
    return (piece.getColor().equals(this.turn.getColor())) ? true : false;
  }

  public void next() {

    turn = turn.equals(whitePlayer) ? blackPlayer : whitePlayer;
  }
}
