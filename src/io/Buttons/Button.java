package io.Buttons;

import game.Game;
import game.enemies.Enemy;
import game.projectiles.Projectile;
import game.towers.Tower;
import io.Com.COM;

import java.util.ArrayList;

/**
 * Created by sander on 14-11-2017.
 */
public abstract class Button {
    public int PinNumber;
    COM com;
    Game game;
    ArrayList<Enemy> enemies = new ArrayList<>();
    ArrayList<Projectile> projectiles = new ArrayList<>();
    ArrayList<Tower> towers = new ArrayList<>();

    public Button(){
        PinNumber = 1;
    }
    public void setCom(COM com){
        this.com = com;
    }
    public void effect(){}
    public void setGame(){
        game = com.getGame();
    }
}
