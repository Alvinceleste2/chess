package chess;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

import chess.exceptions.InvalidPositionException;
import chess.exceptions.InvalidPositionToAddPieceException;
import chess.ui.view.TablePanel;

public class Chess {
  public static void main(String[] args) throws InvalidPositionException, InvalidPositionToAddPieceException {
    System.out.println("Hello, World");

    Board board = Board.getInstance();
    board.boardInit();

    JFrame mainFrame = new JFrame();
    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    mainFrame.setLayout(new BorderLayout());
    mainFrame.add(new TablePanel(), BorderLayout.CENTER);

    mainFrame.setSize(new Dimension(288, 288));
    mainFrame.setResizable(false);
    mainFrame.setVisible(true);
  }
}
