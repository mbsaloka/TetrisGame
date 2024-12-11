package main;

import javax.swing.*;

import utility.SaveData;

public class Main {
	public static void main(String[] args) {
		JFrame window = new JFrame("TETRIS");
		window.setContentPane(new GamePanel());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.pack();
		window.setVisible(true);

		SaveData.createIfNotExist();
	}

}
