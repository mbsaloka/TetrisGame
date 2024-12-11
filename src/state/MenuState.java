package state;

import java.awt.*;
import java.awt.RenderingHints.Key;
import java.io.File;
import java.util.Objects;

import audio.AudioPlayer;
import board.*;
import main.GamePanel;
import ui.Button;
import ui.Title;
import utility.*;

public class MenuState extends State {
	private Background bg;
	private AudioPlayer music;

	private int currentChoice = 0;
	private Button[] options;
	private Title title;

	public MenuState(StateManager stateManager) {
		this.stateManager = stateManager;

		try {
			// music = new AudioPlayer("/SFX/music_menustate.wav");
			bg = new Background("/Backgrounds/Pattern01.png");
			options = new Button[] {
				new Button("/Buttons/Button_Play.png", (GamePanel.WIDTH / 2) - (164 / 2) , 300, 164, 84),
				new Button("/Buttons/Button_About.png", (GamePanel.WIDTH / 2) - (164 / 2) , 400, 164, 84),
				new Button("/Buttons/Button_Exit.png", (GamePanel.WIDTH / 2) - (164 / 2) , 500, 164, 84)
			};
			title = new Title("/Title/Title_Tetris.png", (GamePanel.WIDTH / 2) - (531 / 2), 120, 531, 115);
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

		title.draw(g);

		for (int i = 0; i < options.length; i++) {
			if (i == currentChoice) {
				options[i].setHovered(true);
				options[i].draw(g);
			} else {
				options[i].setHovered(false);
				options[i].draw(g);
			}
		}
	}

	private void select() {
		switch (currentChoice) {
			case 0: stateManager.setState(StateManager.PLAYSTATE); break;
			// case 1: stateManager.setState(StateManager.ABOUTSTATE); break;
			case 2: System.exit(0); break;
		}
	}

}
