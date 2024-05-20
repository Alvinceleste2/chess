package chess.pieces;

import java.util.ArrayList;
import java.util.List;

import chess.Board;
import chess.exceptions.InvalidMovementException;
import chess.exceptions.InvalidPositionException;
import chess.exceptions.InvalidPositionToAddPieceException;
import chess.utils.Color;
import chess.utils.Position;

public class Pawn extends Piece {
  private boolean firstMoved = false;

  public Pawn(Color color) {
    super(color);
    switch (color) {
      case WHITE:
        this.imagePath = "./../assets/ChessSet/Classic/Pieces/Chess-white-classic/Pawn.png";
        break;
      default:
        this.imagePath = "./../assets/ChessSet/Classic/Pieces/Chess-black-classic/Pawn.png";
        break;
    }
  }

  @Override
  public void move(Position position) throws InvalidMovementException {
    super.move(position);

    if (this.checkPromote()) {
      try {
        this.promote();
      } catch (InvalidPositionToAddPieceException | InvalidPositionException e) {
        System.err.println("Unable to promote");
      }
    }

    if (!this.firstMoved) {
      this.firstMoved = true;
    }
  }

  @Override
  public List<Position> calculateMovements() {
    List<Position> list = new ArrayList<>();
    Board board = Board.getInstance();

    // Check forward movements

    try {
      Position posF = new Position(this.position.x, this.color.add(this.position.y, 1));
      if (board.getPieceAtSquare(posF) == null) {
        list.add(posF);

        if (this.firstMoved == false) {
          Position posFF = new Position(this.position.x, this.color.add(this.position.y, 2));
          if (board.getPieceAtSquare(posFF) == null) {
            list.add(posFF);
          }
        }
      }

    } catch (InvalidPositionException e) {
      System.out.println("Checking position out of bounds");
    }

    // Check diagonal movements

    try {
      Position posFL = new Position(this.color.add(this.position.x, -1), this.color.add(this.position.y, 1));
      if (board.getPieceAtSquare(posFL) != null && board.getPieceAtSquare(posFL).color != this.color) {
        list.add(posFL);
      }
    } catch (InvalidPositionException e) {
      System.out.println("Checking position out of bounds");
    }

    try {
      Position posFR = new Position(this.color.add(this.position.x, 1), this.color.add(this.position.y, 1));
      if (board.getPieceAtSquare(posFR) != null && board.getPieceAtSquare(posFR).color != this.color) {
        list.add(posFR);
      }
    } catch (InvalidPositionException e) {
      System.out.println("Checking position out of bounds");
    }

    return list;
  }

  public List<Position> getAttack() {
    List<Position> attackPos = new ArrayList<>();

    try {
      Position posFL = new Position(this.color.add(this.position.x, -1), this.color.add(this.position.y, 1));
      attackPos.add(posFL);

    } catch (InvalidPositionException e) {
      System.out.println("Checking position out of bounds");
    }

    try {
      Position posFR = new Position(this.color.add(this.position.x, 1), this.color.add(this.position.y, 1));
      attackPos.add(posFR);

    } catch (InvalidPositionException e) {
      System.out.println("Checking position out of bounds");
    }

    return attackPos;
  }

  private boolean checkPromote() {
    switch (this.color) {
      case Color.WHITE:
        return this.position.y == Board.maxY - 1;
      default:
        return this.position.y == 0;
    }
  }

  private void promote() throws InvalidPositionException, InvalidPositionToAddPieceException {
    Board.getInstance().getSquare(this.position).replacePiece(new Queen(this.color)).die();
  }
}
