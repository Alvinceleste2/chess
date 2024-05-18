package chess.exceptions;

public class InvalidPositionToAddPieceException extends Exception {
  public InvalidPositionToAddPieceException() {
    super("The square where to add a new piece must be empty");
  }
}
