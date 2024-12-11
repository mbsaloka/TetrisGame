package ui;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Title extends UIElement {
	private BufferedImage titleImage;

	public Title(String imagePath, int x, int y, int width, int height) {
		super(x, y, width, height);

		try {
			titleImage = ImageIO.read(getClass().getResource(imagePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void draw(Graphics2D g2) {
		if (titleImage != null) {
			g2.drawImage(titleImage, x, y, width, height, null);
		}
	}

}
