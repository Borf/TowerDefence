package states;

import game.Game;
import game.level.Level;
import ui.Button;
import util.Reflection;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

/**
 * Created by johan on 2017-04-11.
 */
public class LevelSelectState extends State {
	Button backButton;
	ArrayList<Button> levelButtons = new ArrayList<>();

	public void init()
	{
		backButton = new Button("<", 10, 10, 100, 100);

		Class[] levels = Reflection.getClasses("game.level");

		for(Class c : levels)
		{
			if(Modifier.isAbstract(c.getModifiers()))
				continue;
			if(c.getSimpleName().equals("Wave"))
				continue;

			int y = 50 + 150 * levelButtons.size();

			levelButtons.add(new Button(c.getSimpleName(), towerDefence.getWidth()/2-200, y, 400, 100));


		}

	}



	@Override
	public State update() {
		if(backButton.clicked(mouseState, lastMouseState)) {
			return new MainMenuState();
		}

		for(Button b : levelButtons)
		{
			if(b.clicked(mouseState, lastMouseState))
			{
				try {
					Level level = (Level) Class.forName("game.level." + b.getText()).newInstance();
					towerDefence.game = new Game(level);
					towerDefence.com.setGame(towerDefence.game);
					return new GameState();


				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}




		return null;
	}

	@Override
	public void draw(Graphics2D g2d) {
		g2d.drawImage(new ImageIcon(this.getClass().getResource("/div/test.png")).getImage(), 0,0, null);
		backButton.draw(g2d);
		for(Button b : levelButtons)
			b.draw(g2d);
	}
}
