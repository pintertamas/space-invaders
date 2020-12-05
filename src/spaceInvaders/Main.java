package spaceInvaders;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

public class Main extends Application implements ChangeWindow {

    public enum State{menu, load, game, gameEnd}
    private State state = State.menu;

    @Override
    public void start(Stage primaryStage){
        int screenWidth = 900;
        int screenHeight = 600;
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
                        //System.out.println("Menu");
                        menu.showMenu(root, canvas, gc);
                        break;
                    case load:
                        break;
                    case game:
                        //System.out.println("Game");
                        game.showGame(root, canvas, gc);
                        break;
                    case gameEnd:
                        //System.out.println("GameEnd");
                        break;
                }
            }
        }.start();
    }

    @Override
    public void changeWindow(State state) {
        this.state = state;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
