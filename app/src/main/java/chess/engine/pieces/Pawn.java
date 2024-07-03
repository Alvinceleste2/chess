package chess.engine.pieces;

import java.util.ArrayList;
import java.util.List;

import chess.engine.boards.Board;
import chess.engine.common.GameStatus;
import chess.utils.Assets;
import chess.utils.Color;
import chess.utils.Position;

public class Pawn extends Piece {
  private boolean firstMoved;

  public Pawn(Color color, boolean firstMoved) {
    super(color);
    this.firstMoved = firstMoved;
    this.imagePath += this.color + "/Pawn.png";
  }

  public Pawn(Color color) {
    this(color, false);
  }

  protected void buildDeathPath() {
    this.imagePath = Assets.deathPath + "/Pawn.png";
  }

  @Override
  public void constraintsRefresh() {
    this.firstMoved = true;
  }

  @Override
  public GameStatus isValidMove(Position position) {
    if (this.moveSet().contains(position)) {
      if (this.checkPromote()) {
        return GameStatus.PROMOTION;
      }
      return (Board.getInstance().getPieceAtSquare(position) == null) ? GameStatus.NORMAL : GameStatus.CAPTURE;
    }

    return GameStatus.ILLEGAL;
  }

  @Override
  public List<Position> moveSet() {
    List<Position> list = new ArrayList<>();
    Board board = this.board;

    // Check forward movements

    Position posF = this.forward(1);
    if (posF.isValidPosition() && board.getPieceAtSquare(posF) == null) {
      list.add(posF);

      if (this.firstMoved == false) {
        Position posFF = this.forward(2);
        if (posFF.isValidPosition() && board.getPieceAtSquare(posFF) == null) {
          list.add(posFF);
        }
      }
    }

    // Check diagonal movements

    Position posFL = this.forwardLeft();
    if (posFL.isValidPosition() && board.getPieceAtSquare(posFL) != null
        && board.getPieceAtSquare(posFL).color != this.color) {
      list.add(posFL);
    }

    Position posFR = this.forwardRight();
    if (posFR.isValidPosition() && board.getPieceAtSquare(posFR) != null
        && board.getPieceAtSquare(posFR).color != this.color) {
      list.add(posFR);
    }

    return list;
  }

  public List<Position> getAttack() {
    List<Position> attackPos = new ArrayList<>();

    Position posFL = this.forwardLeft();
    if (posFL.isValidPosition()) {
      attackPos.add(posFL);
    }

    Position posFR = this.forwardRight();
    if (posFR.isValidPosition()) {
      attackPos.add(posFR);
    }

    return attackPos;
  }

  private boolean checkPromote() {
    switch (this.getColor()) {
      case WHITE:
        return this.getPosition().y >= Board.getInstance().maxY - 2;

      case BLACK:
        return this.getPosition().y <= 1;

      case RED:
        return this.getPosition().y >= Board.getInstance().maxY - 2;

      case BLUE:
        return this.getPosition().x >= Board.getInstance().maxX - 2;

      case YELLOW:
        return this.getPosition().y <= 1;

      default:
        return this.getPosition().x <= 1;
    }
  }

  private Position forward(int num) {
    switch (this.color) {
      case WHITE:
        return new Position(this.getPosition().x, this.getPosition().y + num);
      case BLACK:
        return new Position(this.getPosition().x, this.getPosition().y - num);
      case RED:
        return new Position(this.getPosition().x, this.getPosition().y + num);
      case BLUE:
        return new Position(this.getPosition().x + num, this.getPosition().y);
      case YELLOW:
        return new Position(this.getPosition().x, this.getPosition().y - num);
      default:
        return new Position(this.getPosition().x - num, this.getPosition().y);
    }
  }

  private Position forwardLeft() {
    switch (this.color) {
      case WHITE:
        return new Position(this.getPosition().x - 1, this.getPosition().y + 1);
      case BLACK:
        return new Position(this.getPosition().x + 1, this.getPosition().y - 1);
      case RED:
        return new Position(this.getPosition().x - 1, this.getPosition().y + 1);
      case BLUE:
        return new Position(this.getPosition().x + 1, this.getPosition().y + 1);
      case YELLOW:
        return new Position(this.getPosition().x + 1, this.getPosition().y - 1);
      default:
        return new Position(this.getPosition().x - 1, this.getPosition().y - 1);
    }
  }

  private Position forwardRight() {
    switch (this.color) {
      case WHITE:
        return new Position(this.getPosition().x + 1, this.getPosition().y + 1);
      case BLACK:
        return new Position(this.getPosition().x - 1, this.getPosition().y - 1);
      case RED:
        return new Position(this.getPosition().x + 1, this.getPosition().y + 1);
      case BLUE:
        return new Position(this.getPosition().x + 1, this.getPosition().y - 1);
      case YELLOW:
        return new Position(this.getPosition().x - 1, this.getPosition().y - 1);
      default:
        return new Position(this.getPosition().x - 1, this.getPosition().y + 1);
    }
  }

  @Override
  public Pawn clone() {
    return new Pawn(this.color, this.firstMoved);
  }

  @Override
  public String toString() {
    return "";
  }
}
