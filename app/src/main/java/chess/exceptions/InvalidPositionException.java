package chess.exceptions;

import chess.Board;

public class InvalidPositionException extends Exception {
  public InvalidPositionException(int x, int y) {
    super("Invalid position selected:" + "x = " + x + "y = " + y + "max = (" + Board.maxX + ", " + Board.maxY
        + ")");
  }
}
