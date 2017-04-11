package game;

import game.enemies.Enemy;
import game.level.Level;
import game.level.Level1;
import game.projectiles.Projectile;
import game.towers.ArrowTower;
import game.towers.Tower;
import map.TiledMap;
import ui.MouseState;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

/**
 * Created by johan on 2017-04-10.
 */
public class Game {
	TiledMap map;

	Level level = new Level1();

	int cameraX = 0;
	int cameraY = 0;

	int currentWave = 1;

	double waveEnemySpawnTimer = 0;
	int waveEnemiesSpawned = 0;



	GameState gameState = GameState.WaveStart;
	double stateTime = 0;

	ArrayList<Enemy> enemies = new ArrayList<>();
	ArrayList<Projectile> projectiles = new ArrayList<>();
	ArrayList<Tower> towers = new ArrayList<>();

	public Game()
	{
		map = new TiledMap("/maps/map1.json");


		towers.add(new ArrowTower(3,6));
		towers.get(0).setGame(this);

	}

	public void update(MouseState mouseState, MouseState lastMouseState, double elapsedTime)
	{
		stateTime += elapsedTime;
		if(mouseState.middle)
		{
			cameraX += lastMouseState.x - mouseState.x;
			cameraY += lastMouseState.y - mouseState.y;
		}

		switch(gameState)
		{
			case WaveStart:
				if(stateTime > 1) {
					gameState = GameState.Wave;
					stateTime = 0;
				}
				break;
			case Wave:
				waveEnemySpawnTimer -= elapsedTime;
				if(waveEnemySpawnTimer < 0)
					spawnEnemy();
			case WaveEnd:
				for(Enemy e : enemies)
					e.update(elapsedTime);

				enemies.removeIf(e -> !e.isAlive());
				projectiles.removeIf(p -> !p.isAlive());

				for(Tower t : towers)
					t.update(elapsedTime);

				for(Projectile p : projectiles)
					p.update(elapsedTime);

				break;
		}
	}



	public void draw(Graphics2D g2d)
	{
		AffineTransform oldTransform = g2d.getTransform();
		g2d.setTransform(getCameraTransform());
		map.draw(g2d);

		for(Enemy e : enemies)
			e.draw(g2d);

		for(Tower t : towers)
			t.draw(g2d);

		for(Projectile p : projectiles)
			p.draw(g2d);

		g2d.setTransform(oldTransform);
		drawOverlay(g2d);

	}

	private void drawOverlay(Graphics2D g2d) {
		switch(gameState)
		{
			case WaveStart:
				double fac = stateTime; //runs from 0 to 1
				Shape s = new Font("Segoe UI", 0, 72).createGlyphVector(g2d.getFontRenderContext(), "Wave " + currentWave).getOutline();
				AffineTransform tx = new AffineTransform();
				tx.translate(900, 400);
				tx.scale(fac,fac);
				tx.translate(-s.getBounds().getWidth()/2, -s.getBounds().getHeight()/2);
				s = tx.createTransformedShape(s);
				g2d.fill(s);
				break;
			case WaveEnd:


				break;
			case Wave:


				break;
		}

	}


	public AffineTransform getCameraTransform() {
		return AffineTransform.getTranslateInstance(-cameraX, -cameraY);
	}

	public ArrayList<Enemy> getEnemyNear(Point p, double range)
	{
		ArrayList<Enemy> inRange = new ArrayList<>();
		for(Enemy e : enemies)
			if(p.distance(e.x, e.y) < range)
				inRange.add(e);
		return inRange;
	}



	private void spawnEnemy() {
		System.out.println("Spawning enemy....");
		waveEnemySpawnTimer = level.waves.get(currentWave-1).delay;

		try {
			//ugly reflection to go from the object's class to the object so I don't have to write a Enemy loader
			Class<?> enemyClass = level.waves.get(currentWave-1).enemies.get(waveEnemiesSpawned);
			Enemy enemy = (Enemy) enemyClass.newInstance();
			enemy.x = map.path.get(0).x * 128 + 64;
			enemy.y = map.path.get(0).y * 128 + 64;
			enemy.map = map;
			enemies.add(enemy);
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}

		waveEnemiesSpawned++;
		if(waveEnemiesSpawned >= level.waves.get(currentWave-1).enemies.size())
		{
			gameState = GameState.WaveEnd;
		}

	}


	public void addProjectile(Projectile projectile) {
		projectiles.add(projectile);
	}
}
