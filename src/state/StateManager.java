package state;

public class StateManager {
	private State[] gameStates;
	private int currentState;
	private int previousState;

	public static final int GAMESTATES = 5;
	public static final int MENUSTATE = 0;
	public static final int ABOUTSTATE = 1;
	public static final int HIGHSCORESTATE = 2;
	public static final int PLAYSTATE = 3;
	public static final int GAMEOVERSTATE = 4;

	public StateManager() {
		gameStates = new State[GAMESTATES];

		previousState = -1;
		currentState = MENUSTATE;
		loadState(currentState);
	}

	private void loadState(int state) {
		if (state == MENUSTATE) gameStates[state] = new MenuState(this);
		// if (state == ABOUTSTATE) gameStates[state] = new AboutState(this);
		// if (state == HIGHSCORESTATE) gameStates[state] = new HighscoreState(this);
		if (state == PLAYSTATE) gameStates[state] = new PlayState(this);
		// if (state == GAMEOVERSTATE) gameStates[state] = new GameOverState(this);
	}

	private void unloadState(int state) {
		gameStates[state] = null;
	}

	public void setState(int state) {
		previousState = currentState;
		unloadState(currentState);
		currentState = state;
		loadState(currentState);
	}

	public void update() {
		try {
			if(gameStates[currentState] != null) gameStates[currentState].update();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void draw(java.awt.Graphics2D g) {
		try {
			if (gameStates[currentState] != null) gameStates[currentState].draw(g);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
