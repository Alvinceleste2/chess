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
    gbc.gridheight = 1;
    this.add(new TitlePanel("Chess"), gbc);

    gbc.gridy = 1;
    gbc.gridheight = 5;
    this.add(new TimerPanel(), gbc);

    gbc.gridy = 6;
    gbc.gridheight = 4;
    this.add(new MovementHistoryPanel(), gbc);

    gbc.gridy = 10;
    gbc.gridheight = 1;
    this.add(new ActionButtonsPanel(), gbc);
  }
}
