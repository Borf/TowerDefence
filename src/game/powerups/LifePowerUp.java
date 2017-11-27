package game.powerups;

public class LifePowerUp extends PowerUp {

    public LifePowerUp()
    {
        super("/powerups/oneUp.png", 10, 100, 100, 100);
        this.rechargeTime = 10;
    }

    @Override
    public void effect()
    {
        this.game.lives++;
    }

}
