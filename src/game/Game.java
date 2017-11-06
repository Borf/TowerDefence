package game;

import game.enemies.Enemy;
import game.level.Level;
import game.level.Level1;
import game.projectiles.Projectile;
import game.towers.Tower;
import map.TiledMap;
import ui.MouseState;
import util.Reflection;

import java.awt.*;
import java.awt.geom.*;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

/**
 * Created by johan on 2017-04-10.
 */
public class Game {
	TiledMap map;
	Level level = new Level1();

	private int cameraX = 0;
	private int cameraY = 0;
	private int currentWave = 1;
	private double waveEnemySpawnTimer = 0;
	private int waveEnemiesSpawned = 0;
	private boolean towerBuilding = false;
	private int towerBuildX, towerBuildY;
	private int towerBuildScroll = 0;
	private boolean towerBuildHack = false;
	private MouseState startDragState;

	int gold = level.startingGold;
	public int lives = level.lives;


	public GameState gameState = GameState.WaveStart;
	double stateTime = 0;

	ArrayList<Enemy> enemies = new ArrayList<>();
	ArrayList<Projectile> projectiles = new ArrayList<>();
	ArrayList<Tower> towers = new ArrayList<>();
	PowerUp[] powerUps;
	Tower[] buildTowers;



	public Game(Level level)
	{
		this.level = level;
		map = new TiledMap("/maps/" + level.map);
		initializeTowerTemplates();
		chargePowerUps();
	}

	private void chargePowerUps()
	{
		Class<?>[] powerUpTemplates = Reflection.getClasses("game.powerups");
		powerUps = new PowerUp[powerUpTemplates.length-1];

	}

	/**
	 * Initializes tower templates through reflection
	 */
	private void initializeTowerTemplates()
	{
		Class<?>[] towerTemplates = Reflection.getClasses("game.towers");
		buildTowers = new Tower[towerTemplates.length-1];
		int ii = 0;
		for(int i = 0; i < towerTemplates.length; i++)
		{
			try
			{
				if(!Modifier.isAbstract(towerTemplates[i].getModifiers())) {
					Tower t = (Tower) towerTemplates[i].newInstance();
					t.centerX = t.images[0].getWidth()/2;
					buildTowers[ii++] = t;
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * Heart of the game engine. Should be called every tick
	 * @param mouseState 		 The current mouse state
	 * @param lastMouseState The mouse state of last frame
	 * @param elapsedTime    The number of seconds elapsed since last frame
	 */
	public void update(MouseState mouseState, MouseState lastMouseState, double elapsedTime)
	{
		stateTime += elapsedTime;
		//update camera
		if(mouseState.middle)
		{
			cameraX += lastMouseState.x - mouseState.x;
			cameraY += lastMouseState.y - mouseState.y;
		}

		//tower building menu
		updateTowerBuildMenu(mouseState, lastMouseState);

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
				if(enemies.isEmpty() && waveEnemiesSpawned == level.waves.get(currentWave-1).enemies.size())
				{
					gameState = GameState.WaveBreak;
					stateTime = 0;
				}
				for(Enemy e : enemies) {
					e.update(elapsedTime);
					if(e.pathNode == map.path.size()-1)
					{
						e.damage(9999999);
						lives--;
						if(lives <= 0)
						{
							stateTime = 0;
							gameState = GameState.Loser;
						}
					}
					if(!e.isAlive())
						gold += e.gold;
				}

				projectiles.removeIf(p -> !p.isAlive());
				enemies.removeIf(e -> !e.isAlive());

				for(Tower t : towers)
					t.update(elapsedTime);

				for(Projectile p : projectiles)
					p.update(elapsedTime);

				break;
			case WaveBreak:
				if(stateTime > 2)
				{
					currentWave++;
					stateTime = 0;
					if(currentWave > level.waves.size())
						gameState = GameState.Winner;
					else {
						gameState = GameState.WaveStart;
						waveEnemiesSpawned = 0;
						waveEnemySpawnTimer = 0;
					}
				}
				break;
		}
	}

	/**
	 * Handles the mouse for the tower building menu
	 * @param mouseState     Current mouse state
	 * @param lastMouseState Last mouse state
	 */
	private void updateTowerBuildMenu(MouseState mouseState, MouseState lastMouseState) {
		Point2D mouse = null;
		try {
			mouse = getCameraTransform().inverseTransform(new Point2D.Double(mouseState.x, mouseState.y), null);
		} catch (NoninvertibleTransformException e) {
			e.printStackTrace();
		}

		if(mouseState.left && !lastMouseState.left && !towerBuilding)
		{
			startDragState = new MouseState(mouseState);
			int tileX = (int) (mouse.getX() / 128);
			int tileY = (int) (mouse.getY() / 128);
			if (tileX >= 0 && tileY >= 0 && tileX < map.width && tileY < map.height) {
				int index = map.layers.get(0).indices[tileY][tileX];
				if (index == 23) {
					towerBuildX = tileX * 128 + 64;
					towerBuildY = tileY * 128 + 64;
					towerBuilding = true;
					towerBuildHack = true;
				}
			}
		}
		else if(towerBuilding && (mouseState.left || lastMouseState.left))
		{
			if(mouseState.left && !lastMouseState.left)
				startDragState = new MouseState(mouseState);

			Shape buildWindow = new RoundRectangle2D.Double(towerBuildX, towerBuildY, 400, 150, 10, 10);
			if(buildWindow.contains(mouse) && !towerBuildHack)
			{
				if(mouseState.left && lastMouseState.left) // drag
					towerBuildScroll += (mouseState.x - lastMouseState.x);
				else if(!mouseState.left && lastMouseState.left && new Point(mouseState.x, mouseState.y).distance(startDragState.x, startDragState.y) < 3) //mouse up
				{
					int index = (int)((mouse.getX() - (towerBuildX + 5 + towerBuildScroll)) / 130);
					if(index >= 0 && index < buildTowers.length && buildTowers[index].cost <= gold) {
						buildTower(index);
					}

					towerBuilding = false;
				}

			}
			else if(!lastMouseState.left && !towerBuildHack)
				towerBuilding = false;

			if(!mouseState.left && lastMouseState.left)
				towerBuildHack = false;

		}
	}

	/**
	 * Builds a tower at the towerbuild position
	 * @param index the index in the tower build array of the tower to build
	 */
	private void buildTower(int index)
	{
		try {
			gold -= buildTowers[index].cost;
			Tower newTower = newTower = (Tower) buildTowers[index].getClass().newInstance();
			newTower.x = towerBuildX;
			newTower.y = towerBuildY;
			newTower.centerY = -(newTower.images[0].getHeight() - 128);
			newTower.setGame(this);
			towers.add(newTower);
			map.layers.get(0).indices[(towerBuildY - 64) / 128][(towerBuildX - 64) / 128] = 1;
			map.layers.get(0).updateImage();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}


	/**
	 * Draws the game
	 * @param g2d The graphics object to draw on
	 */
	public void draw(Graphics2D g2d)
	{
		AffineTransform oldTransform = g2d.getTransform();
		g2d.setTransform(getCameraTransform());
		map.draw(g2d);


		ArrayList<GameObject> objects = new ArrayList<>();
		objects.addAll(enemies);
		objects.addAll(towers);
		objects.addAll(projectiles);
		objects.sort((GameObject a, GameObject b) -> (int)(a.y - b.y));

		for(GameObject o : objects)
			o.draw(g2d);

		if(towerBuilding)
		{
			Shape buildWindow = new RoundRectangle2D.Double(towerBuildX, towerBuildY, 400, 150, 10, 10);
			g2d.setColor(new Color(1.0f,1.0f,1.0f,0.5f));
			g2d.fill(buildWindow);
			g2d.setColor(Color.black);
			g2d.draw(buildWindow);
			int x = towerBuildX + 5 + towerBuildScroll;
			int y = towerBuildY + 50;
			for(Tower t : buildTowers)
			{
				Area s = new Area(new RoundRectangle2D.Double(x, y-40, 128, 130, 5,5));
				s.intersect(new Area(buildWindow));
				g2d.setClip(s);
				t.x = x;
				t.y = y;
				t.draw(g2d);
				g2d.setFont(new Font("Segoe UI", Font.BOLD, 20));
				g2d.drawString(t.cost + "", x, y-10);
				g2d.setClip(null);
				g2d.draw(s);
				x+=130;
			}
		}
		g2d.setTransform(oldTransform);
		drawOverlay(g2d);

	}

	/**
	 * Draws the overlay that doesn't scroll with the camera (just texts for now)
	 * @param g2d	the graphics object to draw on
	 */
	private void drawOverlay(Graphics2D g2d) {
		g2d.setColor(new Color(1.0f,1.0f,1.0f,0.5f));
		g2d.fill(new RoundRectangle2D.Double(0,0,400,100, 50, 50));
		g2d.setFont(new Font("Segoe UI", 0, 72));
		g2d.setColor(Color.black);
		g2d.drawString("Gold: " + gold, 10,72);

		g2d.setColor(new Color(1.0f,1.0f,1.0f,0.5f));
		g2d.fill(new RoundRectangle2D.Double(400,0,400,100, 50, 50));
		g2d.setFont(new Font("Segoe UI", 0, 72));
		g2d.setColor(Color.black);
		g2d.drawString("Lives: " + lives, 410,72);

		for(PowerUp powerUp : powerUps)
		{
			powerUp.draw(g2d);
		}



		switch(gameState)
		{
			case WaveStart: {
				double fac = stateTime; //runs from 0 to 1
				Shape s = new Font("Segoe UI", 0, 72).createGlyphVector(g2d.getFontRenderContext(), "Wave " + currentWave).getOutline();
				AffineTransform tx = new AffineTransform();
				tx.translate(900, 400);
				tx.scale(fac, fac);
				tx.translate(-s.getBounds().getWidth() / 2, -s.getBounds().getHeight() / 2);
				s = tx.createTransformedShape(s);
				g2d.fill(s);
				break;
			}
			case WaveEnd:
				break;
			case Wave:
				break;
			case Winner: {
				double fac = Math.min(2, stateTime); //runs from 0 to 2
				Shape s = new Font("Segoe UI", 0, 72).createGlyphVector(g2d.getFontRenderContext(), "You Win!").getOutline();
				AffineTransform tx = new AffineTransform();
				tx.translate(900, 400);
				tx.scale(fac, fac);
				tx.translate(-s.getBounds().getWidth() / 2, -s.getBounds().getHeight() / 2);
				s = tx.createTransformedShape(s);
				g2d.fill(s);
				break;
			}
			case Loser:{
				double fac = Math.min(2, stateTime); //runs from 0 to 2
				Shape s = new Font("Segoe UI", 0, 72).createGlyphVector(g2d.getFontRenderContext(), "You Lose!").getOutline();
				AffineTransform tx = new AffineTransform();
				tx.translate(900, 400);
				tx.scale(fac, fac);
				tx.translate(-s.getBounds().getWidth() / 2, -s.getBounds().getHeight() / 2);
				s = tx.createTransformedShape(s);
				g2d.fill(s);
				break;
			}
		}

	}


	public AffineTransform getCameraTransform() {
		return AffineTransform.getTranslateInstance(-cameraX, -cameraY);
	}

	/**
	 * Gets a list of enemies near a point
	 * @param p     The position to search from
	 * @param range The range to search in
	 * @return
	 */
	public ArrayList<Enemy> getEnemiesNear(Point p, double range)
	{
		ArrayList<Enemy> inRange = new ArrayList<>();
		for(Enemy e : enemies)
			if(p.distance(e.x, e.y) < range)
				inRange.add(e);
		return inRange;
	}


	/**
	 * Spawns an enemy. Don't call this if at the end of the wave
	 */
	private void spawnEnemy() {
		waveEnemySpawnTimer = level.waves.get(currentWave-1).delay;

		try {
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
		projectiles.add(projectile);projectile.setGame(this);
	}
}
