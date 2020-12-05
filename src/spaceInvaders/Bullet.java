package spaceInvaders;

public abstract class Bullet {
    protected float posX;
    protected float posY;
    protected int size;
    protected boolean isAlive;

    Bullet(float posX, float posY, int size) {
        isAlive = true;
        this.posX = posX;
        this.posY = posY;
        this.size = size;
    }

    public abstract void update();

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

    public void die() {
        isAlive = false;
    }
}
