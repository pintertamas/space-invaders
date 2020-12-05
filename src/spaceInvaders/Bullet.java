package spaceInvaders;

public abstract class Bullet {
    protected float posX;
    protected float posY;
    protected int size;
    protected int speed;
    protected boolean isAlive;

    Bullet(float posX, float posY, int size, int screenHeight) {
        this.isAlive = true;
        this.posX = posX;
        this.posY = posY;
        this.size = size;
        this.speed = 30;
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

    public void die() {
        isAlive = false;
    }
}
