package spaceInvaders;

import javafx.scene.image.Image;

import java.io.Serializable;
import java.util.Random;

public class Invader extends Figure implements Serializable {
    private boolean isAlive;
    private Image img;

    public Invader(float posX, float posY, int size) {
        super(posX, posY, size);
        this.isAlive = true;

        Random random = new Random();
        int randomImageIndex = random.nextInt(4) + 1;
        switch (randomImageIndex) {
            case 1 -> setImg(new Image("icons/alien1.png", getSize(), getSize(), true, true));
            case 2 -> setImg(new Image("icons/alien2.png", getSize(), getSize(), true, true));
            case 3 -> setImg(new Image("icons/alien3.png", getSize(), getSize(), true, true));
            case 4 -> setImg(new Image("icons/alien4.png", getSize(), getSize(), true, true));
            case 5 -> setImg(new Image("icons/alien5.png", getSize(), getSize(), true, true));

        }
    }

    public void update() {
        //this.setPosY(getPosY()+5);
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
}
