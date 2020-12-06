package spaceInvaders;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FigureTest {
    Figure figure = new Figure(100, 200, 30);

    @Test
    void addInvader() {
        assertEquals(100, figure.getPosX());
        assertEquals(200, figure.getPosY());
        assertEquals(30, figure.getSize());
    }
}