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

  public Player(Color color, King king) {
    this.eaten = new ArrayList<>();
    this.color = color;
    this.king = king;
  }

  public Color getColor() {
    return this.color;
  }

  public King getKing() {
    return this.king;
  }
}
