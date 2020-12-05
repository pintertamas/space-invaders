package spaceInvaders;

public class PlayerBullet extends Bullet {

    PlayerBullet(float posX, float posY, int size) {
        super(posX, posY, size);
    }

    @Override
    public void update() {
        this.posY -= 10;
    }
}
