package game.powerups;

import game.towers.Tower;
import java.util.ArrayList;

public class CritPowerUp extends PowerUp {

    public CritPowerUp()
    {
        super("/projectiles/electric-energy.png", 10, 250, 100, 100);
        this.rechargeTime = 10;
        this.activeTime = 5;
        this.button.setRound();
    }

    @Override
    public void effect()
    {
        ArrayList<Tower> towers = this.game.getTowers();
        for(Tower tower : towers)
        {
            int damage = tower.getDamage();
            tower.setDamage(damage * 5);
        }
    }

    @Override
    public void deActivate()
    {
        ArrayList<Tower> towers = this.game.getTowers();
        for(Tower tower : towers)
        {
            int damage = tower.getDamage();
            tower.setDamage(damage / 5);
        }
    }

}
