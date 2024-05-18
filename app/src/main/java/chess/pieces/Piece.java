package chess.pieces;

import java.util.List;

import chess.Board;
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

  public void die() {
    this.dead = true;
    this.position = null;

    Board.getInstance().delPiece(this);
  }

  public void setPosition(Position position) {
    this.position = position;
  }

  public abstract void move(Position position) throws InvalidMovementException;

  public abstract List<Position> calculateMovements();
}
