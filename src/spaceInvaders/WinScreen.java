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

/**
 * Ez kezeli a megnyert játékok végén lévő képernyőt
 */
public class WinScreen {
    private final int screenWidth;
    private final int screenHeight;
    private final ArrayList<ChangeWindow> listeners;

    /**
     * Konstruktor, ami beállítja a képernyő magasságát és létrehozza a listenereket
     * @param screenWidth a képernyő szélessége
     * @param screenHeight a képernyő magassága
     */
    public WinScreen(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.listeners = new ArrayList<>();
    }

    /**
     * Kirajzolja a játék végén mutatni kívánt elemeket
     * @param root ehhez a csoporthoz adja hozzá
     * @param canvas erre a canvasra rajzol
     * @param gc ezzel rajzolja a hátteret
     */
    public void showWinScreen(Group root, Canvas canvas, GraphicsContext gc) {
        setBackground(root, canvas, gc);
        Text youWonTextFront = new Text(45, 65, "YOU WON!");
        Text youWonTextBack = new Text(50, 70, "YOU WON!");
        youWonTextFront.setId("text");
        youWonTextBack.setId("text");
        youWonTextFront.setStyle("-fx-text-inner-color: darkgreen;");
        youWonTextBack.setStyle("-fx-text-inner-color: limegreen;");
        root.getChildren().addAll(youWonTextBack, youWonTextFront);

        Button menuButton = new Button("MENU");
        Button exitButton = new Button("EXIT");

        menuButton.setOnMousePressed(mouseEvent -> switchState());
        exitButton.setOnMousePressed(mouseEvent -> System.exit(0));

        VBox buttons = new VBox(screenHeight / 10.f);
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(menuButton, exitButton);
        buttons.setMinWidth(screenWidth * 2 / 3.f);
        buttons.setMaxWidth(screenWidth * 2 / 3.f);
        buttons.setLayoutX(screenWidth / 2.f - buttons.getMaxWidth() / 2);
        buttons.setLayoutY(screenHeight / 3.f);
        root.getChildren().addAll(buttons);
    }

    /**
     * Kirajzolja a hátteret
     * @param root ehhez a csoporthoz adja hozzá a vonalakat
     * @param canvas erre a canvasra fest
     * @param gc ezzel a GraphicsContext-tel rajzolja a szürke hátteret
     */
    public void setBackground(Group root, Canvas canvas, GraphicsContext gc) {
        root.getChildren().clear();
        gc.setFill(Color.rgb(30, 30, 30));
        gc.fillRect(0, 0, screenWidth, screenHeight);
        root.getChildren().add(canvas);
    }

    /**
     * Hozzáad egy listenert a ChangeWindow listenerekhez
     * @param listener ezt adja hozzá
     */
    public void addListener(ChangeWindow listener) {
        listeners.add(listener);
    }

    /**
     * Szól a listenereknek hogy a menüre kell változnia az ablaknak
     */
    private void switchState() {
        for (ChangeWindow cw : listeners)
            cw.changeWindow(Main.State.menu);
    }
}