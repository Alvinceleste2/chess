package chess.utils;

import chess.Board;
import chess.exceptions.InvalidPositionException;

public class Position {
  public int x, y;

  public Position(int x, int y) throws InvalidPositionException {
    if (x < 0 || x >= Board.maxX || y < 0 || y >= Board.maxY) {
      throw new InvalidPositionException(x, y);
    }

    this.x = x;
    this.y = y;
  }

  @Override
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("(");
    stringBuilder.append(this.x);
    stringBuilder.append(", ");
    stringBuilder.append(this.y);
    stringBuilder.append(")");
    return stringBuilder.toString();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }

    Position object = (Position) obj;

    return (object.x == x && object.y == y);
  }
}
