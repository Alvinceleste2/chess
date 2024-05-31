package chess.engine;

import chess.exceptions.InvalidMovementException;
import chess.exceptions.InvalidPositionException;
import chess.exceptions.InvalidPositionToAddPieceException;
import chess.table.Board;
import chess.table.pieces.King;
import chess.table.pieces.Piece;
import chess.utils.Color;
import chess.utils.Position;

public class GameEngine {
  private static GameEngine instance;
  private Board board;
  private Player whitePlayer, blackPlayer;
  private Player turn;

  private GameEngine() {
    this.board = new Board();
    whitePlayer = new Player(Color.WHITE, new King(Color.WHITE));
    blackPlayer = new Player(Color.BLACK, new King(Color.BLACK));
  }

  public static GameEngine getInstance() {
    if (instance == null) {
      instance = new GameEngine();
    }

    return instance;
  }

  public Board getBoard() {
    return this.board;
  }

  public void start() {
    turn = whitePlayer;
  }

  public void init() throws InvalidPositionException, InvalidPositionToAddPieceException {
    board.boardInit(this.whitePlayer.getKing(), this.blackPlayer.getKing());
  }

  public boolean checkTurn(Piece piece) {
    return (piece.getColor().equals(this.turn.getColor())) ? true : false;
  }

  public void next() {
    turn = turn.equals(whitePlayer) ? blackPlayer : whitePlayer;

    if (this.checkCheck() && this.checkMate()) {

    }
  }

  public boolean checkCheck() {
    return this.turn.getKing().isInCheck();
  }

  public boolean checkMate() {
    for (Piece p : this.board.getPieces()) {
      if (p.getColor().equals(this.turn.getColor())) {
        for (Position pos : p.calculateMovements()) {
          Board cloneBoard = this.board.clone();
          try {
            cloneBoard.getPieceAtSquare(Position.createPosition(p.getPosition().x, p.getPosition().y)).move(pos);

          } catch (InvalidMovementException e) {

          }
        }
      }

    }
  }
}
