package spaceInvaders;

import java.util.ArrayList;

public class Bullets {
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
}
