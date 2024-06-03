package chess.engine.pieces;

import java.util.ArrayList;
import java.util.List;

import chess.exceptions.InvalidPositionException;
import chess.utils.Color;
import chess.utils.Position;

public class Knight extends Piece {
  public Knight(Color color) {
    super(color);
    this.imagePath += this.color + "/Knight.png";
  }

  @Override
  public List<Position> calculateMovements() {
    List<Position> list = new ArrayList<>();

    // Check UUR
    try {
      Position posUUR = new Position(this.position.x + 1, this.position.y + 2);
      list.add(posUUR);
    } catch (InvalidPositionException e) {
      System.out.println("Checking position out of bounds");
    }

    // Check RRU
    try {
      Position posRRU = new Position(this.position.x + 2, this.position.y + 1);
      list.add(posRRU);
    } catch (InvalidPositionException e) {
      System.out.println("Checking position out of bounds");
    }

    // Check RRD
    try {
      Position posRRD = new Position(this.position.x + 2, this.position.y - 1);
      list.add(posRRD);
    } catch (InvalidPositionException e) {
      System.out.println("Checking position out of bounds");
    }

    // Check DDR
    try {
      Position posDDR = new Position(this.position.x + 1, this.position.y - 2);
      list.add(posDDR);
    } catch (InvalidPositionException e) {
      System.out.println("Checking position out of bounds");
    }

    // Check DDL
    try {
      Position posDDL = new Position(this.position.x - 1, this.position.y - 2);
      list.add(posDDL);
    } catch (InvalidPositionException e) {
      System.out.println("Checking position out of bounds");
    }

    // Check LLD
    try {
      Position posLLD = new Position(this.position.x - 2, this.position.y - 1);
      list.add(posLLD);
    } catch (InvalidPositionException e) {
      System.out.println("Checking position out of bounds");
    }

    // Check LLU
    try {
      Position posLLU = new Position(this.position.x - 2, this.position.y + 1);
      list.add(posLLU);
    } catch (InvalidPositionException e) {
      System.out.println("Checking position out of bounds");
    }

    // Check UUL
    try {
      Position posUUL = new Position(this.position.x - 1, this.position.y + 2);
      list.add(posUUL);
    } catch (InvalidPositionException e) {
      System.out.println("Checking position out of bounds");
    }

    return list;
  }

  public Knight clone() {
    return new Knight(this.color);
  }
}
