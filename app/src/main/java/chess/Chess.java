package chess;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

import chess.exceptions.InvalidMovementException;
import chess.exceptions.InvalidPositionException;
import chess.exceptions.InvalidPositionToAddPieceException;
import chess.ui.view.TablePanel;
import chess.utils.Position;

public class Chess {
  public static void main(String[] args)
      throws InvalidPositionException, InvalidPositionToAddPieceException, InvalidMovementException {
    System.out.println("Hello, World");

    Board board = Board.getInstance();
    board.boardInit();

    JFrame mainFrame = new JFrame();
    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    mainFrame.setLayout(new BorderLayout());
    TablePanel tablePanel = new TablePanel();
    mainFrame.add(tablePanel, BorderLayout.CENTER);

    mainFrame.setSize(new Dimension(576, 576));
    mainFrame.setVisible(true);
    mainFrame.setLocationRelativeTo(null);

    board.getPieceAtSquare(new Position(3, 1)).move(new Position(3, 3));
    tablePanel.refresh();
  }
}
