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
  protected String imagePath;

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

  public Position getPosition() {
    return this.position;
  }

  public String getImagePath() {
    return this.imagePath;
  }

  public void move(Position position) throws InvalidMovementException {
    if (!this.calculateMovements().contains(position)) {
      throw new InvalidMovementException();
    }

    Board.getInstance().getSquare(this.position).setEmpty();
    Board.getInstance().getSquare(position).setPiece(this);
    this.position = position;
  }

  public abstract List<Position> calculateMovements();
}
