package chess.ui.info;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ActionButtonsPanel extends JPanel {
  public ActionButtonsPanel() {
    this.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();

    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.weightx = 1.0;
    gbc.weighty = 1.0;

    gbc.gridx = 0;

    JButton button1 = new JButton();
    this.add(button1, gbc);

    gbc.gridx = 1;

    JButton button2 = new JButton();
    this.add(button2, gbc);

    gbc.gridx = 2;

    JButton button3 = new JButton();
    this.add(button3, gbc);

    gbc.gridx = 3;

    JButton button4 = new JButton();
    this.add(button4, gbc);
  }
}
