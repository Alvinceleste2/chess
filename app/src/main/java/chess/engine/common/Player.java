package chess.engine.common;

import java.util.ArrayList;
import java.util.List;

import chess.engine.pieces.King;
import chess.engine.pieces.Piece;
import chess.utils.Color;

public class Player {
  private Color color;
  private List<Piece> eaten;
  private King king;
  private Timer timer;

  public Player(Color color, King king, Timer timer) {
    this.eaten = new ArrayList<>();
    this.color = color;
    this.king = king;
    king.setPlayer(this);
    this.timer = timer;
  }

  public Color getColor() {
    return this.color;
  }

  public King getKing() {
    return this.king;
  }

  public Timer getTimer() {
    return this.timer;
  }

  @Override
  public String toString() {
    return this.color + " player";
  }
}
