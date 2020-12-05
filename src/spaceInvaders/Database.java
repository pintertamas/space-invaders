package spaceInvaders;

import javafx.scene.Scene;

import java.io.*;

public class Database {

    public Game loadGame(Scene scene, int screenWidth, int screenHeight) {
        File f = new File("game.ser");
        if (f.exists()) {
            try {
                Game game;
                FileInputStream fileIn = new FileInputStream("game.ser");
                ObjectInputStream in = new ObjectInputStream(fileIn);
                game = (Game) in.readObject();
                in.close();
                fileIn.close();
                return game;
            } catch (ClassNotFoundException | IOException i) {
                i.printStackTrace();
            }
        }
        return new Game(scene, screenWidth, screenHeight);
    }

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