package state;

public abstract class State {
    protected StateManager stateManager;

    public abstract void init();
    public abstract void update();
    public abstract void draw(java.awt.Graphics2D g);

}
