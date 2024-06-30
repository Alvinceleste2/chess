package chess.ui.menu;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;

import chess.utils.Assets;

public class GameSettingsPanel extends JPanel {

  private JComboBox<String> themeBox;

  public GameSettingsPanel() {
    this.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.BOTH;

    this.setBorder(new CompoundBorder(
        BorderFactory.createEmptyBorder(0, 0, 0, 0), BorderFactory.createTitledBorder("GameSettings")));

    themeBox = new JComboBox<>(Assets.strings);

    this.add(themeBox, gbc);
  }
}
