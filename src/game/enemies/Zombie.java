package game.enemies;

/**
 * Created by johan on 2017-04-10.
 */
public class Zombie extends Enemy {

	public Zombie() {
		super("/enemies/Zombie.png", 3);
		this.speed = 50;
		this.maxHealth = 100;
		this.gold = 25;
	}
}
