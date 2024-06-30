package chess.utils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import chess.exceptions.InvalidThemeException;

public abstract class Assets {
  public static String[] strings = { "1Bit", "casual", "classic", "gameBoy", "iceFire", "matte", "purpleGreen",
      "redBlack", "redBlue", "wooden", "wooden2", "wooden3" };
  private static final Set<String> themes = new HashSet<>(Arrays.asList(strings));

  public static String piecesPath, boardPath;
  public static String blankPath = "./../assets/chessSet/blank.png";

  public static final int BOARD_SIZE = 288;
  public static final int ICON_SIZE = 32;

  public static void buildPaths(String theme) throws InvalidThemeException {
    if (!themes.contains(theme)) {
      throw new InvalidThemeException(theme);
    }

    piecesPath = "./../assets/chessSet/" + theme + "/pieces/";
    boardPath = "./../assets/chessSet/" + theme + "/boards/default.png";
  }
}
