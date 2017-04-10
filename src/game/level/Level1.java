package game.level;

/**
 * Created by johan on 2017-04-10.
 */
public class Level1 extends Level {

	public Level1() {

		map = "map1.json";



		waves.add(new Wave(1, 10, game.enemies.Wanderer.class));
		waves.add(new Wave(0.25f, 10, game.enemies.Wanderer.class));



	}
}
