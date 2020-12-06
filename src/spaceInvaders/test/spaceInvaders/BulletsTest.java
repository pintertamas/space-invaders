package spaceInvaders;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BulletsTest {

    Bullet bullet1 = new PlayerBullet(100, 100, 30);
    Bullet bullet2 = new PlayerBullet(100, 100, 30);
    Bullet bullet3 = new EnemyBullet(120, 129, 30);
    Bullet bullet4 = new EnemyBullet(200, 200, 30);

    @Test
    void bulletBulletCollision() {
        Bullets bullets = new Bullets();
        assertFalse(bullets.checkBulletBulletCollision(bullet1, bullet2));
        assertTrue(bullets.checkBulletBulletCollision(bullet1, bullet3));
        assertFalse(bullets.checkBulletBulletCollision(bullet1, bullet4));
    }

    @Test
    void addBullets() {
        Bullets bullets = new Bullets();
        assertEquals(0, bullets.getBullets().size());
        bullets.addBullet(bullet1);
        bullets.addBullet(bullet2);
        bullets.addBullet(bullet3);
        bullets.addBullet(bullet4);
        assertEquals(4, bullets.getBullets().size());
    }

    @Test
    void removeBullets() {
        Bullets bullets = new Bullets();
        bullets.addBullet(bullet1);
        assertEquals(1, bullets.getBullets().size());
        bullets.removeBullet(bullet1);
        assertEquals(0, bullets.getBullets().size());
        bullets.addBullet(bullet1);
        bullets.addBullet(bullet2);
        bullets.addBullet(bullet3);
        bullets.addBullet(bullet4);
        bullets.removeBullets();
        assertEquals(0, bullets.getBullets().size());
    }
}