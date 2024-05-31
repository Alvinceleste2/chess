package chess.table.pieces;

import java.util.List;

import chess.exceptions.InvalidMovementException;
import chess.table.Board;
import chess.utils.Assets;
import chess.utils.Color;
import chess.utils.Position;
import chess.utils.Sound;

public abstract class Piece {
  protected Position position;
  protected boolean dead;
  protected List<Position> movements;
  protected Color color;
  protected String imagePath = Assets.piecesPath;

  public Piece(Color color) {
    this.color = color;
  }

  public void die() {
    this.dead = true;
    Board.getInstance().delPiece(this);
    this.position = null;
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
      Sound.playIllegal();
      throw new InvalidMovementException();
    }

    Sound.playMove();
    Board.getInstance().getSquare(this.position).setEmpty();
    Board.getInstance().getSquare(position).setPiece(this);
    this.position = position;
  }

  public abstract List<Position> calculateMovements();

  public abstract Piece clone();

  public Color getColor() {
    return this.color;
  }
}
