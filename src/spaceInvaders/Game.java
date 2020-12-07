package spaceInvaders;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Ez az osztály valósítja meg a játék menetét
 */
public class Game implements Serializable, BulletListener {
    private Invaders invaders;
    private Player player;
    private Bullets bullets;
    private int currentLevel;
    private int screenWidth;
    private int screenHeight;
    private transient Scene scene;
    private ArrayList<ChangeWindow> windowListeners;

    /**
     * Ez a játék konstruktora
     * Meghívásakor létrehozza a fontos entitásokat, mint például az invadereket, a játékost, és a lövedékeket
     * Ezek mellett "feliratkozik" a lövések figyelésére, azaz ha a player vagy valamelyik invader lő egyet, akkor ez az osztály tudni fog róla
     * @param scene ez a játék Scene-je
     * @param screenWidth a képernyő szélessége
     * @param screenHeight a képernyő magassága
     */
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

    /**
     * Ez a függvény beállítja a játék értékeit a megadott játék értékeire
     * @param game ennek az értékeire állítja be
     * @param scene ez a Scene amit használ
     */
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

    /**
     * Ez a függvény fut a játékciklusban
     * @param root ez a csoport amire rakja a dolgokat
     * @param canvas ez a canvas
     * @param gc ezt használja a háttér egyszínűre festéséhez
     */
    public void showGame(Group root, Canvas canvas, GraphicsContext gc) {
        updateLevel();
        setBackground(root, canvas, gc);
        player.changeDirection(scene, screenWidth);
        player.movePlayer(screenWidth, screenHeight);
        player.drawPlayer(root);
        invaders.killIfDead();
        invaders.killIfOutside(screenHeight);
        bullets.drawBullets(root);
        updateInvaders();
        invaders.drawInvaders(root);
        bullets.updateBullets(screenHeight);
        bullets.removeCollidingBullets();
        drawHealth(root);
        drawCurrentLevel(root);
        saveButton(root);
        /*try {
            Thread.sleep(5);
        } catch (Exception ex) {
            ex.printStackTrace();
        }*/
    }

    /**
     * Kitörli a képernyő elemeit és lefesti egyszínűre a hátteret, majd vonalakat rajzol rá nagyon király módon
     * @param root ennek a csoportnak az elemeit törli
     * @param canvas erre a canvasra fest
     * @param gc ezzel a GraphicsContext-tel rajzol téglalapot
     */
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

    /**
     * Felírja a képernyőre a játékos életét
     * @param root ehhez a csoporthoz adja hozzá
     */
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

    /**
     * Felrak a képernyőre egy gombot, aminek a megnyomása elmenti a játékot és visszavisz a menübe
     * @param root ehhez a csoporthoz adja hozzá
     */
    private void saveButton(Group root) {
        Button saveButton = new Button("SAVE");
        saveButton.setId("saveButton");
        saveButton.setOnMousePressed(keyEvent -> changeWindow(Main.State.menu));

        HBox buttonAlignment = new HBox();
        buttonAlignment.setAlignment(Pos.CENTER);
        buttonAlignment.setMinWidth(screenWidth);
        buttonAlignment.getChildren().add(saveButton);

        VBox buttonVBox = new VBox();
        buttonVBox.setMinHeight(screenHeight / 10.f);
        buttonVBox.setAlignment(Pos.CENTER);
        buttonVBox.getChildren().add(buttonAlignment);

        root.getChildren().add(buttonVBox);
    }

    /**
     * Felírja a képernyőre az aktuális szintet
     * @param root ehhez a csoporthoz adja hozzá
     */
    private void drawCurrentLevel(Group root) {
        Text currentLevelText = new Text("LEVEL: " + currentLevel + "/5\t");
        currentLevelText.setId("levelText");

        HBox levelAlignment = new HBox();
        levelAlignment.getChildren().add(currentLevelText);

        BorderPane levelContainer = new BorderPane();
        levelContainer.setRight(currentLevelText);
        levelContainer.setMinWidth(screenWidth);

        VBox levelVBox = new VBox();
        levelVBox.setMinHeight(screenHeight / 10.f);
        levelVBox.getChildren().add(levelContainer);
        levelVBox.setAlignment(Pos.CENTER);

        root.getChildren().add(levelVBox);
    }

    /**
     * Frissíti az invaderek pozícióját, adott esetben pedig sebzi a játékost
     */
    private void updateInvaders() {
        invaders.moveInvadersSideways(screenWidth);
        for (Invader invader : invaders.getInvaders()) {
            invader.update();
            for (int i = 0; i < bullets.getBullets().size(); i++) {
                if (bullets.getBullets().get(i).isAlive && bulletInvaderCollision(bullets.getBullets().get(i), invader)) {
                    bullets.getBullets().get(i).die();
                    invader.die();
                }
                if (bullets.getBullets().get(i).isAlive && bulletPlayerCollision(bullets.getBullets().get(i))) {
                    bullets.getBullets().get(i).die();
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

    /**
     * Visszatér azzal, hogy eltalálta-e az invadert a golyó
     * Feltéve hogy a golyó a játékosé
     * @param bullet a golyó
     * @param invader az invader
     * @return ütköztek-e
     */
    public boolean bulletInvaderCollision(Bullet bullet, Invader invader) {
        return bullet.getBulletId() == Bullet.id.player && (((bullet.getPosX() > invader.getPosX() &&
                bullet.getPosX() < invader.getPosX() + invader.getSize()) ||
                (bullet.getPosX() + bullet.getSize() > invader.getPosX() && bullet.getPosX() + bullet.getSize() < invader.getPosX() + invader.getSize())) &&
                bullet.getPosY() < invader.getPosY() + invader.getSize());
    }

    /**
     * Visszaadja, hogy eltalálta-e a játékost a golyó
     * Feltéve hogy a golyó ellenséges
     * @param bullet a golyó
     * @return ütköztek-e
     */
    public boolean bulletPlayerCollision(Bullet bullet) {
        return bullet.getBulletId() == Bullet.id.enemy && (((bullet.getPosX() > player.getPosX() &&
                bullet.getPosX() < player.getPosX() + player.getSize()) ||
                (bullet.getPosX() + bullet.getSize() > player.getPosX() && bullet.getPosX() + bullet.getSize() < player.getPosX() + player.getSize())) &&
                bullet.getPosY() + bullet.getSize() > player.getPosY());
    }

    /**
     * Visszaadja, hogy a játékos ütközik-e az invaderrel
     * @param invader az invader
     * @return ütköztek-e
     */
    public boolean playerInvaderCollision(Invader invader) {
        return player.getPosX() > invader.getPosX() &&
                player.getPosX() + player.getSize() < invader.getPosX() + invader.getSize() &&
                player.getPosY() > invader.getPosY() &&
                player.getPosY() + player.getSize() > invader.getPosY() + invader.getSize();
    }

    /**
     * Ha elfogytak az invaderek frissíti a szintet, vagy átvált a WinScreen-re
     */
    private void updateLevel() {
        if (invaders.getInvaders().size() == 0 && currentLevel != 5) {
            currentLevel++;
            bullets.removeBullets();
            invaders.spawnInvaders(screenWidth);
            invaders.addBulletListeners(this);
        } else if (currentLevel == 5 && invaders.getInvaders().size() == 0) {
            currentLevel = 1;
            changeWindow(Main.State.win);
        }
    }

    /**
     * Hozzáad egy ChangeWindow-t a listenerekhez
     * @param listener ezt adja hozzá
     */
    public void addWindowListener(ChangeWindow listener) {
        windowListeners.add(listener);
    }

    /**
     * Kitörli a listenereket
     */
    public void clearWindowListeners() {
        windowListeners.clear();
    }

    /**
     * Szól a listenereknek hogy változnia kell az ablaknak
     * @param state ez az amire változtatni kell
     */
    private void changeWindow(Main.State state) {
        if (player.getHealth() <= 0 || (currentLevel == 5 && invaders.getInvaders().size() == 0)) {
            new Database().saveGame(new Game(scene, screenWidth, screenHeight));
            player.setShooting(false);
            bullets.removeBullets();
        } else {
            new Database().saveGame(this);
        }
        for (ChangeWindow cw : windowListeners)
            cw.changeWindow(state);
    }

    /**
     * Hozzáad egy lövedéket a lövedékekhez
     * @param bullet ezt adja hozzá
     */
    @Override
    public void addBullet(Bullet bullet) {
        bullets.addBullet(bullet);
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
