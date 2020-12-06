package spaceInvaders;

import javafx.scene.canvas.GraphicsContext;

import java.io.Serializable;
import java.util.ArrayList;

public class Bullets implements Serializable {
    ArrayList<Bullet> bullets;

    public Bullets() {
        this.bullets  = new ArrayList<>();
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public void addBullet(Bullet bullet) {
        bullets.add(bullet);
    }

    public void removeBullet(Bullet bullet) {
        bullets.remove(bullet);
    }

    public void updateBullets(int screenHeight) {
        if (getBullets().size() != 0)
            for (int i = 0; i < getBullets().size(); i++) {
                if (getBullets().get(i).isAlive)
                    getBullets().get(i).update(screenHeight);
                else {
                    removeBullet(bullets.get(i));
                }
            }
    }

    public void drawBullets(GraphicsContext gc) {
        for (Bullet bullet : getBullets()) {
            bullet.drawBullet(gc);
        }
    }
}
