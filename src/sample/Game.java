package sample;

import com.sun.prism.Graphics;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.ArrayList;

public class Game {
    ArrayList<Invaders> invaders;
    Player player;
    private final int screenWidth;
    private final int screenHeight;
    private final ArrayList<ChangeWindow> listeners;

    public Game(int screenWidth, int screenHeight) {
        this.invaders = new ArrayList<>();
        this.player = new Player(screenHeight - 10, screenWidth / 2.f - 10, 20);
        this.listeners = new ArrayList<>();
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    public void showGame(Group root, Canvas canvas, GraphicsContext gc) {
        setBackground(root, canvas, gc);
    }

    public void setBackground(Group root, Canvas canvas, GraphicsContext gc) {
        root.getChildren().clear();
        gc.setFill(Color.rgb(30, 30, 30));
        gc.fillRect(0, 0, screenWidth, screenHeight);
        root.getChildren().add(canvas);

        for (int i = 0; i < screenHeight; i+=screenHeight/10) {
            Line line = new Line(0, i, screenWidth, i);
            line.setId("line");
            line.setOpacity(0.2*(float)screenHeight/(screenHeight-i));
            root.getChildren().add(line);
        }

        for (float i = screenWidth/3.f; i < screenWidth; i+=screenWidth/100.f) {
            float tmp = (screenWidth/3.f/(i-screenWidth/3.f))*screenWidth;
            Line line = new Line(i, 0, (screenWidth*2.5-tmp), screenHeight);
            line.setId("lineVertical");
            root.getChildren().add(line);
        }
    }

    public void addListener(ChangeWindow listener) {
        listeners.add(listener);
    }

    private void menu() {
        for (ChangeWindow cw : listeners)
            cw.changeWindow(Main.State.menu);
    }
}
