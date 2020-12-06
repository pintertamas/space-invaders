package spaceInvaders;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.Serializable;

public abstract class Bullet implements Serializable {
    protected float posX;
    protected float posY;
    protected int size;
    protected int speed;
    protected boolean isAlive;
    protected enum id {player, enemy};
    protected id bulletId;

    Bullet(float posX, float posY, int size, int speed) {
        this.isAlive = true;
        this.posX = posX;
        this.posY = posY;
        this.size = size;
        this.speed = speed;
    }

    public abstract void update(int screenHeight);

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

    public boolean isAlive() {
        return isAlive;
    }

    public int getSize() {
        return size;
    }

    public id getBulletId() {
        return bulletId;
    }

    public void die() {
        isAlive = false;
    }

    public void drawBullet(GraphicsContext gc) {
        Image image = new Image("icons/bullet.png", getSize(), getSize(), true, true);
        gc.drawImage(image, getPosX(), getPosY());
    }
}
