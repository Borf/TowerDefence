package ui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by johan on 2017-04-10.
 */
public class main {
	public static void main(String[] args)
	{
		JFrame frame = new JFrame("Tower Defence");
		frame.setMinimumSize(new Dimension(800, 600));
		frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setContentPane(new TowerDefence());
		frame.setVisible(true);



	}
}
