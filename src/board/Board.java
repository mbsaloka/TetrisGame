package board;

import java.awt.Graphics2D;
import java.awt.Color;
import java.util.ArrayList;

import mino.Block;
import mino.Mino;
import state.PlayState;

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

	private PlayState playState;

	public Board(int panelWidth, PlayState playState) {
		this.playState = playState;
		left_x = (panelWidth / 2) - (WIDTH / 2);
		right_x = left_x + WIDTH;
		top_y = 50;
		bottom_y = top_y + HEIGHT;
	}

	public void checkDelete(int level, int lines, int dropInterval, int score) {
		int x = Mino.leftBound - Block.SIZE;
		int y = top_y;
		int blockCount = 0;
		int lineCount = 0;

		while (x <= Mino.rightBound && y <= Mino.bottomBound) {
			for (Block block : staticBlocks) {
				if (block.x == (x + (Block.SIZE / 2)) && block.y == (y + (Block.SIZE / 2))) {
					blockCount++;
				}
				else{
				}
			}

			x += Block.SIZE;

			if (x > Mino.rightBound) {
				if (blockCount == BLOCKS_PER_ROW) {
					effectCounterOn = true;
					effectY.add(y + (2 * Block.SIZE));

					for (int i = staticBlocks.size() - 1; i >= 0; i--) {
						Block block = staticBlocks.get(i);
						if (block.y == (y + (Block.SIZE / 2))) {
							staticBlocks.remove(i);
						}
					}

					lineCount++;
					lines++;
					playState.setLines(lines);

					if (lines % 10 == 0 && dropInterval > 1) {
						level++;
						playState.setLevel(level);
						dropInterval = Math.max(dropInterval - 10, 1);
					}

					for (Block block : staticBlocks) {
						if (block.y < y) {
							block.y += Block.SIZE;
						}
					}
				}

				blockCount = 0;
				x = Mino.leftBound - Block.SIZE;
				y += Block.SIZE;
			}
		}

		if (lineCount > 0) {
			int singleLineScore = 10 * level;
			score += singleLineScore * lineCount;
			playState.setScore(score);
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
			g2.setColor(new Color(255, 255, 255, 200));
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
