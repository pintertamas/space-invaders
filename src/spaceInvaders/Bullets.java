package spaceInvaders;

import javafx.scene.Group;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Ez az osztály tartalmazhat golyókat
 */
public class Bullets implements Serializable {
    final ArrayList<Bullet> bullets;

    /**
     * Konstruktor, ami létrehoz egy üres ArrayListet.
     */
    public Bullets() {
        this.bullets = new ArrayList<>();
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    /**
     * Hozzáad egy lövedéket a listához
     * @param bullet a lövedék amit hozzá adunk
     */
    public void addBullet(Bullet bullet) {
        bullets.add(bullet);
    }

    /**
     * Kitöröl egy lövedéket a listából
     * @param bullet a lövedék amit kitörlünk
     */
    public void removeBullet(Bullet bullet) {
        bullets.remove(bullet);
    }

    /**
     * Frissíti a lövedékeket. Ha élnek még akkor mozognak, ha nem, akkor kitörlésre kerülnek
     * @param screenHeight a képernyő magassága
     */
    public void updateBullets(int screenHeight) {
        if (getBullets().size() != 0) {
            for (int i = 0; i < getBullets().size(); i++) {
                if (getBullets().get(i).isAlive)
                    getBullets().get(i).update(screenHeight);
                else {
                    removeBullet(bullets.get(i));
                }
            }
        }
    }

    /**
     * Kitörli az összes golyót a listáról
     */
    public void removeBullets() {
        bullets.clear();
    }

    /**
     * Kitörli azokat a golyókat, amik ütköztek egymással
     */
    public void removeCollidingBullets() {
        for (Bullet bullet : bullets) {
            for (Bullet bullet2 : bullets) {
                bulletBulletCollision(bullet, bullet2);
            }
        }
    }

    /**
     * Visszaadja, hogy két golyó ütközik-e
     * @param bullet az egyik golyó
     * @param bullet2 a másik golyó
     * @return ütközik-e a két golyó
     */
    public boolean checkBulletBulletCollision(Bullet bullet, Bullet bullet2) {
        return bullet.getBulletId() != bullet2.getBulletId() && (((bullet.getPosX() > bullet2.getPosX() &&
                bullet.getPosX() < bullet2.getPosX() + bullet2.getSize()) ||
                (bullet.getPosX() + bullet.getSize() > bullet2.getPosX() && bullet.getPosX() + bullet.getSize() < bullet2.getPosX() + bullet2.getSize())) &&
                ((bullet.getPosY() > bullet2.getPosY() &&
                        bullet.getPosY() < bullet2.getPosY() + bullet2.getSize()) ||
                        (bullet.getPosY() + bullet.getSize() > bullet2.getPosY() && bullet.getPosY() + bullet.getSize() < bullet2.getPosY() + bullet2.getSize())));
    }

    /**
     * Ha két golyó ütközik, törli őket
     * @param bullet az egyik golyó
     * @param bullet2 a másik golyó
     */
    private void bulletBulletCollision(Bullet bullet, Bullet bullet2) {
        if (checkBulletBulletCollision(bullet, bullet2)) {
            bullet.die();
            bullet2.die();
        }
    }

    /**
     * Kirajzolja a golyókat
     * @param root a csoport amihez adjuk a golyókat
     */
    public void drawBullets(Group root) {
        for (Bullet bullet : getBullets()) {
            bullet.drawBullet(root);
        }
    }
}
