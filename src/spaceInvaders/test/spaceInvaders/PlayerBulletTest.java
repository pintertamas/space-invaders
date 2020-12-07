package spaceInvaders;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Leteszteli a PlayerBullet update metódusát
 */
class PlayerBulletTest {

    @Test
    void update() {
        Bullet bullet = new PlayerBullet(100, 200, 30);
        assertEquals(200, bullet.getPosY());
        bullet.update(900);
        assertEquals(200-bullet.speed, bullet.getPosY());
    }
}