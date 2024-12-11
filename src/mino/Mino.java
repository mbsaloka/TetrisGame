package mino;

import java.awt.*;

import audio.AudioPlayer;
import enums.MinoType;
import utility.KeyHandler;
import board.Board;
import state.PlayState;

public class Mino {
	public Block blocks[] = new Block[4];
	public Block tempBlocks[] = new Block[4];

	private AudioPlayer sfxBrickMove; // mino
  private AudioPlayer sfxDropBrick; // mino
	private AudioPlayer sfxDeactiveBrick; // mino

	public int direction = 1;
	public boolean active = true;
	public boolean deactivating;
	private int autoDropCounter = 0;
	private int deactivateCounter = 0;
	private boolean leftCollision, rightCollision, bottomCollision;

	public static int leftBound = Board.left_x - Block.SIZE;
	public static int rightBound = Board.right_x - (Block.SIZE * 3);
	public static int bottomBound = Board.bottom_y - (Block.SIZE * 3);

	public Mino() {
		try {
			sfxBrickMove = new AudioPlayer("/SFX/Brick_Move.wav");
			sfxDropBrick = new AudioPlayer("/SFX/Drop_Brick.wav");
			sfxDeactiveBrick = new AudioPlayer("/SFX/Deactive_Brick.wav");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

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
			if (blocks[i].x <= leftBound) {
				leftCollision = true;
			}

			if (blocks[i].x >= rightBound) {
				rightCollision = true;
			}

			if (blocks[i].y >= bottomBound) {
				bottomCollision = true;
			}
		}
	}

	public void checkRotationCollision() {
		leftCollision = rightCollision = bottomCollision = false;

		checkStaticBlocksCollision();

		for (int i = 0; i < 4; i++) {
			if (tempBlocks[i].x + Block.SIZE < leftBound) {
				leftCollision = true;
			}

			if (tempBlocks[i].x - Block.SIZE > rightBound) {
				rightCollision = true;
			}

			if (tempBlocks[i].y + Block.SIZE > bottomBound) {
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
			sfxBrickMove.play();
			KeyHandler.upPressed = false;
		}

		checkMoveCollision();

		if (KeyHandler.leftPressed) {
			if (!leftCollision) {
				for (int i = 0; i < 4; i++) {
					blocks[i].x -= Block.SIZE;
				}
			}
			sfxBrickMove.play();
			KeyHandler.leftPressed = false;
		}

		if (KeyHandler.rightPressed) {
			if (!rightCollision) {
				for (int i = 0; i < 4; i++) {
					blocks[i].x += Block.SIZE;
				}
			}
			sfxBrickMove.play();
			KeyHandler.rightPressed = false;
		}

		if (KeyHandler.downPressed) {
			if (!bottomCollision) {
				for (int i = 0; i < 4; i++) {
					blocks[i].y += Block.SIZE;
				}

				autoDropCounter = 0;
			}
			sfxBrickMove.play();
			KeyHandler.downPressed = false;
		}

		if (KeyHandler.spacePressed) {
			while (!bottomCollision) {
				for (int i = 0; i < 4; i++) {
					blocks[i].y += Block.SIZE;
				}
				checkMoveCollision();

				autoDropCounter = 0;
				active = false;
			}
			sfxDropBrick.play();
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

		if (deactivateCounter == 25) {
			deactivateCounter = 0;
			checkMoveCollision();

			if (bottomCollision) {
				active = false;
				sfxDeactiveBrick.play();
			}
		}
	}

	public void draw(Graphics2D g2) {
    for (Block block : blocks) {
			block.draw(g2);
    }
	}

}
