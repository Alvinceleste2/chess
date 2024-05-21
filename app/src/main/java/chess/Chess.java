package chess;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

import chess.exceptions.InvalidMovementException;
import chess.exceptions.InvalidPositionException;
import chess.exceptions.InvalidPositionToAddPieceException;
import chess.table.Board;
import chess.ui.view.GameDataPanel;
import chess.ui.view.ProfilePanel;
import chess.ui.view.TablePanel;
import chess.utils.Position;

public class Chess {
  public static void main(String[] args)
      throws InvalidPositionException, InvalidPositionToAddPieceException, InvalidMovementException {
    System.out.println("Hello, World");

    Board board = Board.getInstance();
    board.boardInit();

    JFrame mainFrame = new JFrame();
    mainFrame.setLayout(new BorderLayout());
    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    mainFrame.setResizable(false);

    TablePanel tablePanel = new TablePanel();
    mainFrame.add(tablePanel, BorderLayout.CENTER);
    mainFrame.add(new ProfilePanel(), BorderLayout.NORTH);
    mainFrame.add(new ProfilePanel(), BorderLayout.SOUTH);
    mainFrame.add(new GameDataPanel(), BorderLayout.EAST);

    mainFrame.setSize(new Dimension(576, 576));
    mainFrame.setVisible(true);

    try {
      Thread.sleep(5000);
    } catch (Exception e) {

    }

    board.getPieceAtSquare(new Position(3, 1)).move(new Position(3, 3));
    TablePanel.refresh();
  }
}
