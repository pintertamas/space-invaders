package spaceInvaders;

import javafx.scene.canvas.GraphicsContext;

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

    public void killIfDead() {
        for (int i = 0; i < invaders.size(); i++) {
            if (!invaders.get(i).isAlive()) {
                invaders.remove(invaders.get(i));
            }
        }
    }

    public void killIfOutside(int screenHeight) {
        for (int i = 0; i < invaders.size(); i++) {
            if (invaders.get(i).getPosY() > screenHeight) {
                invaders.remove(invaders.get(i));
            }
        }
    }

    public void spawnInvaders(int screenWidth) {
        for (int i = 0; i < 11; i++) {
            addInvader(new Invader(screenWidth / 11.f * i, 100, 40));
        }
    }

    public void drawInvaders(GraphicsContext gc) {
        for (Invader invader : getInvaders()) {
            invader.drawInvader(gc);
        }
    }

    public void addBulletListeners(BulletListener bulletListener) {
        for (Invader invader : getInvaders()) {
            invader.addBulletListener(bulletListener);
        }
    }
}
