package spaceInvaders;

/**
 * Listener, aminek a feladata a képernyők közötti lépkedés
 * (a Main függvényben változtatja a state értékét)
 */
public interface ChangeWindow {
    void changeWindow(Main.State state);
}
