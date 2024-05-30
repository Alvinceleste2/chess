package chess.exceptions;

public class InvalidThemeException extends Exception {
  public InvalidThemeException(String theme) {
    super("The theme \"" + theme + "\" is not a valid theme");
  }
}
