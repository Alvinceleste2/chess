package chess;

import chess.pieces.Piece;

public class Square {
  private Piece piece;

  public Square(Piece piece) {
    this.piece = piece;
  }

  public Piece getPiece() {
    return this.piece;
  }

  public void changePiece(Piece newPiece) {
    if (this.piece != null) {
      this.piece.die();
    }

    this.piece = newPiece;
  }
}
