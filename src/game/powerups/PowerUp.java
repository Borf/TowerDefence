package game.powerups;

import java.awt.*;
import ui.Button;
import ui.MouseState;
import game.Game;

public abstract class PowerUp {

    protected Button button;
    private Boolean useable = true;
    private double currentCharge;
    protected int rechargeTime;
    protected Game game;

    public PowerUp(String imageString, int x, int y, int width, int height)
    {
        this.button = new Button("", x, y, width, height);
        button.setImage(imageString);
    }

    public void draw(Graphics2D G2D)
    {
        //tx.rotate(angle);
        //tx.translate(centerX, centerY);
        if(useable)
        {
            button.draw(G2D);
        }
    }

    public void update(MouseState mouseState, MouseState lastMouseState, double elapsedTime)
    {
        if(button.clicked(mouseState, lastMouseState) && useable)
        {
            effect();
            useable = false;
            currentCharge = rechargeTime;
        }
        else
        {
            currentCharge -= elapsedTime;
            if(currentCharge <= 0)
            {
                useable = true;
            }
        }
    }

    public void setGame(Game game)
    {
        this.game = game;
    }

    public void effect()
    {

    }
}
