package chess.engine.common;

import chess.engine.pieces.Piece;
import chess.utils.Position;

public class Square {
  private Piece piece;
  private Position position;

  public Square(Piece piece, Position position) {
    this.piece = piece;
    this.position = position;
  }

  public Piece getPiece() {
    return this.piece;
  }

  public void setEmpty() {
    this.piece = null;
  }

  // TODO change every piece == null to isEmpty()

  public boolean isEmpty() {
    return this.piece == null;
  }

  public void setPiece(Piece newPiece) {
    if (this.piece != null) {
      this.piece.die();
    }

    this.piece = newPiece;
  }
}
