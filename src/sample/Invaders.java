package sample;

import java.util.ArrayList;

public class Invaders {
    ArrayList<Invader> invaders;

    public void addInvader(Invader invader) {
        this.invaders.add(invader);
    }

    public ArrayList<Invader> getInvaders() {
        return invaders;
    }
}
