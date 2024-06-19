package chess.ui.info;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class TimerPanel extends JPanel {
  public TimerPanel() {
    this.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();

    JLabel whiteLabel = new JLabel("White");
    JLabel blackLabel = new JLabel("Black");

    this.add(whiteLabel);
    this.add(blackLabel);
  }

  private class TimerBox extends JPanel {
    private String name;

    TimerBox(String name) {
      this.name = name;
    }
  }
}
