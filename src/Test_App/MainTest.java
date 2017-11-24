package Test_App;



import javax.swing.*;
import java.awt.*;

/**
 * Created by sander on 14-11-2017.
 */
public class MainTest {
    public static void main(String[] args)
    {

        JFrame frame = new JFrame("Test");
        frame.setMinimumSize(new Dimension(800, 600));
        frame.setResizable( false );
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setContentPane(new ui());
        frame.setVisible(true);

    }
}
