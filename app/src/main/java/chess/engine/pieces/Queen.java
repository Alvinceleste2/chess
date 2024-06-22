package chess.engine.pieces;

import java.util.ArrayList;
import java.util.List;

import chess.engine.boards.Board;
import chess.utils.Color;
import chess.utils.Position;

public class Queen extends Piece {
  public Queen(Color color) {
    super(color);
    this.imagePath += this.color + "/Queen.png";
  }

  @Override
  public List<Position> moveSet() {
    List<Position> list = new ArrayList<>();
    Board board = this.board;

    // First, the Bishop moves
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

    // Then, the Rook ones
    // Check U
    for (int i = 1;; i++) {
      Position posU = new Position(this.getPosition().x, this.getPosition().y + i);

      if (!posU.isValidPosition()) {
        break;
      }

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

    // Check R
    for (int i = 1;; i++) {
      Position posR = new Position(this.getPosition().x + i, this.getPosition().y);

      if (!posR.isValidPosition()) {
        break;
      }

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

    // Check D
    for (int i = 1;; i++) {
      Position posD = new Position(this.getPosition().x, this.getPosition().y - i);

      if (!posD.isValidPosition()) {
        break;
      }

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

    // Check L
    for (int i = 1;; i++) {
      Position posL = new Position(this.getPosition().x - i, this.getPosition().y);

      if (!posL.isValidPosition()) {
        break;
      }

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

    return list;
  }

  @Override
  public Queen clone() {
    return new Queen(this.color);
  }

  @Override
  public String toString() {
    return "Q";
  }
}
