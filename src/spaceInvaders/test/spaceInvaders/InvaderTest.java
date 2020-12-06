package spaceInvaders;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InvaderTest {

    Invader invader = new Invader(100, 200, 30);

    @Test
    void addInvader() {
        assertEquals(100, invader.getPosX());
        assertEquals(200, invader.getPosY());
        assertEquals(30, invader.getSize());
        assertEquals(0.5, invader.getSpeed());
    }
}