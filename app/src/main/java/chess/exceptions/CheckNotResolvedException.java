package chess.exceptions;

import chess.engine.pieces.King;

public class CheckNotResolvedException extends Exception {
  private King king;

  public CheckNotResolvedException(King king) {
    super("Check to your king has not been resolved");
    this.king = king;
  }

  public King getKing() {
    return this.king;
  }
}
