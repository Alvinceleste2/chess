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

    // Check UUR
    Position posUUR = new Position(this.getPosition().x + 1, this.getPosition().y + 2);
    if (posUUR.isValidPosition()) {
      list.add(posUUR);
    }

    // Check RRU
    Position posRRU = new Position(this.getPosition().x + 2, this.getPosition().y + 1);
    if (posRRU.isValidPosition()) {
      list.add(posRRU);
    }

    // Check RRD
    Position posRRD = new Position(this.getPosition().x + 2, this.getPosition().y - 1);
    if (posRRD.isValidPosition()) {
      list.add(posRRD);
    }

    // Check DDR
    Position posDDR = new Position(this.getPosition().x + 1, this.getPosition().y - 2);
    if (posDDR.isValidPosition()) {
      list.add(posDDR);
    }

    // Check DDL
    Position posDDL = new Position(this.getPosition().x - 1, this.getPosition().y - 2);
    if (posDDL.isValidPosition()) {
      list.add(posDDL);
    }

    // Check LLD
    Position posLLD = new Position(this.getPosition().x - 2, this.getPosition().y - 1);
    if (posLLD.isValidPosition()) {
      list.add(posLLD);
    }

    // Check LLU
    Position posLLU = new Position(this.getPosition().x - 2, this.getPosition().y + 1);
    if (posLLU.isValidPosition()) {
      list.add(posLLU);
    }

    // Check UUL
    Position posUUL = new Position(this.getPosition().x - 1, this.getPosition().y + 2);
    if (posUUL.isValidPosition()) {
      list.add(posUUL);
    }

    return list;
  }

  @Override
  public Knight clone(Board board) {
    return new Knight(this.color, board);
  }
}
