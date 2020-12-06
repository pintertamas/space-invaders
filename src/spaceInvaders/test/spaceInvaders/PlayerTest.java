package spaceInvaders;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    final Player player = new Player(100, 200, 10);

    @Test
    void createPlayer() {
        assertEquals(100, player.getPosX());
        assertEquals(200, player.getPosY());
        assertEquals(10, player.getSize());
        assertEquals(20, player.getSpeed());
        assertEquals(3, player.getHealth());
    }

    @Test
    void damagePlayer() {
        assertEquals(3, player.getHealth());
        player.damage();
        assertEquals(2, player.getHealth());
    }

    @Test
    void movePlayer() {
        assertEquals(100, player.getPosX());
        player.moveLeft();
        assertEquals(100-player.getSpeed(), player.getPosX());
        player.moveRight();
        assertEquals(100, player.getPosX());
    }
}