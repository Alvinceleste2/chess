package chess.engine;

import chess.table.pieces.Piece;
import chess.utils.Position;

public class Movement {
  private Piece piece;
  private Position startPos, endPos;
  private boolean capture, check;

  public Movement(Piece piece, Position startPos, Position endPos, boolean capture, boolean check) {
    this.piece = piece;
    this.startPos = startPos;
    this.endPos = endPos;
    this.capture = capture;
    this.check = check;
  }

  public String toString() {
    String res = this.endPos.toString();
    res += this.check ? "+" : "";

    return res;
  }
}
