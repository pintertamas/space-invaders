package spaceInvaders;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class Menu {
    private final int screenWidth;
    private final int screenHeight;
    private final ArrayList<ChangeWindow> listeners;

    public Menu(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.listeners = new ArrayList<>();
    }

    public void showMenu(Group root, Canvas canvas, GraphicsContext gc) {
        setBackground(root, canvas, gc);
        Text logoTextFront = new Text(45, 65, "SPACE INVADERS");
        Text logoTextBack = new Text(50, 70, "SPACE INVADERS");
        logoTextFront.setId("text");
        logoTextBack.setId("text");
        logoTextFront.setStyle("-fx-text-inner-color: darkgreen;");
        logoTextBack.setStyle("-fx-text-inner-color: limegreen;");
        root.getChildren().addAll(logoTextBack, logoTextFront);

        Button startButton = new Button("START");
        Button loadButton = new Button("LOAD");
        Button exitButton = new Button("EXIT");

        startButton.setOnMousePressed(mouseEvent -> switchState(Main.State.game));
        loadButton.setOnMousePressed(mouseEvent -> switchState(Main.State.load));
        exitButton.setOnMousePressed(mouseEvent -> System.exit(0));

        VBox buttons = new VBox(screenHeight / 10.f);
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(startButton, loadButton, exitButton);
        buttons.setMinWidth(screenWidth * 2 / 3.f);
        buttons.setMaxWidth(screenWidth * 2 / 3.f);
        buttons.setLayoutX(screenWidth / 2.f - buttons.getMaxWidth() / 2);
        buttons.setLayoutY(screenHeight / 3.f);
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

    private void switchState(Main.State state) {
        for (ChangeWindow cw : listeners)
            cw.changeWindow(state);
    }
}
