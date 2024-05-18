package chess.pieces;

import chess.utils.Position;

public abstract class Piece {
  private Position pos;

  public Piece(Position pos) {
    this.pos = pos;
  }

  public abstract void move(Position pos);
}
