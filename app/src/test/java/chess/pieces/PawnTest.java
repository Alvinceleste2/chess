package chess.pieces;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import chess.Board;
import chess.exceptions.InvalidMovementException;
import chess.exceptions.InvalidPositionException;
import chess.exceptions.InvalidPositionToAddPieceException;
import chess.utils.Color;
import chess.utils.Position;

public class PawnTest {
  @Test
  public void MovementTest()
      throws InvalidPositionException, InvalidPositionToAddPieceException, InvalidMovementException {
    Board board = Board.getInstance();

    Position p1, p2, p3, p4;
    Pawn pa1, pa2, pa3, pa4;

    p1 = new Position(0, 1);
    p2 = new Position(2, 1);
    p3 = new Position(3, 2);
    p4 = new Position(3, 1);

    pa1 = new Pawn(Color.WHITE);
    pa2 = new Pawn(Color.WHITE);
    pa3 = new Pawn(Color.BLACK);
    pa4 = new Pawn(Color.WHITE);

    board.addPiece(pa1, p1);
    board.addPiece(pa2, p2);
    board.addPiece(pa3, p3);
    board.addPiece(pa4, p4);

    System.out.println(pa1.calculateMovements());
    pa1.move(new Position(0, 2));
    System.out.println(pa1.position);
    assertTrue(board.getPieceAtSquare(new Position(0, 2)) == pa1);
    assertTrue(pa1.position.equals(new Position(0, 2)));

    System.out.println(pa4.calculateMovements());
    assertTrue(pa4.calculateMovements().isEmpty());

    assertTrue(pa2.calculateMovements().contains(pa3.position));

    pa3.move(pa2.position);
    assertTrue(board.getPieceAtSquare(new Position(2, 1)) == pa3);
    assertTrue(pa2.dead == true);
    assertTrue(pa3.position.equals(new Position(2, 1)));

    assertFalse(pa4.calculateMovements().isEmpty());
  }
}
