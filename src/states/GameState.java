package states;

import ui.TowerDefence;

import java.awt.*;

/**
 * Created by johan on 2017-04-10.
 */
public class GameState extends State {

	long lastTime = System.nanoTime();


	@Override
	public void init() {

	}

	@Override
	public State update() {
		long time = System.nanoTime();
		double elapsedTime = (time-lastTime) / 1e9;
		lastTime = time;

		towerDefence.game.update(mouseState, lastMouseState, elapsedTime);

		if((towerDefence.game.gameState == game.GameState.Winner || towerDefence.game.gameState == game.GameState.Loser) &&
			mouseState.left && !lastMouseState.left)
			return new MainMenuState();

		return null;
	}

	@Override
	public void draw(Graphics2D g2d) {
		towerDefence.game.draw(g2d);

	}
}
