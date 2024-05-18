package chess;

public class Board {
  public static int maxX = 7, maxY = 7;

  private Square[][] squares;

  public Board() {
    for (int i = 0; i < 7; i++) {
      for (int j = 0; j < 7; j++) {
        this.squares[i][j] = new Square(null);
      }
    }
  }

  public void boardInit() {

  }
}
