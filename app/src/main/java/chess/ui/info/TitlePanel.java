package chess.ui.info;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TitlePanel extends JPanel {
  public TitlePanel(String string) {
    this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

    JLabel title = new JLabel(string);

    this.add(Box.createHorizontalGlue());
    this.add(title);
    this.add(Box.createHorizontalGlue());
  }
}
