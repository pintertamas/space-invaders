package spaceInvaders;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.Serializable;

public class EnemyBullet extends Bullet implements Serializable {

    EnemyBullet(float posX, float posY, int size) {
        super(posX, posY, size, 5);
        this.bulletId = id.enemy;
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
