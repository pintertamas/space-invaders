package spaceInvaders;

import javafx.scene.Group;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.Serializable;
import java.util.Random;

public class Invader extends Figure implements Serializable {
    private boolean isAlive;
    private transient Image img;

    public Invader(float posX, float posY, int size) {
        super(posX, posY, size);
        this.isAlive = true;

        int randomImageIndex = new Random().nextInt(4) + 1;
        setImg(new Image("icons/alien" + randomImageIndex + ".png", getSize(), getSize(), true, true));
    }

    public void update() {
        this.setPosY(getPosY()+1);
    }

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

    @Override
    protected void shoot(Bullet bullet) {
        for (BulletListener bl : bulletListeners)
            bl.addBullet(bullet);
    }

    public void drawInvader(Group root) {
        if (isAlive()) {
            ImageView imageView = new ImageView(getImg());
            imageView.setX(getPosX());
            imageView.setY(getPosY());
            root.getChildren().add(imageView);
        }
    }
}
