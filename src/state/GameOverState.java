package state;

import java.awt.*;
import java.io.File;
import java.util.Objects;

import audio.AudioPlayer;
import board.Background;
import main.GamePanel;
import ui.Button;
import ui.Panel;
import utility.*;

public class GameOverState extends State{
	private AudioPlayer sfxGameOver;
	private AudioPlayer sfxChangeOption;
	private AudioPlayer sfxSelectOption;

	private Font font;

	private Background bg;
	private Panel gameOverPanel;

	private int choice = 0;
	private Button[] options;

	public GameOverState(StateManager stateManager){
		this.stateManager = stateManager;

		try{
			sfxGameOver = new AudioPlayer("/SFX/Game_Over.wav");
			sfxChangeOption = new AudioPlayer("/SFX/Change_Option.wav");
			sfxSelectOption = new AudioPlayer("/SFX/Select.wav");

			bg = new Background("/Backgrounds/Pattern01.png");

			gameOverPanel = new Panel("/Panel/Panel_GameOver.png", (GamePanel.WIDTH / 2) - (606 / 2), (GamePanel.HEIGHT / 2) - (422 / 2), 606, 422);
			options = new Button[] {
				new Button("/Buttons/Button_Home.png", (GamePanel.WIDTH / 2) - (84 / 2) - 70, 450, 84, 84),
				new Button("/Buttons/Button_Restart.png", (GamePanel.WIDTH / 2) - (84 / 2) + 70, 450, 84, 84)
			};

			Font AccidentalPrecidency = Font.createFont(Font.TRUETYPE_FONT, new File(Objects.requireNonNull(getClass().getResource("/Fonts/AccidentalPrecidency.ttf")).getPath()));

			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(AccidentalPrecidency);

			font = AccidentalPrecidency.deriveFont(50f);
		}catch (Exception e){
			e.printStackTrace();
		}

		sfxGameOver.play();
	}

	@Override
	public void init() {}

	@Override
	public void update() {
		if (KeyHandler.enterPressed) {
			select();
			KeyHandler.enterPressed = false;
		}

		if (KeyHandler.leftPressed) {
			if (choice > 0) choice--;
			sfxChangeOption.play();
			KeyHandler.leftPressed = false;
		}

		if (KeyHandler.rightPressed) {
			if (choice < options.length - 1) choice++;
			sfxChangeOption.play();
			KeyHandler.rightPressed = false;
		}
	}

	@Override
	public void draw(Graphics2D g) {
		bg.draw(g);
		gameOverPanel.draw(g);

		for (int i = 0; i < options.length; i++) {
			if (i == choice) {
				options[i].setHovered(true);
			} else {
				options[i].setHovered(false);
			}
			options[i].draw(g);
		}

		g.setFont(font);
		g.setColor(Color.WHITE);

		String str1 = "Level: " + stateManager.level;
		Utility.centeredText(g, str1, GamePanel.WIDTH, GamePanel.HEIGHT - 50, font, Color.BLACK);

		String str2 = "Lines: " + stateManager.lines;
		Utility.centeredText(g, str2, GamePanel.WIDTH, GamePanel.HEIGHT + 60, font, Color.BLACK);

		String str3 = "Score: " + stateManager.score;
		Utility.centeredText(g, str3, GamePanel.WIDTH, GamePanel.HEIGHT + 170, font, Color.BLACK);
	}

	private void select() {
		sfxSelectOption.play();
		switch (choice) {
			case 0:
				stateManager.setState(StateManager.MENUSTATE);
				break;
			case 1:
				stateManager.setState(StateManager.PLAYSTATE);
				break;
		}
	}

}
