package chess.exceptions;

public class InvalidPieceSwitchException extends Exception {
  public InvalidPieceSwitchException() {
    super("unable to switch pieces");
  }
}
