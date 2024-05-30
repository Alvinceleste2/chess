package chess.utils;

public enum Color {
  WHITE("white"),
  BLACK("black");

  private String string;

  private Color(String string) {
    this.string = string;
  }

  @Override
  public String toString() {
    return this.string;
  }

  public int add(int num, int count) {
    switch (this) {
      case WHITE:
        return num + count;
      default:
        return num - count;
    }
  }
}
