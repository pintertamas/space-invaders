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

public class Main extends Application implements ChangeWindow, Serializable {

    public enum State {menu, load, game, gameEnd}

    private State state = State.menu;

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

    private void game(Group root, Canvas canvas, Scene scene, GraphicsContext gc, int screenWidth, int screenHeight) {
        Menu menu = new Menu(screenWidth, screenHeight);
        menu.addListener(this);
        Game game = new Game(scene, screenWidth, screenHeight);
        game.addWindowListener(this);
        new AnimationTimer() {
            @Override
            public void handle(long l) {
                switch (state) {
                    case menu:
                        menu.showMenu(root, canvas, gc);
                        break;
                    case load:
                        System.out.println("load");

                        state = State.game;
                        break;
                    case game:
                        game.showGame(root, canvas, gc);
                        break;
                    case gameEnd:
                        break;
                }
            }
        }.start();
        Random random = new Random();
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
            if (state == State.game && game.invaders().getInvaders().size() != 0) {
                for (Invader invader : game.invaders().getInvaders()) {
                    if (random.nextInt(4) >= 3)
                        invader.shoot(new EnemyBullet(invader.getPosX() + invader.getSize() / 2.f - screenHeight / 60.f, invader.getPosY() + invader.getSize() + screenHeight / 30f + 10, screenHeight / 30));
                }

            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    @Override
    public void changeWindow(State state) {
        this.state = state;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
