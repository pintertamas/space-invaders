package spaceInvaders;

import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;

import java.io.Serializable;
import java.util.ArrayList;

public class Invaders implements Serializable {
    ArrayList<Invader> invaders;
    private boolean direction;
    private final float speed;

    public Invaders() {
        this.invaders = new ArrayList<>();
        direction = true;
        this.speed = 0.5f;
    }

    public void addInvader(Invader invader) {
        this.invaders.add(invader);
    }

    public void moveInvadersSideways(int screenWidth) {
        float leftOne = screenWidth + 1;
        float rightOne = -1;
        int leftIdx = 0;
        int rightIdx = 0;
        for (int i = 0; i < invaders.size(); i++) {
            if (invaders.get(i).getPosX() < leftOne) {
                leftOne = invaders.get(i).getPosX();
                leftIdx = i;
            }
            if (invaders.get(i).getPosX() + invaders.get(i).getSize() > rightOne) {
                rightOne = invaders.get(i).getPosX();
                rightIdx = i;
            }
        }
        for (Invader invader : invaders) {
            if (direction && invaders.get(rightIdx).getPosX() + invader.getSize() + invader.getSpeed() < screenWidth) {
                invader.setPosX(invader.getPosX() + speed);
            } else if (!direction && invaders.get(leftIdx).getPosX() - invader.getSpeed() > 0) {
                invader.setPosX(invader.getPosX() - speed);
            } else direction = !direction;
        }
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
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 11; j++) {
                addInvader(new Invader(screenWidth / 11.f * j, 100 + i * 35, 30));
            }
    }

    public void loadInvaders() {
        for (Invader invader : invaders) {
            invader.addImageToInvader();
        }
    }

    public void drawInvaders(Group root) {
        for (Invader invader : getInvaders()) {
            invader.drawInvader(root);
        }
    }

    public void addBulletListeners(BulletListener bulletListener) {
        for (Invader invader : getInvaders()) {
            invader.addBulletListener(bulletListener);
        }
    }

    public void removeBulletListeners() {
        for (Invader invader : getInvaders()) {
            invader.clearListeners();
        }
    }
}
