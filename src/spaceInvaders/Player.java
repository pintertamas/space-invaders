package spaceInvaders;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

import java.io.Serializable;
import java.sql.Time;

public class Player extends Figure implements Serializable {

    private int health;
    private final int speed;
    private Time shootTime;

    public Player(float posX, float posY, int size) {
        super(posX, posY, size);
        health = 3;
        speed = 25;
    }

    public void damage(){
        this.health--;
    }

    public void moveLeft() {
        setPosX(getPosX() - getSpeed());
    }

    public void moveRight() {
        setPosX(getPosX() + getSpeed());
    }

    public int getHealth() {
        return health;
    }

    public int getSpeed() {
        return speed;
    }

    @Override
    protected void shoot(Bullet bullet) {
        for (BulletListener bl : bulletListeners)
            bl.addBullet(bullet);
    }

    public void movePlayer(Scene scene, int screenWidth, int screenHeight) {
        scene.setOnKeyPressed(keyEvent -> {
            KeyCode code = keyEvent.getCode();
            if (code == KeyCode.A && getPosX() - 10 >= 10) {
                moveLeft();
            } else if (code == KeyCode.D && getPosX() + getSize() + 10 < screenWidth - 10) {
                moveRight();
            } else if (code == KeyCode.W) {
                shoot(new PlayerBullet(getPosX() + getSize() / 2.f - 15, getPosY() - screenHeight / 60.f, screenHeight / 30));
            }

        });
    }

    public void drawPlayer(Group root) {
        Image image = new Image("icons/player.png", getSize(), getSize(), true, true);
        ImageView imageView = new ImageView(image);
        imageView.setX(getPosX());
        imageView.setY(getPosY());
        root.getChildren().add(imageView);
    }
}
