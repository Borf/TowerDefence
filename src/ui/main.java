package ui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by johan on 2017-04-10.
 */
public class main {
	public static void main(String[] args)
	{
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = /*800;*/ screenSize.getWidth();
		double height = /*600;*/ screenSize.getHeight();

		JFrame frame = new JFrame("Tower Defence");
		frame.setMinimumSize(new Dimension((int)width, (int)height));
		frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setContentPane(new TowerDefence());
		frame.setVisible(true);



	}
}
