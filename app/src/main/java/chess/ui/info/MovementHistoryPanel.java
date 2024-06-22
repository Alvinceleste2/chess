package chess.ui.info;

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
    listModel = new DefaultListModel<>();
    listModel.addAll(Board.getInstance().getMovements());

    list = new JList<>(listModel);

    scroll = new JScrollPane(list);

    this.add(scroll);
  }

  public static void refreshMovements() {
    listModel.clear();
    listModel.addAll(Board.getInstance().getMovements());
  }
}
