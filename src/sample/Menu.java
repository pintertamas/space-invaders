package sample;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class Menu {
    private int screenWidth;
    private int screenHeight;
    private final ArrayList<ChangeWindow> listeners;

    public Menu(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        listeners = new ArrayList<>();
    }

    public void showMenu(Group root, Canvas canvas, GraphicsContext gc) {
        setBackground(root, canvas, gc);
        Text logoTextFront = new Text(45, 65, "SPACE INVADERS");
        Text logoTextBack = new Text(50, 70, "SPACE INVADERS");
        logoTextFront.setId("logoText");
        logoTextBack.setId("logoText");
        logoTextFront.setStyle("-fx-text-inner-color: darkgreen;");
        logoTextBack.setStyle("-fx-text-inner-color: limegreen;");
        root.getChildren().addAll(logoTextBack, logoTextFront);

        Button startButton = new Button("START");
        Button loadButton = new Button("LOAD");
        Button exitButton = new Button("EXIT");

        startButton.setOnMousePressed(mouseEvent -> startGame());
        loadButton.setOnMousePressed(mouseEvent -> System.out.println("load button clicked"));
        exitButton.setOnMousePressed(mouseEvent -> System.exit(0));

        HBox buttons = new HBox(25);
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(startButton, loadButton, exitButton);
        buttons.setLayoutX(100);
        buttons.setLayoutY(screenHeight/2.5);

        root.getChildren().addAll(buttons);
    }

    public void setBackground(Group root, Canvas canvas, GraphicsContext gc) {
        root.getChildren().clear();
        gc.setFill(Color.rgb(30, 30, 30));
        gc.fillRect(0, 0, screenWidth, screenHeight);
        root.getChildren().add(canvas);
    }

    public void addListener(ChangeWindow listener) {
        listeners.add(listener);
    }

    private void startGame() {
        for (ChangeWindow cw : listeners)
            cw.changeWindow(Main.State.game);
    }
}
