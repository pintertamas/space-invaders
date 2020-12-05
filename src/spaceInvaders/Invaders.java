package spaceInvaders;

import java.io.Serializable;
import java.util.ArrayList;

public class Invaders implements Serializable {
    ArrayList<Invader> invaders;

    public Invaders() {
        this.invaders = new ArrayList<>();
    }

    public void addInvader(Invader invader) {
        this.invaders.add(invader);
    }

    public ArrayList<Invader> getInvaders() {
        return invaders;
    }
}
