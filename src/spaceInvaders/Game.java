package spaceInvaders;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.io.Serializable;
import java.util.ArrayList;

public class Game implements Serializable, BulletListener {
    private Invaders invaders;
    private Player player;
    private Bullets bullets;
    private int currentLevel;
    private int screenWidth;
    private int screenHeight;
    private transient Scene scene;
    private ArrayList<ChangeWindow> windowListeners;

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
        invaders.spawnInvaders(screenWidth);
        invaders.addBulletListeners(this);
    }

    public void setGame(Game game, Scene scene) {
        this.currentLevel = game.getCurrentLevel();
        this.invaders = game.getInvaders();
        this.player = game.getPlayer();
        this.bullets = game.getBullets();
        this.windowListeners = new ArrayList<>();
        this.screenWidth = game.getScreenWidth();
        this.screenHeight = game.getScreenHeight();
        this.scene = scene;
        this.invaders.loadInvaders();
        player.clearListeners();
        player.addBulletListener(this);
        invaders.removeBulletListeners();
        invaders.addBulletListeners(this);
    }

    public void showGame(Group root, Canvas canvas, GraphicsContext gc) {
        updateLevel();
        setBackground(root, canvas, gc);
        saveButton(root);
        player.changeDirection(scene, screenWidth);
        player.movePlayer(screenWidth, screenHeight);
        player.drawPlayer(root);
        invaders.killIfDead();
        invaders.killIfOutside(screenHeight);
        invaders.drawInvaders(root);
        bullets.drawBullets(gc);
        updateInvaders();
        bullets.updateBullets(screenHeight);
        bullets.removeCollidingBullets();
        drawHealth(root);
        /*try {
            Thread.sleep(2);
        } catch (Exception ex) {
            ex.printStackTrace();
        }*/
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

    private void drawHealth(Group root) {
        HBox life = new HBox(screenWidth / 50.f);
        Image image = new Image("icons/health.png", screenWidth / 20.f, screenWidth / 20.f, true, true);

        if (player.getHealth() > 0) {
            ImageView imageView1 = new ImageView(image);
            imageView1.setY(screenHeight / 200.f);
            imageView1.setX(0);
            life.getChildren().add(imageView1);
        }
        if (player.getHealth() > 1) {
            ImageView imageView2 = new ImageView(image);
            imageView2.setY(screenHeight / 200.f);
            imageView2.setX(screenWidth / 10.f + screenWidth / 100.f);
            life.getChildren().add(imageView2);
        }
        if (player.getHealth() > 2) {
            ImageView imageView3 = new ImageView(image);
            imageView3.setY(screenHeight / 200.f);
            imageView3.setX(2 * screenWidth / 10.f + 2 * screenWidth / 100.f);
            life.getChildren().add(imageView3);
        }
        VBox lifeContainer = new VBox();
        lifeContainer.setMinHeight(screenHeight / 10.f);
        lifeContainer.setAlignment(Pos.CENTER);
        life.setAlignment(Pos.CENTER);
        life.setMinWidth(screenWidth / 3.f);
        lifeContainer.getChildren().add(life);
        root.getChildren().addAll(lifeContainer);
    }

    private void saveButton(Group root) {
        Button saveButton = new Button("SAVE");
        saveButton.setId("saveButton");
        HBox buttonAlignment = new HBox();
        buttonAlignment.getChildren().add(saveButton);
        buttonAlignment.setAlignment(Pos.CENTER);
        buttonAlignment.setMinWidth(screenWidth);
        saveButton.setOnMousePressed(keyEvent -> changeWindow(Main.State.menu));
        root.getChildren().add(buttonAlignment);
    }

    private void updateInvaders() {
        invaders.moveInvadersSideways(screenWidth);
        for (Invader invader : invaders.getInvaders()) {
            invader.update();
            for (Bullet bullet : bullets.getBullets()) {
                if (bullet.isAlive && bulletInvaderCollision(bullet, invader)) {
                    bullet.die();
                    invader.die();
                }
                if (bullet.isAlive && bulletPlayerCollision(bullet, player)) {
                    bullet.die();
                    player.damage();
                    if (player.getHealth() <= 0) {
                        changeWindow(Main.State.gameOver);
                    }
                }
            }
            if (playerInvaderCollision(invader) && invader.isAlive()) {
                player.damage();
                invader.die();
            }
        }
    }

    private boolean bulletInvaderCollision(Bullet bullet, Invader invader) {
        return bullet.getBulletId() == Bullet.id.player && (((bullet.getPosX() > invader.getPosX() &&
                bullet.getPosX() < invader.getPosX() + invader.getSize()) ||
                (bullet.getPosX() + bullet.getSize() > invader.getPosX() && bullet.getPosX() + bullet.getSize() < invader.getPosX() + invader.getSize())) &&
                bullet.getPosY() < invader.getPosY() + invader.getSize());
    }

    private boolean bulletPlayerCollision(Bullet bullet, Player player) {
        return bullet.getBulletId() == Bullet.id.enemy && (((bullet.getPosX() > player.getPosX() &&
                bullet.getPosX() < player.getPosX() + player.getSize()) ||
                (bullet.getPosX() + bullet.getSize() > player.getPosX() && bullet.getPosX() + bullet.getSize() < player.getPosX() + player.getSize())) &&
                bullet.getPosY() + bullet.getSize() > player.getPosY());
    }

    private boolean playerInvaderCollision(Invader invader) {
        return player.getPosX() > invader.getPosX() &&
                player.getPosX() + player.getSize() < invader.getPosX() + invader.getSize() &&
                player.getPosY() > invader.getPosY() &&
                player.getPosY() + player.getSize() > invader.getPosY() + invader.getSize();
    }

    private void updateLevel() {
        if (invaders.getInvaders().size() == 0 && currentLevel != 5) {
            currentLevel++;
            invaders.spawnInvaders(screenWidth);
            invaders.addBulletListeners(this);
        } else if (currentLevel == 5 && invaders.getInvaders().size() == 0) {
            currentLevel = 1;
            changeWindow(Main.State.win);
        }
    }

    public void addWindowListener(ChangeWindow listener) {
        windowListeners.add(listener);
    }

    private void changeWindow(Main.State state) {
        if (player.getHealth() <= 0) {
            new Database().saveGame(new Game(scene, screenWidth, screenHeight));
            player.setShooting(false);
        } else {
            new Database().saveGame(this);
        }
        for (ChangeWindow cw : windowListeners)
            cw.changeWindow(state);
    }

    @Override
    public void addBullet(Bullet bullet) {
        bullets.addBullet(bullet);
    }

    public Invaders invaders() {
        return invaders;
    }

    public Invaders getInvaders() {
        return invaders;
    }

    public Player getPlayer() {
        return player;
    }

    public Bullets getBullets() {
        return bullets;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public Scene getScene() {
        return scene;
    }
}
