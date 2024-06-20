package chess.ui.info;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import chess.engine.boards.Board;
import chess.engine.common.Timer;
import chess.utils.Color;

public class TimerPanel extends JPanel {
  public TimerPanel() {
    this.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();

    gbc.gridx = 0;
    gbc.weightx = 1.0;

    this.add(new TimerBox(Board.getInstance().getPlayers().get(0).getTimer(),
        Board.getInstance().getPlayers().get(0).getColor()), gbc);

    gbc.gridx = 1;
    gbc.weightx = 1.0;

    this.add(new TimerBox(Board.getInstance().getPlayers().get(1).getTimer(),
        Board.getInstance().getPlayers().get(1).getColor()), gbc);
  }

  public class TimerBox extends JPanel {
    JLabel nameLabel, timeLabel;
    Timer timer;

    TimerBox(Timer timer, Color color) {
      this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
      this.timer = timer;
      this.timer.setObserver(this);

      nameLabel = new JLabel(color.toString());
      this.add(nameLabel);

      timeLabel = new JLabel(timer.toString());
      this.add(timeLabel);
    }

    public void update() {
      this.timeLabel.setText(this.timer.toString());
    }
  }
}
