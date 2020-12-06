package spaceInvaders;

import java.io.Serializable;
import java.util.ArrayList;

public class Figure implements Serializable {
    private float posX;
    private float posY;
    private final int size;
    protected final ArrayList<BulletListener> bulletListeners;

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

    public void addBulletListener(BulletListener listener) {
        bulletListeners.add(listener);
    }

    protected void shoot(Bullet bullet) {}

    public void clearListeners() {
        bulletListeners.clear();
    }
}
