package game.powerups;

import game.towers.Tower;
import java.util.ArrayList;

public class CritPowerUp extends PowerUp {

    public CritPowerUp()
    {
        super("/projectiles/electric-energy.png", 10, 250, 100, 100);
        this.rechargeTime = 10;
        this.button.setRound();
    }

    @Override
    public void effect()
    {
        ArrayList<Tower> towers = this.game.getTowers();
        for(Tower tower : towers)
        {
            tower.setDamage(100);
        }
    }

}
