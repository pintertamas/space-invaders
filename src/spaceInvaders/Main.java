package spaceInvaders;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.Serializable;
import java.util.Random;

/**
 * Ez a fő osztály, amiben a játék fut
 */
public class Main extends Application implements ChangeWindow, Serializable {

    /**
     * A játék jelenlegi státusza
     */
    public enum State {menu, load, game, gameOver, win}

    private State state = State.menu;

    /**
     * Az Application többek között ezt a függvényt indítja a launch() metódust meghívva
     * Beállítja az ablak tulajdonságait, majd meghívja a game(...) metódust
     * @param primaryStage az ablak amiben a játék fut
     */
    @Override
    public void start(Stage primaryStage) {
        int screenWidth = 600;
        int screenHeight = 900;
        Group root = new Group();
        Canvas canvas = new Canvas(screenWidth, screenHeight);
        root.getChildren().add(canvas);
        Scene scene = new Scene(root);
        GraphicsContext gc;
        primaryStage.setTitle("Space Invaders");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        String style = getClass().getResource("resources/style.css").toExternalForm();
        scene.getStylesheets().addAll(style);
        root.getStylesheets().addAll(style);
        gc = canvas.getGraphicsContext2D();
        game(root, canvas, scene, gc, screenWidth, screenHeight);
    }

    /**
     * Ez a függvény létrehozza a játékhoz szükséges képernyőket, majd feliratkozik a listenerjeikre
     * Ez után egy AnimationTimer gondoskodik arról, hogy a játék egy végtelen ciklusban fusson
     * Ebben a timerben van egy switch statement ami a state-től függően változtatja a megjelenített tartalmat
     * A függvény végén futó Timeline bizonyos időközönként végigmegy az invadereken, és egy kis eséllyel meghívja a shoot() függvényét az aktuális invadernek
     * @param root a csoport amire rakjuk a dolgokat
     * @param canvas erre a canvasra rajzolunk
     * @param scene ezt a scenet használjuk
     * @param gc ezzel rajzolunk dolgokat
     * @param screenWidth a képernyő szélessége
     * @param screenHeight a képernyő magassága
     */
    private void game(Group root, Canvas canvas, Scene scene, GraphicsContext gc, int screenWidth, int screenHeight) {
        Menu menu = new Menu(screenWidth, screenHeight);
        menu.addListener(this);
        Game game = new Game(scene, screenWidth, screenHeight);
        game.addWindowListener(this);
        GameOver gameOver = new GameOver(screenWidth, screenHeight);
        gameOver.addListener(this);
        WinScreen winScreen = new WinScreen(screenWidth, screenHeight);
        winScreen.addListener(this);
        new AnimationTimer() {
            @Override
            public void handle(long l) {
                switch (state) {
                    case menu -> menu.showMenu(root, canvas, gc);
                    case load -> {
                        game.setGame(new Database().loadGame(scene, screenWidth, screenHeight), scene);
                        game.addWindowListener(Main.this);
                        state = State.game;
                    }
                    case game -> game.showGame(root, canvas, gc);
                    case gameOver -> {
                        gameOver.showGameOver(root, canvas, gc);
                        game.setGame(new Game(scene, screenWidth, screenHeight), scene);
                        game.clearWindowListeners();
                        game.addWindowListener(Main.this);
                    }
                    case win -> {
                        winScreen.showWinScreen(root, canvas, gc);
                        game.setGame(new Game(scene, screenWidth, screenHeight), scene);
                        game.clearWindowListeners();
                        game.addWindowListener(Main.this);
                    }
                }
            }
        }.start();
        Random random = new Random();
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
            if (state == State.game && game.getInvaders().getInvaders().size() != 0) {
                for (Invader invader : game.getInvaders().getInvaders()) {
                    if (random.nextInt(100) >= 90)
                        invader.shoot(new EnemyBullet(invader.getPosX() + invader.getSize() / 2.f - screenHeight / 60.f, invader.getPosY() + invader.getSize() + screenHeight / 30f + 10, screenHeight / 30));
                }
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    /**
     * Megváltoztatja a statet a paraméterben kapottra
     * @param state erre változtatja
     */
    @Override
    public void changeWindow(State state) {
        this.state = state;
    }

    /**
     * Elindítja az alkalmazást
     * @param args az argumentumok
     */
    public static void main(String[] args) {
        launch(args);
    }
}
