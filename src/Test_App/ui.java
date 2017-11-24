package Test_App;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;

/**
 * Created by sander on 14-11-2017.
 */
public class ui extends JPanel implements ActionListener{
    public String pin = "-1";
    io io;
    JButton reset;
    int X = 0;
    public ui(){
        reset = new JButton("Reset");
        reset.addActionListener(this);
        try{
            io io = new io();
            io.setUI(this);
        }catch (Exception e){

        }
        add(reset);
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g.drawImage(new ImageIcon(this.getClass().getResource("/div/tech.jpg")).getImage(),0,0, 800,600,null);
        g2.setColor(new Color(148,230,242));
        g2.fill(new Rectangle2D.Double(300,35,200,20));
        g2.setColor(Color.black);
        g.drawString("the button pressed activated pin: " +pin, 300, 50);

        g.drawImage(new ImageIcon(this.getClass().getResource("/div/ArduinoUno.png")).getImage(),getHeight()/4,getWidth()/6, null);

        if(pin != "-1"){
            g.setColor(new Color(255,0,0));
            g2.setStroke(new BasicStroke(3));
            g.drawOval(X,130,20,55);
            g.setColor(new Color(0));
            /*
            * y = 130
            * pin 6  = x 460
            * pin 7  = x 444
            * pin 8  = x 420
            * pin 9  = x 406
            * pin 10 = x 392
            * */
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        pin = "-1";
        repaint();
    }
}
