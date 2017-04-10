package states;

import game.Game;
import ui.Button;
import ui.TowerDefence;

import java.awt.*;

public class MainMenuState extends State {
	Button startButton, quitButton;

	public void init()
	{
		startButton = new Button("Start", towerDefence.getWidth()/2-200, 100, 400, 100);

		quitButton = new Button("quit", towerDefence.getWidth()/2-200, 400, 400, 100);

	}



	@Override
	public State update() {
		if(startButton.clicked(mouseState, lastMouseState)) {
			towerDefence.game = new Game();
			return new GameState();
		}
		else if(quitButton.clicked(mouseState, lastMouseState))
			System.exit(0);



		return null;
	}

	@Override
	public void draw(Graphics2D g2d) {
		startButton.draw(g2d);
		quitButton.draw(g2d);
	}
}
