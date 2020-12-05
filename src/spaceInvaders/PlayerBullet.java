package spaceInvaders;

public class PlayerBullet extends Bullet {

    PlayerBullet(float posX, float posY, int size) {
        super(posX, posY, size);
    }

    @Override
    public void update(int screenHeight) {
        this.posY -= this.speed;
        this.killIfOutside(screenHeight);
    }
}
