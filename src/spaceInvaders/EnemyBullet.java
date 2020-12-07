package spaceInvaders;

import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.Serializable;

/**
 * Az ellenség lövedékeit tárolja, és a lövedék osztályt egészíti ki
 */
public class EnemyBullet extends Bullet implements Serializable {

    /**
     * Konstruktor a lövedékhez
     * @param posX a golyó X pozíciója
     * @param posY a golyó Y pozíciója
     * @param size a golyó mérete
     */
    EnemyBullet(float posX, float posY, int size) {
        super(posX, posY, size, 5);
        this.bulletId = id.enemy;
    }

    /**
     * Frissíti a golyó pozícióját
     * Ha kimegy a képernyőről, megöli a killIfOutside(int) függvény
     * @param screenHeight a képernyő magassága
     */
    @Override
    public void update(int screenHeight) {
        this.posY += this.speed;
        this.killIfOutside(screenHeight);
    }

    /**
     * Kirajzolja a golyót a képernyőre a paramétereivel
     * @param root ehhez a csoporthoz adja hozzá
     */
    @Override
    public void drawBullet(Group root) {
        Image image = new Image("icons/bullet_enemy.png", getSize(), getSize(), true, true);
        ImageView imageView = new ImageView(image);
        imageView.setX(getPosX());
        imageView.setY(getPosY());
        root.getChildren().add(imageView);
    }
}
