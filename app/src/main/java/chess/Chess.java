package chess;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import chess.engine.boards.Board;
import chess.engine.common.Timer;
import chess.exceptions.InvalidThemeException;
import chess.ui.center.TablePanel;
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

    Timer watch = new Timer(80, 3, 1);
    Thread thread = new Thread(watch);
    thread.start();

    try {
      Thread.sleep(4000);
    } catch (Exception e) {

    }

    watch.stop();

    try {
      Thread.sleep(4000);
    } catch (Exception e) {

    }

    watch.stop();

    Board board = Board.getInstance();
    board.init();

    SwingUtilities.invokeLater(new Runnable() {
      public void run() {

        JFrame mainFrame = new JFrame();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());

        mainFrame.add(new TablePanel());
        // Chess.add(mainPanel, new GameDataPanel(), 4, 0, 2, 3, 0);

        // mainFrame.add(new TablePanel(), BorderLayout.CENTER);
        // mainFrame.add(new ProfilePanel(), BorderLayout.NORTH);
        // mainFrame.add(new ProfilePanel(), BorderLayout.SOUTH);
        // mainFrame.add(new GameDataPanel(), BorderLayout.EAST);

        mainFrame.setSize(576, 576);
        mainFrame.setVisible(true);
      }
    });
  }

  private void initPanels() {
    GridBagLayout gridBagLayout = new GridBagLayout();
    GridBagConstraints gbc = new GridBagConstraints();
  }
}
