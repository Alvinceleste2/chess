package chess.utils;

import chess.exceptions.InvalidPositionException;
import chess.table.Board;

public class Position {
  private final static String[] letters = { "a", "b", "c", "d", "e", "f", "g", "h" };
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
    return Position.letters[this.x] + Integer.toString(this.y + 1);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }

    Position object = (Position) obj;

    return (object.x == x && object.y == y);
  }

  public static Position createPosition(int x, int y) {
    try {
      Position pos = new Position(x, y);
      return pos;
    } catch (InvalidPositionException e) {
      return null;
    }
  }
}
