package ui;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.AlphaComposite;
import java.io.IOException;

import main.GamePanel;

public class Panel extends UIElement {
	private BufferedImage panelImage;

	public Panel(String imagePath, int x, int y, int width, int height) {
		super(x, y, width, height);

		try {
			panelImage = ImageIO.read(getClass().getResource(imagePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void draw(Graphics2D g2) {
    if (panelImage != null) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f)); // 50% transparansi
			g2.setColor(Color.BLACK);
			g2.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);

			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

			g2.drawImage(panelImage, x, y, width, height, null);
    }
	}

}
