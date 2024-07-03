package chess;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import chess.engine.boards.Board;
import chess.engine.boards.ChaturajiBoard;
import chess.engine.common.TimerConstraints;
import chess.ui.info.InfoPanel;
import chess.ui.table.TablePanel;

public class Chess {
  public static void main(String[] args) {
    System.out.println("Hello, World");

    SwingUtilities.invokeLater(() -> {
      JFrame mainFrame = new JFrame();
      mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      mainFrame.setLayout(new GridBagLayout());

      ChaturajiBoard board = Board.createChaturajiBoard();
      board.init(new TimerConstraints(20, 0, 0));

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
