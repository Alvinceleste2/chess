package chess.pieces;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import chess.Board;
import chess.exceptions.InvalidMovementException;
import chess.exceptions.InvalidPositionException;
import chess.exceptions.InvalidPositionToAddPieceException;
import chess.utils.Color;
import chess.utils.Position;

public class King extends Piece {

  private boolean firstMoved;

  public King(Color color) {
    super(color);

    this.firstMoved = false;

    switch (color) {
      case WHITE:
        this.imagePath = "./../assets/ChessSet/Classic/Pieces/Chess-white-classic/King.png";
        break;
      default:
        this.imagePath = "./../assets/ChessSet/Classic/Pieces/Chess-black-classic/King.png";
        break;
    }
  }

  @Override
  public void move(Position position) throws InvalidMovementException {
    if (!this.checkCastling(position)) {
      System.err.println("Unable to castling");
      JOptionPane.showMessageDialog(null, "Unable to castling", "Unable to castling",
          JOptionPane.ERROR_MESSAGE);
      super.move(position);
    } else {
      System.err.println("Conditions to castling reached");
      try {
        this.castling(position);
      } catch (InvalidPositionToAddPieceException e) {
        System.err.println("Unable to castling");
      }
    }

    if (!this.firstMoved) {
      this.firstMoved = true;
    }
  }

  private boolean checkCastling(Position position) {
    // We check castling conditions
    Piece piece = Board.getInstance().getPieceAtSquare(position);

    if (!(piece instanceof Rook) || piece.color != this.color) {
      return false;
    }

    Rook r = (Rook) piece;
    double posX = Math.ceil((this.position.x + r.position.x) / 2);

    try {
      if (r.getFirstMoved() || this.firstMoved
          || this.isDangerousPosition(new Position((int) posX, this.position.y))) {
        return false;
      }

      for (int i = Math.min(this.position.x, r.position.x) + 1; i < Math.max(this.position.x, r.position.x); i++) {
        if (!Board.getInstance().getSquare(new Position(i, this.position.y)).isEmpty()) {
          return false;
        }
      }

    } catch (InvalidPositionException e) {
      System.err.println("Invalid position");
      return false;
    }

    if (this.isInCheck()) {
      return false;
    }

    return true;
  }

  public void castling(Position position) throws InvalidPositionToAddPieceException {
    Board board = Board.getInstance();

    try {
      board.addPiece(board.getSquare(position).replacePiece(this), new Position(3, 5));
    } catch (InvalidPositionException e) {

    }
  }

  public List<Position> calculateMovements() {
    List<Position> list = new ArrayList<>();

    list.addAll(this.getMovements());
    list.removeAll(this.getDanger());

    return list;
  }

  public List<Position> getMovements() {
    List<Position> list = new ArrayList<>();
    Board board = Board.getInstance();

    // First, we include every posible movement

    // U
    try {
      Position posU = new Position(this.position.x, this.position.y + 1);
      Piece piece = board.getPieceAtSquare(posU);

      if (piece == null || piece.color != this.color) {
        list.add(posU);
      }

    } catch (InvalidPositionException e) {
      System.out.println("Checking position out of bounds");
    }

    // UR
    try {
      Position posUR = new Position(this.position.x + 1, this.position.y + 1);
      Piece piece = board.getPieceAtSquare(posUR);

      if (piece == null || piece.color != this.color) {
        list.add(posUR);
      }

    } catch (InvalidPositionException e) {
      System.out.println("Checking position out of bounds");
    }

    // R
    try {
      Position posR = new Position(this.position.x + 1, this.position.y);
      Piece piece = board.getPieceAtSquare(posR);

      if (piece == null || piece.color != this.color) {
        list.add(posR);
      }

    } catch (InvalidPositionException e) {
      System.out.println("Checking position out of bounds");
    }

    // DR
    try {
      Position posDR = new Position(this.position.x + 1, this.position.y - 1);
      Piece piece = board.getPieceAtSquare(posDR);

      if (piece == null || piece.color != this.color) {
        list.add(posDR);
      }

    } catch (InvalidPositionException e) {
      System.out.println("Checking position out of bounds");
    }

    // D
    try {
      Position posD = new Position(this.position.x, this.position.y - 1);
      Piece piece = board.getPieceAtSquare(posD);

      if (piece == null || piece.color != this.color) {
        list.add(posD);
      }

    } catch (InvalidPositionException e) {
      System.out.println("Checking position out of bounds");
    }

    // DL
    try {
      Position posDL = new Position(this.position.x - 1, this.position.y - 1);
      Piece piece = board.getPieceAtSquare(posDL);

      if (piece == null || piece.color != this.color) {
        list.add(posDL);
      }

    } catch (InvalidPositionException e) {
      System.out.println("Checking position out of bounds");
    }

    // L
    try {
      Position posL = new Position(this.position.x - 1, this.position.y);
      Piece piece = board.getPieceAtSquare(posL);

      if (piece == null || piece.color != this.color) {
        list.add(posL);
      }

    } catch (InvalidPositionException e) {
      System.out.println("Checking position out of bounds");
    }

    // UL
    try {
      Position posUL = new Position(this.position.x - 1, this.position.y + 1);
      Piece piece = board.getPieceAtSquare(posUL);

      if (piece == null || piece.color != this.color) {
        list.add(posUL);
      }

    } catch (InvalidPositionException e) {
      System.out.println("Checking position out of bounds");
    }

    return list;
  }

  public List<Position> getDanger() {
    // The King can only move where there is no danger
    List<Position> posToErase = new ArrayList<>();

    for (Piece p : Board.getInstance().getPieces()) {
      if (p.color != this.color) {
        if (p instanceof King) {
          King k = (King) p;
          posToErase.addAll(k.getMovements());

        } else if (p instanceof Pawn) {
          Pawn pa = (Pawn) p;
          posToErase.addAll(pa.getAttack());

        } else {
          posToErase.addAll(p.calculateMovements());
        }
      }
    }

    return posToErase;
  }

  public boolean isDangerousPosition(Position position) {
    return this.getDanger().contains(position) ? true : false;
  }

  public boolean isInCheck() {
    for (Position p : this.getDanger()) {
      if (p.equals(this.position)) {
        return true;
      }
    }

    return false;
  }
}
