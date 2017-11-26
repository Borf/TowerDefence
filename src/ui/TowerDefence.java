package ui;

import game.Game;
import io.Com.COM;
import states.MainMenuState;
import states.State;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by johan on 2017-04-10.
 */
public class TowerDefence extends JPanel implements ActionListener, MouseListener, MouseMotionListener{
	State currentState;
	MouseState mouseState = new MouseState();
	MouseState lastMouseState = new MouseState();
	public Game game;
	public COM com;

	public TowerDefence() {
		super();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = /*800;*/ screenSize.getWidth();
		double height = /*600;*/screenSize.getHeight();

		setSize((int)width, (int)height);
		currentState = new MainMenuState();
		currentState.towerDefence = this;
		currentState.init();

		try {
			com = new COM();
		} catch (Exception e) {
			e.printStackTrace();
		}

		new Timer(1000/60, this).start();
		addMouseMotionListener(this);
		addMouseListener(this);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		currentState.draw(g2d);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		currentState.mouseState = mouseState;
		currentState.lastMouseState = lastMouseState;

		State newState = currentState.update();
		if(newState != null) {
			currentState = newState;
			currentState.towerDefence = this;
			currentState.init();
		}
		lastMouseState = new MouseState(mouseState);
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(SwingUtilities.isLeftMouseButton(e))
			mouseState.left = true;
		if(SwingUtilities.isRightMouseButton(e))
			mouseState.right = true;
		if(SwingUtilities.isMiddleMouseButton(e))
			mouseState.middle = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(SwingUtilities.isLeftMouseButton(e))
			mouseState.left = false;
		if(SwingUtilities.isRightMouseButton(e))
			mouseState.right = false;
		if(SwingUtilities.isMiddleMouseButton(e))
			mouseState.middle = false;
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mouseState.x = e.getX();
		mouseState.y = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseState.x = e.getX();
		mouseState.y = e.getY();
	}
}
