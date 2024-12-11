package mino;

import java.awt.*;

import enums.MinoType;
import utility.KeyHandler;
import board.Board;
import state.PlayState;

public class Mino {
	public Block blocks[] = new Block[4];
	public Block tempBlocks[] = new Block[4];
	public int direction = 1;
	public boolean active = true;
	public boolean deactivating;
	private int autoDropCounter = 0;
	private int deactivateCounter = 0;
	private boolean leftCollision, rightCollision, bottomCollision;

	private int left_bound = Board.left_x - Block.SIZE;
	private int right_bound = Board.right_x - (Block.SIZE * 3);
	private int bottom_bound = Board.bottom_y - (Block.SIZE * 3);

	public void create(MinoType type) {
		for (int i = 0; i < 4; i++) {
			blocks[i] = new Block(type);
			tempBlocks[i] = new Block(type);
		}
	}

	public void setXY(int x, int y) {}

	public void updateXY(int direction) {
		checkRotationCollision();

		if (!leftCollision && !rightCollision && !bottomCollision) {
			this.direction = direction;
			for (int i = 0; i < 4; i++) {
				blocks[i].x = tempBlocks[i].x;
				blocks[i].y = tempBlocks[i].y;
			}
		}
	}

	public void getDirection1() {}

	public void getDirection2() {}

	public void getDirection3() {}

	public void getDirection4() {}

	public void checkMoveCollision() {
		leftCollision = rightCollision = bottomCollision = false;

		checkStaticBlocksCollision();

		for (int i = 0; i < 4; i++) {
			if (blocks[i].x <= left_bound) {
				leftCollision = true;
			}

			if (blocks[i].x >= right_bound) {
				rightCollision = true;
			}

			if (blocks[i].y >= bottom_bound) {
				bottomCollision = true;
			}
		}
	}

	public void checkRotationCollision() {
		leftCollision = rightCollision = bottomCollision = false;

		checkStaticBlocksCollision();

		for (int i = 0; i < 4; i++) {
			if (tempBlocks[i].x + Block.SIZE < left_bound) {
				leftCollision = true;
			}

			if (tempBlocks[i].x - Block.SIZE > right_bound) {
				rightCollision = true;
			}

			if (tempBlocks[i].y + Block.SIZE > bottom_bound) {
				bottomCollision = true;
			}
		}
	}

	private void checkStaticBlocksCollision() {
		for (int i = 0; i < Board.staticBlocks.size(); i++) {
			int targetX = Board.staticBlocks.get(i).x;
			int targetY = Board.staticBlocks.get(i).y;

			for (int j = 0; j < 4; j++) {
				if (blocks[j].y + Block.SIZE == targetY && blocks[j].x == targetX) {
					bottomCollision = true;
				}

				if (blocks[j].x - Block.SIZE == targetX && blocks[j].y == targetY) {
					leftCollision = true;
				}

				if (blocks[j].x + Block.SIZE == targetX && blocks[j].y == targetY) {
					rightCollision = true;
				}
			}
		}
	}

	public void update() {
		if (deactivating) {
			deactivating();
		}

		if (KeyHandler.upPressed) {
			switch (direction) {
				case 1: getDirection2(); break;
				case 2: getDirection3(); break;
				case 3: getDirection4(); break;
				case 4: getDirection1(); break;
			}
			KeyHandler.upPressed = false;
		}

		checkMoveCollision();

		if (KeyHandler.leftPressed) {
			if (!leftCollision) {
				for (int i = 0; i < 4; i++) {
					blocks[i].x -= Block.SIZE;
				}
			}
			KeyHandler.leftPressed = false;
		}

		if (KeyHandler.rightPressed) {
			if (!rightCollision) {
				for (int i = 0; i < 4; i++) {
					blocks[i].x += Block.SIZE;
				}
			}
			KeyHandler.rightPressed = false;
		}

		if (KeyHandler.downPressed) {
			if (!bottomCollision) {
				for (int i = 0; i < 4; i++) {
					blocks[i].y += Block.SIZE;
				}

				autoDropCounter = 0;
			}
			KeyHandler.downPressed = false;
		}

		if (KeyHandler.spacePressed) {
			while (!bottomCollision) {
				for (int i = 0; i < 4; i++) {
					blocks[i].y += Block.SIZE;
				}
				checkMoveCollision();

				autoDropCounter = 0;
			}
			KeyHandler.spacePressed = false;
		}

		if (bottomCollision) {
			deactivating = true;
		} else {
			autoDropCounter++;
			if (autoDropCounter == PlayState.dropInterval) {
				for (int i = 0; i < 4; i++) {
					blocks[i].y += Block.SIZE;
				}
				autoDropCounter = 0;
			}
		}
	}

	public void deactivating() {
		deactivateCounter++;

		if (deactivateCounter == 45) {
			deactivateCounter = 0;
			checkMoveCollision();

			if (bottomCollision) {
				active = false;
			}
		}
	}

	public void draw(Graphics2D g2) {
    for (Block block : blocks) {
			block.draw(g2);
    }
	}

}
