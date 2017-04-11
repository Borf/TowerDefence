package ui;

/**
 * Created by johan on 2017-04-10.
 */
public class MouseState {
	public int x = 0;
	public int y = 0;
	public boolean left = false;
	public boolean right = false;
	public boolean middle = false;


	MouseState()
	{

	}
	public MouseState(MouseState other)
	{
		x = other.x;
		y = other.y;
		left = other.left;
		right = other.right;
		middle = other.middle;
	}

}
