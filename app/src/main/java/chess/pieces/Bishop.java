package chess.pieces;

import java.util.ArrayList;
import java.util.List;

import chess.Board;
import chess.exceptions.InvalidMovementException;
import chess.exceptions.InvalidPositionException;
import chess.utils.Color;
import chess.utils.Position;

public class Bishop extends Piece {
  public Bishop(Color color) {
    super(color);
    this.imagePath = "./../assets/ChessSet/Classic/Pieces/Chess-white-classic/Bishop.png";
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

    // UR branch
    try {
      for (int i = 0;; i++) {
        Position posUR = new Position(this.position.x + i, this.position.y + i);
        Piece piece = board.getPieceAtSquare(posUR);

        if (piece == null) {
          list.add(posUR);
          continue;
        }

        if (piece.color != this.color) {
          list.add(posUR);
        }

        break;
      }
    } catch (InvalidPositionException e) {
      System.out.println("Checking position out of bounds");
    }

    // DR branch
    try {
      for (int i = 0;; i++) {
        Position posDR = new Position(this.position.x + i, this.position.y - i);
        Piece piece = board.getPieceAtSquare(posDR);

        if (piece == null) {
          list.add(posDR);
          continue;
        }

        if (piece.color != this.color) {
          list.add(posDR);
        }

        break;
      }
    } catch (InvalidPositionException e) {
      System.out.println("Checking position out of bounds");
    }

    // DL branch
    try {
      for (int i = 0;; i++) {
        Position posDL = new Position(this.position.x - i, this.position.y - i);
        Piece piece = board.getPieceAtSquare(posDL);

        if (piece == null) {
          list.add(posDL);
          continue;
        }

        if (piece.color != this.color) {
          list.add(posDL);
        }

        break;
      }
    } catch (InvalidPositionException e) {
      System.out.println("Checking position out of bounds");
    }

    // UL branch
    try {
      for (int i = 0;; i++) {
        Position posUL = new Position(this.position.x - i, this.position.y + i);
        Piece piece = board.getPieceAtSquare(posUL);

        if (piece == null) {
          list.add(posUL);
          continue;
        }

        if (piece.color != this.color) {
          list.add(posUL);
        }

        break;
      }
    } catch (InvalidPositionException e) {
      System.out.println("Checking position out of bounds");
    }

    return list;
  }
}
