package board;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

import main.GamePanel;

public class Background {
	private BufferedImage pattern;

	public Background(String path) {
		try {
			pattern = ImageIO.read(getClass().getResourceAsStream(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void draw(Graphics2D g2) {
		if (pattern == null) {
			return;
		}

		int patternWidth = pattern.getWidth();
		int patternHeight = pattern.getHeight();

		for (int y = 0; y < GamePanel.HEIGHT; y += patternHeight) {
			for (int x = 0; x < GamePanel.WIDTH; x += patternWidth) {
				g2.drawImage(pattern, x, y, null);
			}
		}
	}

}
