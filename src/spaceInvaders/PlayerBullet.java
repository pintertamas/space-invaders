package spaceInvaders;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class PlayerBullet extends Bullet {

    PlayerBullet(float posX, float posY, int size) {
        super(posX, posY, size);
    }

    @Override
    public void update(int screenHeight) {
        this.posY -= this.speed;
        this.killIfOutside(screenHeight);
    }

    @Override
    public void drawBullet(GraphicsContext gc) {
        Image image = new Image("icons/bullet.png", getSize(), getSize(), true, true);
        gc.drawImage(image, getPosX(), getPosY());
    }
}
