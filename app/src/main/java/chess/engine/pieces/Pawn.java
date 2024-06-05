package chess.engine.pieces;

import java.util.ArrayList;
import java.util.List;

import chess.engine.boards.Board;
import chess.engine.requests.PromoteReq;
import chess.utils.Color;
import chess.utils.Position;

public class Pawn extends Piece {
  private boolean firstMoved;

  public Pawn(Color color, boolean firstMoved, Board board) {
    super(color, board);
    this.firstMoved = firstMoved;
    this.imagePath += this.color + "/Pawn.png";
  }

  public Pawn(Color color, boolean firstMoved) {
    this(color, firstMoved, Board.getInstance());
  }

  public Pawn(Color color) {
    this(color, false, Board.getInstance());
  }

  @Override
  public void constraintsRefresh() {
    this.firstMoved = true;
  }

  @Override
  public boolean isValidMove(Position position) throws PromoteReq {
    if (this.moveSet().contains(position)) {
      if (this.checkPromote()) {
        throw new PromoteReq();
      }

      return true;
    }

    return false;
  }

  @Override
  public List<Position> moveSet() {
    List<Position> list = new ArrayList<>();
    Board board = this.board;

    // Check forward movements

    Position posF = new Position(this.getPosition().x, this.color.add(this.getPosition().y, 1));
    if (posF.isValidPosition() && board.getPieceAtSquare(posF) == null) {
      list.add(posF);

      if (this.firstMoved == false) {
        Position posFF = new Position(this.getPosition().x, this.color.add(this.getPosition().y, 2));
        if (posFF.isValidPosition() && board.getPieceAtSquare(posFF) == null) {
          list.add(posFF);
        }
      }
    }

    // Check diagonal movements

    Position posFL = new Position(this.color.add(this.getPosition().x, -1), this.color.add(this.getPosition().y, 1));
    if (posFL.isValidPosition() && board.getPieceAtSquare(posFL) != null
        && board.getPieceAtSquare(posFL).color != this.color) {
      list.add(posFL);
    }

    Position posFR = new Position(this.color.add(this.getPosition().x, 1), this.color.add(this.getPosition().y, 1));
    if (posFR.isValidPosition() && board.getPieceAtSquare(posFR) != null
        && board.getPieceAtSquare(posFR).color != this.color) {
      list.add(posFR);
    }

    return list;
  }

  public List<Position> getAttack() {
    List<Position> attackPos = new ArrayList<>();

    Position posFL = new Position(this.color.add(this.getPosition().x, -1),
        this.color.add(this.getPosition().y, 1));
    if (posFL.isValidPosition()) {
      attackPos.add(posFL);
    }

    Position posFR = new Position(this.color.add(this.getPosition().x, 1),
        this.color.add(this.getPosition().y, 1));
    if (posFR.isValidPosition()) {
      attackPos.add(posFR);
    }

    return attackPos;
  }

  private boolean checkPromote() {
    switch (this.color) {
      case Color.WHITE:
        return this.getPosition().y == Board.maxY - 2;
      default:
        return this.getPosition().y == 1;
    }
  }

  @Override
  public Pawn clone(Board board) {
    return new Pawn(this.color, this.firstMoved, board);
  }
}
