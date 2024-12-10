package board;

import java.awt.Graphics2D;
import java.awt.Color;
import java.util.ArrayList;

import mino.Block;

public class Board {
	public static final int WIDTH = 300;
	public static final int HEIGHT = 600;
	public static final int BLOCKS_PER_ROW = 10;

	public static int left_x;
	public static int right_x;
	public static int top_y;
	public static int bottom_y;

	public static ArrayList<Block> staticBlocks = new ArrayList<>();

	private boolean effectCounterOn = false;
	private int effectCounter = 0;
	private ArrayList<Integer> effectY = new ArrayList<>();

	public Board(int panelWidth) {
		left_x = (panelWidth / 2) - (WIDTH / 2);
		right_x = left_x + WIDTH;
		top_y = 50;
		bottom_y = top_y + HEIGHT;
	}

	public void checkDelete(int level, int lines, int dropInterval, int score) {
		int x = left_x;
		int y = top_y;
		int blockCount = 0;
		int lineCount = 0;

		while (x < right_x && y < bottom_y) {
			for (Block block : staticBlocks) {
				if (block.x == x && block.y == y) {
					blockCount++;
				}
			}

			x += Block.SIZE;

			if (x == right_x) {
				if (blockCount == BLOCKS_PER_ROW) {
					effectCounterOn = true;
					effectY.add(y);

					for (int i = staticBlocks.size() - 1; i >= 0; i--) {
						Block block = staticBlocks.get(i);
						if (block.y == y) {
							staticBlocks.remove(i);
						}
					}

					lineCount++;
					lines++;

					if (lines % 10 == 0 && dropInterval > 1) {
						level++;
						dropInterval = Math.max(dropInterval - 10, 1);
					}

					for (Block block : staticBlocks) {
						if (block.y < y) {
							block.y += Block.SIZE;
						}
					}
				}

				blockCount = 0;
				x = left_x;
				y += Block.SIZE;
			}
		}

		if (lineCount > 0) {
			int singleLineScore = 10 * level;
			score += singleLineScore * lineCount;
		}
	}

	public void updateEffect() {
		if (effectCounterOn) {
			effectCounter++;
			if (effectCounter == 10) {
				effectCounterOn = false;
				effectCounter = 0;
				effectY.clear();
			}
		}
	}

	public void drawEffect(Graphics2D g2) {
		if (effectCounterOn) {
			g2.setColor(Color.white);
			for (int y : effectY) {
				g2.fillRect(left_x, y, WIDTH, Block.SIZE);
			}
		}
	}

	public void draw(Graphics2D g2) {
		boolean isDark = true;

		for (int y = top_y; y < bottom_y; y += Block.SIZE) {
			for (int x = left_x; x < right_x; x += Block.SIZE) {
				if (isDark) {
					g2.setColor(new Color(30, 144, 255));
				} else {
					g2.setColor(new Color(173, 216, 230));
				}
				g2.fillRect(x, y, Block.SIZE, Block.SIZE);
				isDark = !isDark;
			}
			isDark = !isDark;
		}
	}

}
