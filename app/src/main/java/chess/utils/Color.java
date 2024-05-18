package chess.utils;

public enum Color {
  WHITE,
  BLACK;

  public int add(int num, int count) {
    switch (this) {
      case WHITE:
        return num + count;
      default:
        return num - count;
    }
  }
}
