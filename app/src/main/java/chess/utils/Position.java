package chess.utils;

import chess.Board;
import chess.exceptions.InvalidPositionException;

public class Position {
  public int x, y;

  public Position(int x, int y) throws InvalidPositionException {
    if (x < 0 || x > Board.maxX || y < 0 || y > Board.maxY) {
      throw new InvalidPositionException(x, y);
    }

    this.x = x;
    this.y = y;
  }

}
