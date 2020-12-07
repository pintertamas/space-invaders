package spaceInvaders;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.Serializable;

/**
 * Ez az osztály a lövedékeket tárolja
 * Azért abstract, mert van egy abstract update(int) függvénye
 */
public abstract class Bullet implements Serializable {
    protected final float posX;
    protected float posY;
    protected final int size;
    protected final int speed;
    protected boolean isAlive;

    /**
     * A golyó typusa (ellenséges, vagy a játékosé-e)
     */
    protected enum id {player, enemy}

    protected id bulletId;

    /**
     * A golyók konstruktora. Az alábbi paraméterek mellett beállítja, hogy a golyó "élve" jöjjön létre
     * @param posX a golyó X pozíciója
     * @param posY a golyó X y pozíciója
     * @param size a golyó mérete
     * @param speed a golyó sebessége
     */
    Bullet(float posX, float posY, int size, int speed) {
        this.isAlive = true;
        this.posX = posX;
        this.posY = posY;
        this.size = size;
        this.speed = speed;
    }

    /**
     * Ez a függvény frissíti a golyókat minden lefutásnál
     * @param screenHeight a képernyő magassága
     */
    public abstract void update(int screenHeight);

    /**
     * Megöli a golyót, ha az a képernyőn kívülre kerül
     * @param screenHeight a képernyő magassága
     */
    protected void killIfOutside(int screenHeight) {
        if (this.posY < 0 || this.posY > screenHeight)
            this.die();
    }

    public float getPosX() {
        return posX;
    }

    public float getPosY() {
        return posY;
    }

    public int getSize() {
        return size;
    }

    public id getBulletId() {
        return bulletId;
    }

    /**
     * Megöli a golyót
     */
    public void die() {
        isAlive = false;
    }

    /**
     * Kirajzolja az adott lövedéket a helyére
     * (Arra az esetre van, ha nem adnánk meg, hogy ellenséges, vagy játékos lövedéket lövünk ki)
     * @param root ehhez a csoporthoz adja hozzá
     */
    public abstract void drawBullet(Group root);
}
