package chess.utils;

public enum Color {
  WHITE("white"),
  BLACK("black"),
  RED("red"),
  BLUE("blue"),
  YELLOW("yellow"),
  GREEN("green");

  private String string;

  private Color(String string) {
    this.string = string;
  }

  @Override
  public String toString() {
    return this.string;
  }
}
