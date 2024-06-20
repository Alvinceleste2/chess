package chess.utils;

import chess.engine.boards.Board;

public class Position {
  private final static String[] letters = { "a", "b", "c", "d", "e", "f", "g", "h" };
  public int x, y;

  public Position(int x, int y) {
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

  public boolean isValidPosition() {
    return (this.x >= Board.getInstance().maxX || this.x < 0 || this.y >= Board.getInstance().maxY || this.y < 0)
        ? false
        : true;
  }
}
