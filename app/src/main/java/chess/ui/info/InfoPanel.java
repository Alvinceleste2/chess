package chess.ui.info;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import chess.ui.components.TitlePanel;

public class InfoPanel extends JPanel {
  public InfoPanel() {
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

    this.add(new TitlePanel("Chess"));
    this.add(new TimerPanel());
  }
}
