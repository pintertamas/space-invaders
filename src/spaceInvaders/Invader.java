package spaceInvaders;

import java.io.Serializable;

public class Invader extends Figure implements Serializable {
    private boolean isAlive;

    public Invader(float posX, float posY, int size) {
        super(posX, posY, size);
        this.isAlive = true;
    }

    public void update() {
        this.setPosY(getPosY()+5);
    }

    public void die() {
        this.isAlive = false;
    }

    public boolean isAlive() {
        return isAlive;
    }
}
