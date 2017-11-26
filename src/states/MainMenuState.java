package states;

import sun.awt.image.ToolkitImage;
import ui.Button;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.nio.Buffer;

public class MainMenuState extends State {
	Button startButton, quitButton;
	BufferedImage wood;
	public void init()
	{
		startButton = new Button("Start", towerDefence.getWidth()/2-200, towerDefence.getHeight()/2-100, 400, 100);

		quitButton = new Button("quit", towerDefence.getWidth()/2-200, towerDefence.getHeight()/2+100, 400, 100);
		Image img =  new ImageIcon(this.getClass().getResource("/div/wood.png")).getImage();
		wood = ((ToolkitImage) img).getBufferedImage();
	}



	@Override
	public State update() {
		if(startButton.clicked(mouseState, lastMouseState)) {
			return new LevelSelectState();
		}
		else if(quitButton.clicked(mouseState, lastMouseState))
			System.exit(0);



		return null;
	}

	@Override
	public void draw(Graphics2D g2d) {
		g2d.drawImage(new ImageIcon(this.getClass().getResource("/div/MainMenu.png")).getImage(), 0,0, null);
		g2d.setColor(Color.black);
		g2d.fill(new Rectangle2D.Double(towerDefence.getWidth()/2-290,210,600,200));
		TexturePaint sign = new TexturePaint(wood, new Rectangle(200,100));
		g2d.setPaint(sign);
		g2d.fillRect(towerDefence.getWidth()/2-300, 200, 600,200);
		//g2d.setColor(new Color(138,7,7));
		g2d.setColor(Color.black);
		g2d.setFont(new Font("Segoe UI", Font.PLAIN, 72));
		g2d.drawString("Tower Defence" , towerDefence.getWidth()/2-250,320);
		startButton.draw(g2d);
		quitButton.draw(g2d);
	}
}
