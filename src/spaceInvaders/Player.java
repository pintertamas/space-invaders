package spaceInvaders;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

import java.io.Serializable;

/**
 * Ez a class a játékost kezeli
 */
public class Player extends Figure implements Serializable {

    private int health;
    private final int speed;
    private long shootTime;

    /**
     * Ezek a kátékos lehetséges lépései
     * balra/jobbra mozgás, egy helyben maradás
     */
    private enum direction {left, right, stand}

    private direction dir;
    private boolean shooting;

    /**
     * A játékos konstruktora ami az alábbi paramétereken túl beállítja
     * a játékos életét 3-ra
     * sebességét pedig 20-ra
     * a shootTime-ot a létrehozás időpontjára
     * a mozgás irányát
     * azt, hogy nem lő éppen
     * @param posX a játékos X pozíciója
     * @param posY a játékos Y pozíciója
     * @param size a játékos mérete
     */
    public Player(float posX, float posY, int size) {
        super(posX, posY, size);
        health = 3;
        speed = 20;
        shootTime = System.currentTimeMillis();
        this.dir = direction.stand;
        shooting = false;
    }

    /**
     * A játékost sebzi
     */
    public void damage() {
        this.health--;
    }

    /**
     * A játékost balra mozgatja
     */
    public void moveLeft() {
        setPosX(getPosX() - getSpeed());
    }

    /**
     * A játékost jobbra mozgatja
     */
    public void moveRight() {
        setPosX(getPosX() + getSpeed());
    }

    public int getHealth() {
        return health;
    }

    public int getSpeed() {
        return speed;
    }

    public void setShooting(boolean shooting) {
        this.shooting = shooting;
    }

    /**
     * A játékos lövését kezeli
     * A lövés csak akkor megy végbe, ha már eltelt egy bizonyos idő az utolsó lövés óta
     * @param bullet ezt a lövedéket lövi
     */
    @Override
    protected void shoot(Bullet bullet) {
        if (System.currentTimeMillis() > shootTime + 100) {
            for (BulletListener bl : bulletListeners)
                bl.addBullet(bullet);
            shootTime = System.currentTimeMillis();
        }
    }

    /**
     * A játékos mozgásának irányától függően mozgatja azt
     * @param screenWidth a képernyő szélessége
     * @param screenHeight a képernyő magassága
     */
    public void movePlayer(int screenWidth, int screenHeight) {
        if (dir == direction.left && getPosX() - getSpeed() > 0) {
            moveLeft();
        } else if (dir == direction.right && getPosX() + getSize() < screenWidth) {
            moveRight();
        }
        if (shooting) {
            shoot(new PlayerBullet(getPosX() + getSize() / 2.f - screenHeight / 60.f, getPosY() - screenHeight / 30.f - 10, screenHeight / 30));
        }
    }

    /**
     * Gombnyomásoktól függően állítja be az irányt
     * @param scene a Scene amin nézi a billentyű lenyomásokat
     * @param screenWidth a képernyő szélessége
     */
    public void changeDirection(Scene scene, int screenWidth) {
        scene.setOnKeyPressed(keyEvent -> {
            KeyCode code = keyEvent.getCode();
            if (code == KeyCode.A && getPosX() >= getSpeed()) {
                dir = direction.left;
            } else if (code == KeyCode.D && getPosX() + getSize() < screenWidth - getSpeed()) {
                dir = direction.right;
            } else if (code == KeyCode.W) {
                shooting = true;
            }
        });
        scene.setOnKeyReleased(keyEvent -> {
            KeyCode code = keyEvent.getCode();
            if (code == KeyCode.D || code == KeyCode.A)
                dir = direction.stand;
            if (code == KeyCode.W) {
                shooting = false;
            }
        });
    }

    /**
     * Kirajzolja a játékost a képernyőre annak pozyciójára és méretével
     * @param root ehhez a csoporthoz adja hozzá
     */
    public void drawPlayer(Group root) {
        Image image = new Image("icons/player.png", getSize(), getSize(), true, true);
        ImageView imageView = new ImageView(image);
        imageView.setX(getPosX());
        imageView.setY(getPosY());
        root.getChildren().add(imageView);
    }
}
