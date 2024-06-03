package chess.engine.pieces;

import java.util.ArrayList;
import java.util.List;

import chess.engine.boards.Board;
import chess.exceptions.InvalidMovementException;
import chess.exceptions.InvalidPositionException;
import chess.utils.Color;
import chess.utils.Position;

public class Rook extends Piece {

  private boolean firstMoved;

  public Rook(Color color, boolean firstMoved) {
    super(color);
    this.firstMoved = false;
    this.imagePath += this.color + "/Rook.png";
  }

  public Rook(Color color) {
    this(color, false);
  }

  @Override
  public void move(Position position) throws InvalidMovementException {
    super.move(position);

    if (!this.firstMoved) {
      this.firstMoved = true;
    }
  }

  public boolean getFirstMoved() {
    return this.firstMoved;
  }

  @Override
  public List<Position> calculateMovements() {
    List<Position> list = new ArrayList<>();
    Board board = Board.getInstance();

    // Check U
    try {
      for (int i = 1;; i++) {
        Position posU = new Position(this.position.x, this.position.y + i);
        Piece piece = board.getPieceAtSquare(posU);

        if (piece == null) {
          list.add(posU);
          continue;
        }

        if (piece.color != this.color) {
          list.add(posU);
        }

        break;
      }
    } catch (InvalidPositionException e) {
      System.out.println("Checking position out of bounds");
    }

    // Check R
    try {
      for (int i = 1;; i++) {
        Position posR = new Position(this.position.x + i, this.position.y);
        Piece piece = board.getPieceAtSquare(posR);

        if (piece == null) {
          list.add(posR);
          continue;
        }

        if (piece.color != this.color) {
          list.add(posR);
        }

        break;
      }
    } catch (InvalidPositionException e) {
      System.out.println("Checking position out of bounds");
    }

    // Check D
    try {
      for (int i = 1;; i++) {
        Position posD = new Position(this.position.x, this.position.y - i);
        Piece piece = board.getPieceAtSquare(posD);

        if (piece == null) {
          list.add(posD);
          continue;
        }

        if (piece.color != this.color) {
          list.add(posD);
        }

        break;
      }
    } catch (InvalidPositionException e) {
      System.out.println("Checking position out of bounds");
    }

    // Check L
    try {
      for (int i = 1;; i++) {
        Position posL = new Position(this.position.x - i, this.position.y);
        Piece piece = board.getPieceAtSquare(posL);

        if (piece == null) {
          list.add(posL);
          continue;
        }

        if (piece.color != this.color) {
          list.add(posL);
        }

        break;
      }
    } catch (InvalidPositionException e) {
      System.out.println("Checking position out of bounds");
    }

    return list;
  }

  public Rook clone() {
    return new Rook(this.color, this.firstMoved);
  }
}
