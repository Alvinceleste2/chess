package chess.pieces;

import java.util.ArrayList;
import java.util.List;

import chess.Board;
import chess.exceptions.InvalidMovementException;
import chess.exceptions.InvalidPositionException;
import chess.utils.Color;
import chess.utils.Position;

public class King extends Piece {
  public King(Color color) {
    super(color);
  }

  @Override
  public void move(Position position) throws InvalidMovementException {
    if (!this.calculateMovements().contains(position)) {
      throw new InvalidMovementException();
    }

    Board.getInstance().getSquare(this.position).changePiece(null);
    Board.getInstance().getSquare(position).changePiece(this);
    this.position = position;
  }

  @Override
  public List<Position> calculateMovements() {
    List<Position> list = new ArrayList<>();
    Board board = Board.getInstance();

    // First, we include every posible movement

    // TODO Castling

    // U
    try {
      list.add(new Position(this.position.x, this.position.y + 1));
    } catch (InvalidPositionException e) {
      System.out.println("Checking position out of bounds");
    }

    // UR
    try {
      list.add(new Position(this.position.x + 1, this.position.y + 1));
    } catch (InvalidPositionException e) {
      System.out.println("Checking position out of bounds");
    }

    // R
    try {
      list.add(new Position(this.position.x + 1, this.position.y));
    } catch (InvalidPositionException e) {
      System.out.println("Checking position out of bounds");
    }

    // DR
    try {
      list.add(new Position(this.position.x + 1, this.position.y - 1));
    } catch (InvalidPositionException e) {
      System.out.println("Checking position out of bounds");
    }

    // D
    try {
      list.add(new Position(this.position.x, this.position.y - 1));
    } catch (InvalidPositionException e) {
      System.out.println("Checking position out of bounds");
    }

    // DL
    try {
      list.add(new Position(this.position.x - 1, this.position.y - 1));
    } catch (InvalidPositionException e) {
      System.out.println("Checking position out of bounds");
    }

    // L
    try {
      list.add(new Position(this.position.x - 1, this.position.y));
    } catch (InvalidPositionException e) {
      System.out.println("Checking position out of bounds");
    }

    // UL
    try {
      list.add(new Position(this.position.x - 1, this.position.y + 1));
    } catch (InvalidPositionException e) {
      System.out.println("Checking position out of bounds");
    }

    // The King can only move where there is no danger

    List<Position> posToErase = new ArrayList<>();

    for (Piece p : board.getPieces()) {
      posToErase.addAll(p.calculateMovements());
    }

    list.removeAll(posToErase);

    return list;
  }
}
