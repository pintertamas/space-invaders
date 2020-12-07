package spaceInvaders;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Leteszteli az EnemyBullet update metódusát
 */
class EnemyBulletTest {

    @Test
    void update() {
        Bullet bullet = new EnemyBullet(100, 200, 30);
        assertEquals(200, bullet.getPosY());
        bullet.update(900);
        assertEquals(200 + bullet.speed, bullet.getPosY());
    }
}