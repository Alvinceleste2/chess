package chess.ui.info;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

public class InfoPanel extends JPanel {
  public InfoPanel() {
    this.setLayout(new GridBagLayout());

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.BOTH;
    gbc.weightx = 1.0;
    gbc.weighty = 1.0;

    gbc.gridy = 0;

    this.add(new TitlePanel("Chess"), gbc);

    gbc.gridy = 2;
    this.add(new TimerPanel(), gbc);

    gbc.gridy = 4;
    this.add(new MovementHistoryPanel(), gbc);
  }
}
