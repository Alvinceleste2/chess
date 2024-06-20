package chess.engine.pieces;

import java.util.List;

import chess.engine.boards.Board;
import chess.engine.common.GameStatus;
import chess.utils.Assets;
import chess.utils.Color;
import chess.utils.Position;

public abstract class Piece {
  protected boolean dead;
  protected List<Position> movements;
  protected Color color;
  protected String imagePath = Assets.piecesPath;
  protected Board board;

  public Piece(Color color) {
    this.color = color;
  }

  public void die() {
    this.dead = true;
    this.board.getPieces().remove(this);
  }

  public Position getPosition() {
    for (int i = 0; i < Board.getInstance().maxX; i++) {
      for (int j = 0; j < Board.getInstance().maxY; j++) {
        if (!this.board.getSquare(new Position(i, j)).isEmpty()
            && this.board.getSquare(new Position(i, j)).getPiece().equals(this)) {
          return new Position(i, j);
        }
      }
    }

    return null;
  }

  public String getImagePath() {
    return this.imagePath;
  }

  public GameStatus isValidMove(Position position) {
    if (!this.moveSet().contains(position)) {
      return GameStatus.ILLEGAL;
    } else {
      return (Board.getInstance().getPieceAtSquare(position) == null) ? GameStatus.NORMAL : GameStatus.CAPTURE;
    }
  }

  public abstract List<Position> moveSet();

  public abstract Piece clone();

  public void constraintsRefresh() {
  }

  public void setBoard(Board board) {
    this.board = board;
  }

  public Color getColor() {
    return this.color;
  }
}
