package spaceInvaders;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.Serializable;
import java.util.Random;

/**
 * Ez az osztály az invadereket kezeli
 */
public class Invader extends Figure implements Serializable {
    private boolean isAlive;
    private transient Image img;
    private final float speed;

    /**
     * Ez a konstruktor, ami beállítja az alábbi paramétereket túl azt hogy mennyi az invader sebessége, és azt hogy él
     * @param posX az invader X pozíciója
     * @param posY az invader Y pozíciója
     * @param size az invader mérete
     */
    public Invader(float posX, float posY, int size) {
        super(posX, posY, size);
        this.isAlive = true;
        this.speed = 05.5f;
    }

    /**
     * Ad egy képet az invadernek véletlenszerűen
     */
    public void addImageToInvader() {
        int randomImageIndex = new Random().nextInt(5) + 1;
        setImg(new Image("icons/alien" + randomImageIndex + ".png", getSize(), getSize(), true, true));
    }

    /**
     * Frissíti az invader pozícióját
     */
    public void update() {
        this.setPosY(getPosY() + getSpeed());
    }

    /**
     * Megöli az invadert
     */
    public void die() {
        this.isAlive = false;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    public Image getImg() {
        return img;
    }

    public float getSpeed() {
        return speed;
    }

    /**
     * Az invader lövését kezeli
     * @param bullet ezt a lövedéket lövi
     */
    @Override
    protected void shoot(Bullet bullet) {
        for (BulletListener bl : bulletListeners)
            bl.addBullet(bullet);
    }

    /**
     * Kirajzolja az invadert a képernyőre
     * @param root ehhez a csoporthoz adja hozzá
     */
    public void drawInvader(Group root) {
        if (isAlive()) {
            ImageView imageView = new ImageView(getImg());
            imageView.setX(getPosX());
            imageView.setY(getPosY());
            root.getChildren().add(imageView);
        }
    }
}
