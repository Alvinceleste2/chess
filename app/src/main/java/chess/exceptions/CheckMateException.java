package chess.exceptions;

public class CheckMateException extends Exception {
  public CheckMateException() {
    super("CheckMate!");
  }
}
