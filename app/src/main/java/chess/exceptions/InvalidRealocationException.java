package chess.exceptions;

public class InvalidRealocationException extends Exception {
  public InvalidRealocationException() {
    super("Unable to realocate the piece");
  }
}
