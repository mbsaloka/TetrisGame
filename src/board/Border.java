package board;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Border {
	private BufferedImage borderImage;

	public Border(String imagePath) {
		try {
			borderImage = ImageIO.read(getClass().getResource(imagePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void draw(Graphics2D g2) {
		if (borderImage != null) {
			int borderWidth = borderImage.getWidth();
			int borderHeight = borderImage.getHeight();
			int x = Board.left_x - (borderWidth - Board.WIDTH) / 2;
			int y = Board.top_y - (borderHeight - Board.HEIGHT) / 2;

			g2.drawImage(borderImage, x, y, borderWidth, borderHeight, null);
		}
	}

}
