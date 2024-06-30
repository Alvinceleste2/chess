package chess.ui.menu;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MenuFrame extends JFrame {
  public MenuFrame() {
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setSize(500, 500);
    this.setResizable(false);

    this.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.BOTH;

    JCheckBox c1 = new JCheckBox("Offline", true);
    JCheckBox c2 = new JCheckBox("Online");

    gbc.gridx = 0;
    gbc.gridy = 0;
    this.add(c1, gbc);

    gbc.gridx = 1;
    gbc.gridy = 0;
    this.add(c2, gbc);

    gbc.gridx = 0;
    gbc.gridy = 1;

    this.add(new GameSettingsPanel(), gbc);
  }
}
