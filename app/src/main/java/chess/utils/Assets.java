package chess.utils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import chess.exceptions.InvalidThemeException;

public abstract class Assets {
  public static String[] classicThemes = { "1Bit", "casual", "classic", "gameBoy", "iceFire", "matte", "purpleGreen",
      "redBlack", "redBlue", "wooden", "wooden2", "wooden3" };
  public static String[] staticThemes = { "chaturaji" };
  private static final Set<String> classicThemesSet = new HashSet<>(Arrays.asList(classicThemes));
  private static final Set<String> staticThemesSet = new HashSet<>(Arrays.asList(staticThemes));

  public static String piecesPath, boardPath;
  public static String blankPath = "./../assets/chessSet/blank.png";

  public static final int BOARD_SIZE = 288;
  public static final int ICON_SIZE = 32;

  public static void buildPaths(String theme) throws InvalidThemeException {
    if (!classicThemesSet.contains(theme) && !staticThemesSet.contains(theme)) {
      throw new InvalidThemeException(theme);
    }

    piecesPath = "./../assets/chessSet/" + theme + "/pieces/";
    boardPath = "./../assets/chessSet/" + theme + "/boards/default.png";
  }
}
