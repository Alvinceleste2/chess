package chess.ui;

import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import chess.engine.Movement;
import chess.table.Board;
import chess.ui.components.TitlePanel;

public class GameDataPanel extends JPanel {
  public GameDataPanel() {

    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

    this.add(new TitlePanel("chess", new Font("Comic Sans", Font.BOLD, 24)));

    JScrollPane scroll = new JScrollPane();
    DefaultListModel<Movement> items = new DefaultListModel<>();
    JList<Movement> itemList = new JList<>(items);

  }
}
