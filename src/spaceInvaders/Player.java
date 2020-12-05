package spaceInvaders;

import java.io.Serializable;

public class Player extends Figure implements Serializable {

    private int health;
    private final int speed;

    public Player(float posX, float posY, int size) {
        super(posX, posY, size);
        health = 3;
        speed = 25;
    }

    public void damage(){
        this.health--;
    }

    public void moveLeft() {
        setPosX(getPosX() - getSpeed());
    }

    public void moveRight() {
        setPosX(getPosX() + getSpeed());
    }

    public int getHealth() {
        return health;
    }

    public int getSpeed() {
        return speed;
    }

    @Override
    protected void shoot(Bullet bullet) {
        for (BulletListener bl : bulletListeners)
            bl.addBullet(bullet);
    }
}
