package ui;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Button extends UIElement {
	private BufferedImage buttonImage;
	private BufferedImage buttonImageHover;

	public Button(String imagePath, int x, int y, int width, int height) {
		super(x, y, width, height);

		try {
			buttonImage = ImageIO.read(getClass().getResource(imagePath));
			buttonImageHover = ImageIO.read(getClass().getResource(imagePath.replace(".png", "_Hover.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void draw(Graphics2D g2) {
		if (buttonImage != null && buttonImageHover != null) {
			BufferedImage imageToDraw = isHovered ? buttonImageHover : buttonImage;
			g2.drawImage(imageToDraw, x, y, width, height, null);
		}
	}

}
