package spaceInvaders;

public class PlayerBullet extends Bullet {

    PlayerBullet(float posX, float posY, int size, int screenHeight) {
        super(posX, posY, size, screenHeight);
    }

    @Override
    public void update(int screenHeight) {
        this.posY -= this.speed;
        this.killIfOutside(screenHeight);
    }
}
