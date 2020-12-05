package spaceInvaders;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.io.Serializable;
import java.util.ArrayList;

public class Game implements Serializable {
    private Invaders invaders;
    private Player player;
    private int currentLevel;
    private final int screenWidth;
    private final int screenHeight;
    private Scene scene;
    private final ArrayList<ChangeWindow> listeners;

    public Game(Scene scene, int screenWidth, int screenHeight) {
        this.currentLevel = 1;
        this.invaders = new Invaders();
        this.player = new Player(screenWidth / 2.f - 25, screenHeight - 60, 50);
        this.listeners = new ArrayList<>();
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.scene = scene;
        spawnInvaders();
    }

    public void showGame(Group root, Canvas canvas, GraphicsContext gc) {
        setBackground(root, canvas, gc);
        movePlayer();
        drawPlayer(gc);
        drawInvaders(gc);
    }

    private void setBackground(Group root, Canvas canvas, GraphicsContext gc) {
        root.getChildren().clear();
        gc.setFill(Color.rgb(30, 30, 30));
        gc.fillRect(0, 0, screenWidth, screenHeight);
        root.getChildren().add(canvas);

        for (int i = 0; i < screenHeight; i += screenHeight / 10) {
            Line line = new Line(0, i, screenWidth, i);
            line.setId("line");
            line.setOpacity(0.2 * (float) screenHeight / (screenHeight - i));
            root.getChildren().add(line);
        }

        for (float i = screenWidth / 3.f; i < screenWidth; i += screenWidth / 100.f) {
            float tmp = (screenWidth / 3.f / (i - screenWidth / 3.f)) * screenWidth;
            Line line = new Line(i, 0, (screenWidth * 2.5 - tmp), screenHeight);
            line.setId("lineVertical");
            root.getChildren().add(line);
        }
    }

    private void spawnInvaders() {
        for (int i = 0; i < 11; i++) {
            invaders.addInvader(new Invader(screenWidth / 11.f * i, 100, 40));
        }
    }

    private void drawInvaders(GraphicsContext gc) {
        for (Invader invader : invaders.getInvaders()) {
            Image alien1 = new Image("icons/alien2.png", invader.getSize(), invader.getSize(), true, true);
            gc.drawImage(alien1, invader.getPosX(), invader.getPosY());
        }
    }

    private void drawPlayer(GraphicsContext gc) {
        Image image = new Image("icons/player.png", player.getSize(), player.getSize(), true, true);
        gc.drawImage(image, player.getPosX(), player.getPosY());
    }

    private void movePlayer() {
        scene.setOnKeyPressed(keyEvent -> {
            KeyCode code = keyEvent.getCode();
            if (code == KeyCode.A && player.isAbleToMove(screenWidth)) {
                player.setPosX(player.getPosX() - 10);
            } else if (code == KeyCode.D && player.isAbleToMove(screenWidth)) {
                player.setPosX(player.getPosX() + 10);
            }
            if (code == KeyCode.SPACE) {
                player.shoot(true);
            }
        });
    }

    public void addListener(ChangeWindow listener) {
        listeners.add(listener);
    }

    private void menu() {
        for (ChangeWindow cw : listeners)
            cw.changeWindow(Main.State.menu);
    }
}
