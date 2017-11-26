package io.Buttons;

import game.enemies.Enemy;

/**
 * Created by sander on 14-11-2017.
 */
public class Button1 extends Button {
    public Button1(){
        PinNumber = 6;
    }
    @Override
    public void effect() {
        setGame(); //de game moet geset worden zodat deze functie toegang heeft tot de huidige arrays van alles.

        enemies = game.GetEnemies();
        for (Enemy e: enemies) {
            e.damage(100000);
        }
        game.SetEnemies(enemies);

        game.AddGold(100000);
    }
}
