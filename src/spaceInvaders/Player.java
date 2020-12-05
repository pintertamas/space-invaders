package spaceInvaders;

import java.io.Serializable;

public class Player extends Figure implements Serializable {

    private int health;

    public Player(float posX, float posY, int size) {
        super(posX, posY, size);
        health = 3;
    }

    public void damage(){
        this.health--;
    }

    public boolean isAbleToMove(int screenWidth) {
        return (this.getPosX() - 10 > 10 && this.getPosX() + 10 < screenWidth - 10);
    }
}
