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

    public void setPosY(float posY) {
        this.posY = posY;
    }

    protected void shoot(boolean direction) {
        if (direction) {
            System.out.println("true");
        } else {
            System.out.println("false");
        }
    }
}
