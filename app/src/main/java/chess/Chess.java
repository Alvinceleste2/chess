package chess;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import chess.engine.boards.Board;
import chess.exceptions.InvalidPositionToAddPieceException;
import chess.exceptions.InvalidThemeException;
import chess.ui.center.TablePanel;
import chess.utils.Assets;

public class Chess {
  public static void main(String[] args) throws InvalidPositionToAddPieceException {
    System.out.println("Hello, World");

    try {
      Assets.buildPaths("wooden2");
    } catch (InvalidThemeException e) {
      System.out.println(e);
      return;
    }

    Board board = Board.getInstance();
    board.init();

    SwingUtilities.invokeLater(new Runnable() {
      public void run() {

        JFrame mainFrame = new JFrame();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());

        mainFrame.add(mainPanel);

        Chess.add(mainPanel, new TablePanel(), 0, 0, 3, 3, 0);
        // Chess.add(mainPanel, new GameDataPanel(), 4, 0, 2, 3, 0);

        // mainFrame.add(new TablePanel(), BorderLayout.CENTER);
        // mainFrame.add(new ProfilePanel(), BorderLayout.NORTH);
        // mainFrame.add(new ProfilePanel(), BorderLayout.SOUTH);
        // mainFrame.add(new GameDataPanel(), BorderLayout.EAST);

        mainFrame.setSize(578, 578);
        mainFrame.setVisible(true);
      }
    });

  }

  public static void add(JPanel panel, JComponent comp, int x, int y, int width, int height, int inset) {
    /*
     * Gridbag constrains
     * gridx -> column of component
     * gridy -> row of component
     * gridwidth -> number of columns component spans
     * gridheight -> number of rows component spans
     * fill -> constant for remaining space
     * anchor -> determines where to place if no fill
     * Insets -> padding around a component
     */
    GridBagConstraints constr = new GridBagConstraints();
    constr.gridx = x;
    constr.gridy = y;
    constr.weightx = 1.0;
    constr.weighty = 1.0;
    constr.gridheight = height;
    constr.gridwidth = width;
    constr.insets = new Insets(inset, inset, inset, inset);
    constr.anchor = GridBagConstraints.CENTER;
    constr.fill = GridBagConstraints.BOTH;
    constr.ipadx = 15;
    constr.ipady = 15;
    panel.add(comp, constr);
  }
}
