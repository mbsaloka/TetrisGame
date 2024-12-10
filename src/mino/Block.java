package mino;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import enums.MinoType;

public class Block {
	public int x, y;
	public static final int SIZE = 30;

	private BufferedImage image;
	private MinoType type;
	private static final Map<MinoType, BufferedImage> spriteCache = new HashMap<>();

	public Block(MinoType type) {
		this.type = type;
		this.image = loadSprite(type);
	}

	private BufferedImage loadSprite(MinoType type) {
		if (spriteCache.containsKey(type)) {
			return spriteCache.get(type);
		}

		String spritePath = "/Mino/mino_" + type.name() + ".png";
		try {
			BufferedImage sprite = ImageIO.read(getClass().getResource(spritePath));
			spriteCache.put(type, sprite);
			return sprite;
		} catch (IOException | NullPointerException e) {
			e.printStackTrace();
			throw new RuntimeException("Gagal memuat sprite untuk: " + type);
		}
	}

	public BufferedImage getImage() {
		return image;
	}

	public MinoType getType() {
		return type;
	}

	public void draw(Graphics2D g) {
		g.drawImage(image, x, y, SIZE, SIZE, null);
	}

}
