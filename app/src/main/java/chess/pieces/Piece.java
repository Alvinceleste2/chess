package chess.pieces;

import java.util.List;

import chess.exceptions.InvalidMovementException;
import chess.utils.Color;
import chess.utils.Position;

public abstract class Piece {
  protected Position position;
  protected boolean dead;
  protected List<Position> movements;
  protected Color color;

  public Piece(Color color) {
    this.color = color;
  }

  public void kill() {
    this.dead = true;
    this.position = null;
  }

  public void setPosition(Position position) {
    this.position = position;
  }

  public abstract void move(Position position) throws InvalidMovementException;

  public abstract List<Position> calculateMovements();
}
