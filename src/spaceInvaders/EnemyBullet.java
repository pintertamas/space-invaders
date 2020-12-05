package spaceInvaders;

public class EnemyBullet extends Bullet {

    EnemyBullet(float posX, float posY, int size) {
        super(posX, posY, size);
    }

    @Override
    public void update() {
        this.posY += 10;
    }
}
