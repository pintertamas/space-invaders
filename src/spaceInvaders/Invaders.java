package spaceInvaders;

import javafx.scene.Group;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Ez a class Invadereket tud tárolni, és azokon műveleteket végrehajtani
 */
public class Invaders implements Serializable {
    final ArrayList<Invader> invaders;
    private boolean direction;
    private final float speed;

    /**
     * Az invaderek konstruktora
     */
    public Invaders() {
        this.invaders = new ArrayList<>();
        direction = true;
        this.speed = 0.5f;
    }

    /**
     * Hozzáad egy invadert a listához
     * @param invader ezt adja hozzá
     */
    public void addInvader(Invader invader) {
        this.invaders.add(invader);
    }

    /**
     * Mozgatja az invadereket oldalra
     * Ehhez megkeresi a legszélső invadereket és azokhoz igazítja a csoport mozgását
     * @param screenWidth ez a képernyő szélessége
     */
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

    /**
     * Leszedi a listáról azt az invadert, amelyik meghalt már
     */
    public void removeIfDead() {
        for (int i = 0; i < invaders.size(); i++) {
            if (!invaders.get(i).isAlive()) {
                invaders.remove(invaders.get(i));
            }
        }
    }

    /**
     * Megöli azt az invadert, ami a képernyőn kívül van
     * @param screenHeight a képernyő magassága
     */
    public void killIfOutside(int screenHeight) {
        for (Invader invader : invaders) {
            if (invader.getPosY() > screenHeight) {
                invader.die();
            }
        }
    }

    /**
     * Létrehoz 11x5 invadert egyenletesen
     * @param screenWidth ez a képernyő szélessége
     */
    public void spawnInvaders(int screenWidth) {
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 11; j++) {
                Invader invader = new Invader(screenWidth / 11.f * j, 100 + i * 35, 30);
                invader.addImageToInvader();
                addInvader(invader);
            }
    }

    /**
     * Képet ad az invadereknek
     */
    public void loadInvaders() {
        for (Invader invader : invaders) {
            invader.addImageToInvader();
        }
    }

    /**
     * Felrajzolja az invadereket a képernyőre
     * @param root ehhez a csoporthoz adja hozzá
     */
    public void drawInvaders(Group root) {
        for (Invader invader : getInvaders()) {
            invader.drawInvader(root);
        }
    }

    /**
     * Hozzáad egy BulletListenert az összes invaderhez
     * @param bulletListener ezt a BulletListenert adja hozzá
     */
    public void addBulletListeners(BulletListener bulletListener) {
        for (Invader invader : getInvaders()) {
            invader.addBulletListener(bulletListener);
        }
    }

    /**
     * Kitörli a bulletListenerjeit az invadereknek
     */
    public void removeBulletListeners() {
        for (Invader invader : getInvaders()) {
            invader.clearListeners();
        }
    }
}
