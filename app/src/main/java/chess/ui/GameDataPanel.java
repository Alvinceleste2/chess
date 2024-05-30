package chess.ui;

import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import chess.ui.components.TitlePanel;

public class GameDataPanel extends JPanel {
  public GameDataPanel() {

    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

    this.add(new TitlePanel("chess", new Font("Comic Sans", Font.BOLD, 24)));
  }
}
