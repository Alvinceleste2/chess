package chess.exceptions;

public class InvalidMovementException extends Exception {
  public InvalidMovementException() {
    super("This movement is not a valid movement");
  }
}
