package game.level;

import java.util.ArrayList;

public abstract class Level{

	public String map;
	public ArrayList<Wave> waves = new ArrayList<>();
	public int startingGold = 100;

	Level()
	{

	}


}
