package state;

import java.awt.*;
import java.awt.RenderingHints.Key;
import java.io.File;
import java.util.Objects;

import audio.AudioPlayer;
import board.*;
import main.GamePanel;
import utility.*;

public class MenuState extends State {
	private Background bg;
	private AudioPlayer music;

	private int currentChoice = 0;
	private String[] options = {
		"Play Game",
		"High Score",
		"About",
		"Quit"
	};

	private Color titleColor;
	private Font titleFont;
	private Font font;

	public MenuState(StateManager stateManager) {
		this.stateManager = stateManager;

		try {
			// music = new AudioPlayer("/SFX/music_menustate.wav");
			bg = new Background("/Backgrounds/Pattern01.png");

			Font ManilaCity = Font.createFont(Font.TRUETYPE_FONT, new File(Objects.requireNonNull(getClass().getResource("/Fonts/ManilaCity.ttf")).getPath()));
			Font AccidentalPrecidency = Font.createFont(Font.TRUETYPE_FONT, new File(Objects.requireNonNull(getClass().getResource("/Fonts/AccidentalPrecidency.ttf")).getPath()));

			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(ManilaCity);
			ge.registerFont(AccidentalPrecidency);

			titleColor = new Color(255, 235, 72);
			titleFont = ManilaCity.deriveFont(60f);

			font = AccidentalPrecidency.deriveFont(50f);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// music.play();
	}

	@Override
	public void init() {}

	@Override
	public void update() {
		if (KeyHandler.enterPressed) {
			// music.stop();
			select();
		}

		if (KeyHandler.upPressed) {
			currentChoice = currentChoice - 1 == -1 ? options.length - 1 : currentChoice - 1;
			KeyHandler.upPressed = false;
		}

		if (KeyHandler.downPressed) {
			currentChoice = currentChoice + 1 == options.length ? 0 : currentChoice + 1;
			KeyHandler.downPressed = false;
		}
	}

	@Override
	public void draw(Graphics2D g) {
		bg.draw(g);

		Utility.horizontalCenteredText(g, "TETRIS", GamePanel.WIDTH, 290, titleFont, titleColor);

		g.setFont(font);

		for (int i = 0; i < options.length; i++) {
			if (i == currentChoice) {
				g.setColor(Color.WHITE);
			} else {
				g.setColor(Color.DARK_GRAY);
			}

			Utility.horizontalCenteredText(g, options[i], GamePanel.WIDTH, 360 + i * 45, font, g.getColor());
		}
	}

	private void select() {
		switch (currentChoice) {
			case 0: stateManager.setState(StateManager.PLAYSTATE); break;
			// case 1: stateManager.setState(StateManager.HIGHSCORESTATE); break;
			// case 2: stateManager.setState(StateManager.ABOUTSTATE); break;
			case 3: System.exit(0); break;
		}
	}

}
