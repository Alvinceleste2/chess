package chess.engine.pieces;

import java.util.List;

import chess.engine.boards.Board;
import chess.utils.Assets;
import chess.utils.Color;
import chess.utils.Position;

public abstract class Piece {
  protected boolean dead;
  protected List<Position> movements;
  protected Color color;
  protected String imagePath = Assets.piecesPath;
  protected Board board;

  public Piece(Color color, Board board) {
    this.color = color;
    this.board = board;
  }

  public Piece(Color color) {
    this(color, Board.getInstance());
  }

  public void die() {
    this.dead = true;
    this.board.getEaten().get(this.color).add(this);
    this.board.getPieces().remove(this);
  }

  public Position getPosition() {
    for (int i = 0; i < Board.maxX; i++) {
      for (int j = 0; j < Board.maxY; j++) {
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

  public int isValidMove(Position position) {
    if (!this.moveSet().contains(position)) {
      return Board.ILLEGAL;
    } else {
      return (Board.getInstance().getPieceAtSquare(position) == null) ? Board.NORMAL : Board.CAPTURE;
    }
  }

  public abstract List<Position> moveSet();

  public abstract Piece clone(Board board);

  public void constraintsRefresh() {
  }

  public Color getColor() {
    return this.color;
  }
}
