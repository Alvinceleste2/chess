package chess.table;

import chess.table.pieces.Piece;

public class Square {
  private Piece piece;

  public Square(Piece piece) {
    this.piece = piece;
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
