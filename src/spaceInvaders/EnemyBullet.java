package spaceInvaders;

public class EnemyBullet extends Bullet {

    EnemyBullet(float posX, float posY, int size, int screenHeight) {
        super(posX, posY, size, screenHeight);
    }

    @Override
    public void update(int screenHeight) {
        this.posY += this.speed;
        this.killIfOutside(screenHeight);
    }
}
