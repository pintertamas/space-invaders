package spaceInvaders;

import javafx.scene.Scene;

import java.io.*;

/**
 * Célja a játékok elmentése és visszatöltése szerializált fájlból
 */
public class Database {

    /**
     * Visszatölti a játékot a game.ser fájlból
     * Ha nincsen még ilyen, akkor létrehoz egy új játékot a
     * @param scene az új játék Scene-je
     * @param screenWidth a képernyő szélessége
     * @param screenHeight a képernyő magassága
     * @return a game.ser-ben tárolt játék, vagy egy új játék a fentebb említett paraméterekkel
     */
    public Game loadGame(Scene scene, int screenWidth, int screenHeight) {
        File f = new File("game.ser");
        if (f.exists()) {
            try {
                Game tmp;
                FileInputStream fileIn = new FileInputStream("game.ser");
                ObjectInputStream in = new ObjectInputStream(fileIn);
                tmp = (Game) in.readObject();
                in.close();
                fileIn.close();
                return tmp;
            } catch (ClassNotFoundException | IOException i) {
                i.printStackTrace();
            }
        }
        return new Game(scene, screenWidth, screenHeight);
    }

    /**
     * Elmenti a játékot egy game.ser fájlba
     * @param game a játék amit el akarunk menteni
     */
    public void saveGame(Game game) {
        try {
            FileOutputStream fileOut = new FileOutputStream("game.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(game);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
}