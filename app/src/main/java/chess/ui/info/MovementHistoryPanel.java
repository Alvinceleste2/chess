package chess.ui.info;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import chess.engine.boards.Board;
import chess.engine.common.Movement;

public class MovementHistoryPanel extends JPanel {
  private static JScrollPane scroll;

  private static JList<Movement> list;

  private static DefaultListModel<Movement> listModel;

  public MovementHistoryPanel() {
    this.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();

    gbc.fill = GridBagConstraints.BOTH;
    gbc.weightx = 1.0;
    gbc.weighty = 1.0;
    gbc.insets = new Insets(10, 10, 10, 10);

    listModel = new DefaultListModel<>();
    listModel.addAll(Board.getInstance().getMovements());

    list = new JList<>(listModel);

    scroll = new JScrollPane(list);
    scroll.setPreferredSize(this.getSize());

    this.add(scroll, gbc);
  }

  public static void refreshMovements() {
    listModel.clear();
    listModel.addAll(Board.getInstance().getMovements());
  }
}
