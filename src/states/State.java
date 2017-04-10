package states;

import ui.MouseState;
import ui.TowerDefence;

import java.awt.*;

public abstract class State {
	public MouseState mouseState;
	public MouseState lastMouseState;
	public TowerDefence towerDefence;


	public abstract void init();
	public abstract State update();
	public abstract void draw(Graphics2D g2d);

}
