package spaceInvaders;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class EnemyBullet extends Bullet {

    EnemyBullet(float posX, float posY, int size) {
        super(posX, posY, size);
    }

    @Override
    public void update(int screenHeight) {
        this.posY += this.speed;
        this.killIfOutside(screenHeight);
    }

    @Override
    public void drawBullet(GraphicsContext gc) {
        Image image = new Image("icons/bullet_enemy.png", getSize(), getSize(), true, true);
        gc.drawImage(image, getPosX(), getPosY());
    }
}
