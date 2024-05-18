package chess.ui.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import jdk.jshell.SourceCodeAnalysis.Highlight;

public class Tile extends JPanel {
  private BufferedImage backgroundImage;
  private int x, y;

  public Tile(String imagePath, int x, int y) {
    try {
      backgroundImage = ImageIO.read(new File(imagePath));
    } catch (IOException e) {
      e.printStackTrace();
    }

    this.setOpaque(false);
    this.x = x;
    this.y = y;

    this.addComponentListener(new ComponentAdapter() {
      @Override
      public void componentResized(ComponentEvent e) {
        enforceAspectRatio();
      }
    });

    this.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        JPanel panel = (JPanel) e.getSource();
      }
    });
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    if (backgroundImage != null) {
      int panelWidth = getWidth();
      int panelHeight = getHeight();

      double scaleX = (double) panelWidth / backgroundImage.getWidth();
      double scaleY = (double) panelHeight / backgroundImage.getHeight();

      double scale = Math.max(scaleX, scaleY);

      int scaledWidth = (int) (scale * backgroundImage.getWidth());
      int scaledHeight = (int) (scale * backgroundImage.getHeight());

      int x = (panelWidth - scaledWidth) / 2;
      int y = (panelHeight - scaledHeight) / 2;

      g.drawImage(backgroundImage, x, y, scaledWidth, scaledHeight, this);
      this.enforceAspectRatio();
    }
  }

  private void enforceAspectRatio() {
    System.out.println(this.getParent());
    System.out.println(this);
    this.setSize(this.getParent().getWidth() / 9, this.getParent().getHeight() / 9);
  }
}
