package chess;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import chess.engine.boards.Board;
import chess.exceptions.InvalidThemeException;
import chess.ui.info.InfoPanel;
import chess.ui.table.TablePanel;
import chess.utils.Assets;

public class Chess {
  public static void main(String[] args) {
    System.out.println("Hello, World");

    try {
      Assets.buildPaths("wooden2");
    } catch (InvalidThemeException e) {
      System.out.println(e);
      return;
    }

    Board board = Board.getInstance();
    board.init();

    SwingUtilities.invokeLater(() -> {
      JFrame mainFrame = new JFrame();
      mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      mainFrame.setLayout(new GridBagLayout());

      GridBagConstraints gbc = new GridBagConstraints();

      gbc.fill = GridBagConstraints.BOTH;
      gbc.gridx = 0;
      gbc.weightx = 0.65;
      gbc.weighty = 1.0;

      mainFrame.add(new TablePanel(), gbc);

      gbc.gridx = 1;
      gbc.weightx = 0.35;
      gbc.weighty = 1.0;

      mainFrame.add(new InfoPanel(), gbc);

      mainFrame.setSize(576, 576);
      mainFrame.setVisible(true);
    });
  }
}
