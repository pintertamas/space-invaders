package spaceInvaders;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Game implements Serializable, BulletListener {
    private final Invaders invaders;
    private final Player player;
    private final Bullets bullets;
    private int currentLevel;
    private final int screenWidth;
    private final int screenHeight;
    private final Scene scene;
    private final ArrayList<ChangeWindow> windowListeners;

    public Game(Scene scene, int screenWidth, int screenHeight) {
        this.currentLevel = 1;
        this.invaders = new Invaders();
        this.player = new Player(screenWidth / 2.f - 25, screenHeight - 60, 50);
        player.addBulletListener(this);
        this.bullets = new Bullets();
        this.windowListeners = new ArrayList<>();
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
        drawBullets(gc);
        updateInvaders();
        updateBullets();
        try{
            Thread.sleep(10);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

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
            if (invader.isAlive()) {
                gc.drawImage(invader.getImg(), invader.getPosX(), invader.getPosY());
            }
        }
    }

    private void drawPlayer(GraphicsContext gc) {
        Image image = new Image("icons/player.png", player.getSize(), player.getSize(), true, true);
        gc.drawImage(image, player.getPosX(), player.getPosY());
    }

    private void drawBullets(GraphicsContext gc) {
        for (Bullet bullet : bullets.getBullets()) {
            Image image = new Image("icons/bullet.png", bullet.getSize(), bullet.getSize(), true, true);
            gc.drawImage(image, bullet.getPosX(), bullet.getPosY());
        }
    }

    private void movePlayer() {
        scene.setOnKeyPressed(keyEvent -> {
            KeyCode code = keyEvent.getCode();
            if (code == KeyCode.A && player.getPosX() - 10 >= 10) {
                player.moveLeft();
            } else if (code == KeyCode.D && player.getPosX() + player.getSize() + 10 < screenWidth - 10) {
                player.moveRight();
            } else if (code == KeyCode.SPACE) {
                player.shoot(screenHeight);
            }
        });
    }

    private void updateBullets() {
        if (bullets.getBullets().size() != 0)
            for (int i = 0; i < bullets.getBullets().size(); i++) {
                if (bullets.getBullets().get(i).isAlive)
                    bullets.getBullets().get(i).update(screenHeight);
                else {
                    bullets.removeBullet(bullets.bullets.get(i));
                }
            }
    }

    private void updateInvaders() {
        for (Invader invader : invaders.getInvaders()) {
            invader.update();
            for (Bullet bullet : bullets.getBullets()) {
                if (bulletInvaderCollision(bullet, invader)) {
                    bullet.die();
                    invader.die();
                }
            }
            if (playerInvaderCollision(invader)) {
                player.damage();
                System.out.println(player.getHealth());
                invader.die();
            }
        }
    }

    private boolean bulletInvaderCollision(Bullet bullet, Invader invader) {
        if (bullet.isAlive()) {
            return bullet.getPosX() > invader.getPosX() &&
                    bullet.getPosX() + bullet.getSize() < invader.getPosX() + invader.getSize() &&
                    bullet.getPosY() > invader.getPosY() &&
                    bullet.getPosY() + bullet.getSize() > invader.getPosY() + invader.getSize();
        }
        return false;
    }

    private boolean playerInvaderCollision(Invader invader) {
        return player.getPosX() > invader.getPosX() &&
                player.getPosX() + player.getSize() < invader.getPosX() + invader.getSize() &&
                player.getPosY() > invader.getPosY() &&
                player.getPosY() + player.getSize() > invader.getPosY() + invader.getSize();
    }

    public void addWindowListener(ChangeWindow listener) {
        windowListeners.add(listener);
    }

    private void menu() {
        for (ChangeWindow cw : windowListeners)
            cw.changeWindow(Main.State.menu);
    }

    @Override
    public void addBullet(Bullet bullet) {
        bullets.addBullet(bullet);
    }
}
