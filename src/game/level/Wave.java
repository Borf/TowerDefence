package game.level;

import java.util.ArrayList;

/**
 * Created by johan on 2017-04-10.
 */
public class Wave {
	public double delay = 1;
	public ArrayList<Class<?>> enemies = new ArrayList<>();

	public Wave(double delay, int amount, Class<?> monster) {
		this.delay = delay;
		for(int i = 0; i < amount; i++)
			enemies.add(monster);
	}
}
