package chess.exceptions;

public class IllegalMovementException extends Exception {
  public IllegalMovementException() {
    super("Tried to perform an illegal movement");
  }
}
