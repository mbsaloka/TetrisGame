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

public class AboutState extends State{
	private AudioPlayer sfxSelectOption;

	private Background bg;
	private Panel aboutPanel;

	public AboutState(StateManager stateManager){
		this.stateManager = stateManager;

		try{
			sfxSelectOption = new AudioPlayer("/SFX/Select.wav");

			bg = new Background("/Backgrounds/Pattern01.png");
			aboutPanel = new Panel("/Panel/Panel_About.png", (GamePanel.WIDTH / 2) - (730 / 2), (GamePanel.HEIGHT / 2) - (510 / 2), 730, 510);
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void init() {}

	@Override
	public void update() {
		if (KeyHandler.escapePressed) {
			KeyHandler.escapePressed = false;
			sfxSelectOption.play();
			stateManager.setState(StateManager.MENUSTATE);
		}
	}

	@Override
	public void draw(Graphics2D g) {
		bg.draw(g);
		aboutPanel.draw(g);
	}

}
