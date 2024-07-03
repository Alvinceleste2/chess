package chess.engine.pieces;

import java.util.ArrayList;
import java.util.List;

import chess.engine.boards.Board;
import chess.utils.Assets;
import chess.utils.Color;
import chess.utils.Position;

public class Bishop extends Piece {
  public Bishop(Color color) {
    super(color);
    this.imagePath += this.color + "/Bishop.png";
  }

  protected void buildDeathPath() {
    this.imagePath = Assets.deathPath + "/Bishop.png";
  }

  @Override
  public List<Position> moveSet() {
    List<Position> list = new ArrayList<>();
    Board board = this.board;

    // UR branch
    for (int i = 1;; i++) {
      Position posUR = new Position(this.getPosition().x + i, this.getPosition().y + i);

      if (!posUR.isValidPosition()) {
        break;
      }

      Piece piece = board.getPieceAtSquare(posUR);

      if (piece == null) {
        list.add(posUR);
        continue;
      }

      if (!piece.color.equals(this.color)) {
        list.add(posUR);
      }

      break;
    }

    // DR branch
    for (int i = 1;; i++) {
      Position posDR = new Position(this.getPosition().x + i, this.getPosition().y - i);

      if (!posDR.isValidPosition()) {
        break;
      }

      Piece piece = board.getPieceAtSquare(posDR);

      if (piece == null) {
        list.add(posDR);
        continue;
      }

      if (!piece.color.equals(this.color)) {
        list.add(posDR);
      }

      break;
    }

    // DL branch
    for (int i = 1;; i++) {
      Position posDL = new Position(this.getPosition().x - i, this.getPosition().y - i);

      if (!posDL.isValidPosition()) {
        break;
      }

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

    // UL branch
    for (int i = 1;; i++) {
      Position posUL = new Position(this.getPosition().x - i, this.getPosition().y + i);

      if (!posUL.isValidPosition()) {
        break;
      }

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

    return list;
  }

  @Override
  public Bishop clone() {
    return new Bishop(this.color);
  }

  @Override
  public String toString() {
    return "B";
  }
}
