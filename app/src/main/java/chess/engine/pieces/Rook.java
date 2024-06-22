package chess.engine.pieces;

import java.util.ArrayList;
import java.util.List;

import chess.engine.boards.Board;
import chess.utils.Color;
import chess.utils.Position;

public class Rook extends Piece {

  private boolean firstMoved;

  public Rook(Color color, boolean firstMoved) {
    super(color);
    this.firstMoved = firstMoved;
    this.imagePath += this.color + "/Rook.png";
  }

  public Rook(Color color) {
    this(color, false);
  }

  @Override
  public void constraintsRefresh() {
    this.firstMoved = true;
  }

  public boolean getFirstMoved() {
    return this.firstMoved;
  }

  @Override
  public List<Position> moveSet() {
    List<Position> list = new ArrayList<>();
    Board board = this.board;

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
  public Rook clone() {
    return new Rook(this.color, this.firstMoved);
  }

  @Override
  public String toString() {
    return "R";
  }
}
