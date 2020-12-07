package spaceInvaders;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A játékban levő figurákat tárolja
 */
public class Figure implements Serializable {
    private float posX;
    private float posY;
    private final int size;
    protected final ArrayList<BulletListener> bulletListeners;

    /**
     * Konstruktor a figurákhoz
     * @param posX a figura X pozíciója
     * @param posY a figura Y pozíciója
     * @param size a figura mérete
     */
    public Figure(float posX, float posY, int size) {
        this.posX = posX;
        this.posY = posY;
        this.size = size;
        this.bulletListeners = new ArrayList<>();
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

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

    /**
     * Hozzáad egy BulletListenert a listenerek listájához
     * @param listener ezt adja hozzá
     */
    public void addBulletListener(BulletListener listener) {
        bulletListeners.add(listener);
    }

    /**
     * Lő egyet a figura
     * @param bullet ezt a lövedéket lövi
     */
    protected void shoot(Bullet bullet) {}

    /**
     * Kitörli a listenereket
     */
    public void clearListeners() {
        bulletListeners.clear();
    }
}
