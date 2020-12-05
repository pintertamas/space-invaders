package sample;

import java.io.Serializable;

public class Player extends Figure implements Serializable {
    public Player(float posX, float posY, int size) {
        super(posX, posY, size);
    }
}
