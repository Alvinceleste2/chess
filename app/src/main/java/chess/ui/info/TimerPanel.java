package chess.ui.info;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;

import chess.engine.boards.Board;
import chess.engine.common.Timer;
import chess.utils.Color;

public class TimerPanel extends JPanel {
  public TimerPanel() {
    this.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.BOTH;
    gbc.weightx = 1.0;

    for (int i = 0; i < Board.getInstance().getPlayers().size(); i++) {
      gbc.gridy = i;

      this.add(new TimerBox(Board.getInstance().getPlayers().get(i).getTimer(),
          Board.getInstance().getPlayers().get(i).getColor()), gbc);
    }
  }

  public class TimerBox extends JPanel {
    JLabel timeLabel;
    Timer timer;

    TimerBox(Timer timer, Color color) {
      this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
      this.timer = timer;
      this.timer.setObserver(this);

      this.setBorder(new CompoundBorder(
          BorderFactory.createEmptyBorder(0, 0, 0, 0), BorderFactory.createTitledBorder(color.toString())));

      timeLabel = new JLabel(timer.toString());
      this.add(timeLabel);
    }

    public void update() {
      this.timeLabel.setText(this.timer.toString());
    }
  }
}
