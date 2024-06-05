package chess.ui.components;

import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TitlePanel extends JPanel {
  public TitlePanel(String string, Font font) {
    this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

    JLabel title = new JLabel(string);
    title.setFont(font);

    this.add(Box.createHorizontalGlue());
    this.add(title);
    this.add(Box.createHorizontalGlue());
  }
}