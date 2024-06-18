package chess.engine.pieces;

import java.util.ArrayList;
import java.util.List;

import chess.engine.boards.Board;
import chess.utils.Color;
import chess.utils.Position;

public class Knight extends Piece {
  public Knight(Color color, Board board) {
    super(color, board);
    this.imagePath += this.color + "/Knight.png";
  }

  public Knight(Color color) {
    this(color, Board.getInstance());
  }

  @Override
  public List<Position> moveSet() {
    List<Position> list = new ArrayList<>();
    Board board = Board.getInstance();
    Piece piece;

    // Check UUR
    Position posUUR = new Position(this.getPosition().x + 1, this.getPosition().y + 2);
    if (posUUR.isValidPosition()) {
      piece = board.getPieceAtSquare(posUUR);

      if (piece == null || !board.getPieceAtSquare(posUUR).getColor().equals(this.color)) {
        list.add(posUUR);
      }
    }

    // Check RRU
    Position posRRU = new Position(this.getPosition().x + 2, this.getPosition().y + 1);
    if (posRRU.isValidPosition()) {
      piece = board.getPieceAtSquare(posRRU);

      if (piece == null || !board.getPieceAtSquare(posRRU).getColor().equals(this.color)) {
        list.add(posRRU);
      }
    }

    // Check RRD
    Position posRRD = new Position(this.getPosition().x + 2, this.getPosition().y - 1);
    if (posRRD.isValidPosition()) {
      piece = board.getPieceAtSquare(posRRD);

      if (piece == null || !board.getPieceAtSquare(posRRD).getColor().equals(this.color)) {
        list.add(posRRD);
      }
    }

    // Check DDR
    Position posDDR = new Position(this.getPosition().x + 1, this.getPosition().y - 2);
    if (posDDR.isValidPosition()) {
      piece = board.getPieceAtSquare(posDDR);

      if (piece == null || !board.getPieceAtSquare(posDDR).getColor().equals(this.color)) {
        list.add(posDDR);
      }
    }

    // Check DDL
    Position posDDL = new Position(this.getPosition().x - 1, this.getPosition().y - 2);
    if (posDDL.isValidPosition()) {
      piece = board.getPieceAtSquare(posDDL);

      if (piece == null || !board.getPieceAtSquare(posDDL).getColor().equals(this.color)) {
        list.add(posDDL);
      }
    }

    // Check LLD
    Position posLLD = new Position(this.getPosition().x - 2, this.getPosition().y - 1);
    if (posLLD.isValidPosition()) {
      piece = board.getPieceAtSquare(posLLD);

      if (piece == null || !board.getPieceAtSquare(posLLD).getColor().equals(this.color)) {
        list.add(posLLD);
      }
    }

    // Check LLU
    Position posLLU = new Position(this.getPosition().x - 2, this.getPosition().y + 1);
    if (posLLU.isValidPosition()) {
      piece = board.getPieceAtSquare(posLLU);

      if (piece == null || !board.getPieceAtSquare(posLLU).getColor().equals(this.color)) {
        list.add(posLLU);
      }
    }

    // Check UUL
    Position posUUL = new Position(this.getPosition().x - 1, this.getPosition().y + 2);
    if (posUUL.isValidPosition()) {
      piece = board.getPieceAtSquare(posUUL);

      if (piece == null || !board.getPieceAtSquare(posUUL).getColor().equals(this.color)) {
        list.add(posUUL);
      }
    }

    return list;
  }

  @Override
  public Knight clone(Board board) {
    return new Knight(this.color, board);
  }
}
