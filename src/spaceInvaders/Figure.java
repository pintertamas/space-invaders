package spaceInvaders;

public class Figure {
    private float posX;
    private float posY;
    private int size;

    public Figure(float posX, float posY, int size) {
        this.posX = posX;
        this.posY = posY;
        this.size = size;
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

    protected void shoot(boolean direction) {

    }
}
