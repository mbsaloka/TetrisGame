package state;

import java.awt.*;
import java.io.File;
import java.util.Objects;
import java.util.Random;

import audio.AudioPlayer;
import board.*;
import main.GamePanel;
import mino.*;
import ui.Button;
import ui.Panel;
import utility.*;

public class PlayState extends State {
	private Background bg;
	private AudioPlayer music;

	private Font font;

  private Board board;
  private Border border;
  private Mino currentMino;
  private Mino nextMino;
  private Button HomeButton;
  private Button resumeButton;
  private Button restartButton;
  private Panel pausePanel;

  private final int MINO_START_X;
  private final int MINO_START_Y;
  private final int NEXTMINO_X;
  private final int NEXTMINO_Y;

  private int level = 1;
  private int lines = 0;
  private int score = 0;
  private boolean gameOver = false;
  private boolean paused = false;
	public static int dropInterval = 60;

  private int pauseChoice = 1;
  private Button[] options;

	public PlayState(StateManager stateManager) {
		this.stateManager = stateManager;
    board = new Board(GamePanel.WIDTH, this);

    MINO_START_X = Board.left_x + (Board.WIDTH / 2) - (Block.SIZE * 2) - (Block.SIZE / 2);
    MINO_START_Y = Board.top_y - (Block.SIZE / 2);

    NEXTMINO_X = Board.right_x + 175;
    NEXTMINO_Y = Board.top_y + 500;

    currentMino = getRandomMino();
    currentMino.setXY(MINO_START_X, MINO_START_Y);
    nextMino = getRandomMino();
    nextMino.setXY(NEXTMINO_X, NEXTMINO_Y);

    Board.staticBlocks.clear();

		try {
			// music = new AudioPlayer("/SFX/music_menustate.wav");
			bg = new Background("/Backgrounds/Pattern01.png");
      border = new Border("/Backgrounds/Border.png");

      pausePanel = new Panel("/Panel/Panel_Pause.png", (GamePanel.WIDTH / 2) - (606 / 2), (GamePanel.HEIGHT / 2) - (240 /2), 606, 240);
      options = new Button[] {
				new Button("/Buttons/Button_Home.png", (GamePanel.WIDTH / 2) - (84 / 2) - 173, 360, 84, 84),
				new Button("/Buttons/Button_Resume.png", (GamePanel.WIDTH / 2) - (84 / 2) , 360, 84, 84),
				new Button("/Buttons/Button_Restart.png", (GamePanel.WIDTH / 2) - (84 / 2) + 173, 360, 84, 84)
      };

			Font AccidentalPrecidency = Font.createFont(Font.TRUETYPE_FONT, new File(Objects.requireNonNull(getClass().getResource("/Fonts/AccidentalPrecidency.ttf")).getPath()));

			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(AccidentalPrecidency);

			font = AccidentalPrecidency.deriveFont(50f);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// music.play();
	}

	@Override
	public void init() {
  }

	@Override
	public void update() {
    if (KeyHandler.escapePressed) {
      paused = !paused;
      KeyHandler.escapePressed = false;
    }

    if (paused) {
      if (KeyHandler.enterPressed) {
        select();
        KeyHandler.enterPressed = false;
      }

      if (KeyHandler.leftPressed) {
        if (pauseChoice > 0) pauseChoice--;
        KeyHandler.leftPressed = false;
      }

      if (KeyHandler.rightPressed) {
        if (pauseChoice < options.length - 1) pauseChoice++;
        KeyHandler.rightPressed = false;
      }

      return;
    }

    if (!currentMino.active) {
      Board.staticBlocks.add(currentMino.blocks[0]);
      Board.staticBlocks.add(currentMino.blocks[1]);
      Board.staticBlocks.add(currentMino.blocks[2]);
      Board.staticBlocks.add(currentMino.blocks[3]);

      if (currentMino.blocks[0].x == MINO_START_X && currentMino.blocks[0].y == MINO_START_Y) {
        gameOver = true;
      }

      currentMino.deactivating = false;

      currentMino = nextMino;
      currentMino.setXY(MINO_START_X, MINO_START_Y);
      nextMino = getRandomMino();
      nextMino.setXY(NEXTMINO_X, NEXTMINO_Y);

      board.checkDelete(level, lines, dropInterval, score);
    } else {
      currentMino.update();
    }
    board.updateEffect();
	}

	@Override
	public void draw(Graphics2D g) {
    bg.draw(g);
    board.draw(g);
    border.draw(g);

    board.drawEffect(g);
    if (currentMino != null) {
      currentMino.draw(g);
    }
    nextMino.draw(g);

    for (Block block : Board.staticBlocks) {
      block.draw(g);
    }

    g.setFont(font);
    g.setColor(Color.WHITE);

    g.drawString("Level: " + level, 50, 50);
    g.drawString("Lines: " + lines, 50, 100);
    g.drawString("Score: " + score, 50, 150);

    if (paused) {
      pausePanel.draw(g);
      for (int i=0; i < options.length; i++) {
        if (i == pauseChoice) {
          options[i].setHovered(true);
          options[i].draw(g);
        } else {
          options[i].setHovered(false);
          options[i].draw(g);
        }
      }
    }

    if (gameOver) {
      g.drawString("GAME OVER", Board.left_x + 50, Board.top_y + 300);
      stateManager.setState(StateManager.GAMEOVERSTATE);
    }
	}

  private Mino getRandomMino() {
    Mino mino = null;
    int i = new Random().nextInt(7);

    switch (i) {
      case 0: mino = new MinoBar(); break;
      case 1: mino = new MinoSquare(); break;
      case 2: mino = new MinoL(); break;
      case 3: mino = new MinoJ(); break;
      case 4: mino = new MinoS(); break;
      case 5: mino = new MinoZ(); break;
      case 6: mino = new MinoT(); break;
    }

    return mino;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  public void setLines(int lines) {
    this.lines = lines;
  }

  public void setScore(int score) {
    this.score = score;
  }

  private void select() {
    switch (pauseChoice) {
      case 0: stateManager.setState(StateManager.MENUSTATE); break;
      case 1: paused = false; break;
      case 2: stateManager.setState(StateManager.PLAYSTATE); break;
    }
  }

}
