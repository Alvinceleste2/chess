package chess;

public class Chess {
  private static Chess single_instance = null;

  private Chess() {
  }

  public Chess getInstance() {
    if (single_instance == null) {
      single_instance = new Chess();
    }

    return single_instance;
  }

  public static void main(String[] args) {
    System.out.println("Hello, World!");
  }
}
