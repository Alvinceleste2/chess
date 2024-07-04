package chess.engine.pieces;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import chess.engine.boards.Board;
import chess.engine.common.GameStatus;
import chess.engine.common.Player;
import chess.ui.table.TablePanel;
import chess.utils.Assets;
import chess.utils.Color;
import chess.utils.Position;

public class King extends Piece {

  private boolean firstMoved;
  private Player player;

  public King(Color color, boolean firstMoved) {
    super(color);
    this.firstMoved = firstMoved;
    this.imagePath += this.color + "/King.png";
  }

  public King(Color color) {
    this(color, false);
  }

  protected void buildDeathPath() {
    this.imagePath = Assets.deathPath + "/King.png";
  }

  public void setPlayer(Player player) {
    this.player = player;
  }

  public void die() {
    super.die();
    this.buildDeathPath();
    for (Piece p : Board.getInstance().getPieces().get(this.color)) {
      if (!p.equals(this)) {
        p.softDie();
      }
    }

    Board.getInstance().killPlayer(this.player);
  }

  @Override
  public void constraintsRefresh() {
    this.firstMoved = true;
  }

  @Override
  public GameStatus isValidMove(Position position) {
    if (this.moveSet().contains(position)) {
      if (this.checkCastling(position)) {
        return GameStatus.CASTLING;
      }
      return (Board.getInstance().getPieceAtSquare(position) == null) ? GameStatus.NORMAL : GameStatus.CAPTURE;
    }
    return GameStatus.ILLEGAL;
  }

  private boolean checkCastling(Position position) {
    // We check castling conditions
    Piece piece = Board.getInstance().getPieceAtSquare(position);

    if (!(piece instanceof Rook) || piece.color != this.color) {
      return false;
    }

    Rook r = (Rook) piece;

    if (r.getFirstMoved() || this.firstMoved) {
      return false;
    }

    for (int i = Math.min(this.getPosition().x, r.getPosition().x) + 1; i < Math.max(this.getPosition().x,
        r.getPosition().x); i++) {
      if (!Board.getInstance().getSquare(new Position(i, this.getPosition().y)).isEmpty()) {
        return false;
      }
    }

    if (this.isInCheck()) {
      return false;
    }

    return true;
  }

  @Override
  public List<Position> moveSet() {
    List<Position> list = new ArrayList<>();

    list.addAll(this.getMovements());
    list.addAll(this.getCastling());

    return list;
  }

  public List<Position> getMovements() {
    List<Position> list = new ArrayList<>();
    Board board = this.board;
    Piece piece;

    // First, we include every posible movement

    // U
    Position posU = new Position(this.getPosition().x, this.getPosition().y + 1);

    if (posU.isValidPosition()) {
      piece = board.getPieceAtSquare(posU);
      if (piece == null || piece.color != this.color) {
        list.add(posU);
      }
    }

    // UR
    Position posUR = new Position(this.getPosition().x + 1, this.getPosition().y + 1);

    if (posUR.isValidPosition()) {
      piece = board.getPieceAtSquare(posUR);
      if (piece == null || piece.color != this.color) {
        list.add(posUR);
      }
    }

    // R
    Position posR = new Position(this.getPosition().x + 1, this.getPosition().y);

    if (posR.isValidPosition()) {
      piece = board.getPieceAtSquare(posR);
      if (piece == null || piece.color != this.color) {
        list.add(posR);
      }
    }

    // DR
    Position posDR = new Position(this.getPosition().x + 1, this.getPosition().y - 1);

    if (posDR.isValidPosition()) {
      piece = board.getPieceAtSquare(posDR);
      if (piece == null || piece.color != this.color) {
        list.add(posDR);
      }
    }

    // D
    Position posD = new Position(this.getPosition().x, this.getPosition().y - 1);

    if (posD.isValidPosition()) {
      piece = board.getPieceAtSquare(posD);
      if (piece == null || piece.color != this.color) {
        list.add(posD);
      }
    }

    // DL
    Position posDL = new Position(this.getPosition().x - 1, this.getPosition().y - 1);

    if (posDL.isValidPosition()) {
      piece = board.getPieceAtSquare(posDL);
      if (piece == null || piece.color != this.color) {
        list.add(posDL);
      }
    }

    // L
    Position posL = new Position(this.getPosition().x - 1, this.getPosition().y);

    if (posL.isValidPosition()) {
      piece = board.getPieceAtSquare(posL);
      if (piece == null || piece.color != this.color) {
        list.add(posL);
      }
    }

    // UL
    Position posUL = new Position(this.getPosition().x - 1, this.getPosition().y + 1);

    if (posUL.isValidPosition()) {
      piece = board.getPieceAtSquare(posUL);
      if (piece == null || piece.color != this.color) {
        list.add(posUL);
      }
    }

    return list;
  }

  public List<Position> getDanger() {
    // The King can only move where there is no danger
    List<Position> posToErase = new ArrayList<>();

    for (Map.Entry<Color, List<Piece>> entry : this.board.getPieces().entrySet()) {
      if (entry.getKey().equals(this.color)) {
        continue;
      } else {
        for (Piece p : entry.getValue()) {
          if (p.color != this.color) {
            if (p instanceof King) {
              King k = (King) p;
              posToErase.addAll(k.getMovements());

            } else if (p instanceof Pawn) {
              Pawn pa = (Pawn) p;
              posToErase.addAll(pa.getAttack());

            } else {
              posToErase.addAll(p.moveSet());
            }
          }
        }
      }
    }

    return posToErase;
  }

  public List<Position> getCastling() {
    List<Position> list = new ArrayList<>();

    // We check if castling is possible

    Position posI = new Position(0, this.getPosition().y),
        posD = new Position(Board.getInstance().maxX - 1, this.getPosition().y);

    if (checkCastling(posI)) {
      list.add(posI);
    }

    if (checkCastling(posD)) {
      list.add(posD);
    }

    return list;
  }

  public boolean isInCheck() {
    for (Position p : this.getDanger()) {
      if (p.equals(this.getPosition())) {
        return true;
      }
    }

    return false;
  }

  @Override
  public King clone() {
    return new King(this.color, this.firstMoved);
  }

  @Override
  public String toString() {
    return "K";
  }
}
