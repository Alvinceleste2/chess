package chess.engine.common;

import chess.engine.boards.Board;
import chess.engine.pieces.Piece;
import chess.utils.Position;

public class Movement {
  private Piece piece;
  private Position startPos, endPos;
  private GameStatus status;

  public Movement(Piece piece, Position startPos, Position endPos, GameStatus status) {
    this.piece = piece;
    this.startPos = startPos;
    this.endPos = endPos;
    this.status = status;
  }

  public String toString() {
    int num = Board.getInstance().getMovements().indexOf(this);

    String res = num + ". " + this.piece.toString();

    return res;
  }
}
