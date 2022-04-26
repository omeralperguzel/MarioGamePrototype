package entity.powerup;

import entity.Entity;
import main.Handler;
import main.Id;
import main.Main;
import tile.Tile;

import java.awt.*;
import java.util.Random;

public class Mushroom extends Entity {

    private Random random = new Random();

    public Mushroom(int x, int y, int width, int height, boolean solid, Id id, Handler handler){
        super(x, y, width, height, solid, id, handler);

        int dir = random.nextInt(2);

        switch (dir) {
            case 0 -> setVelX(-3);
            case 1 -> setVelX(3);
        }
    }

    public void render(Graphics g){
        g.drawImage(Main.mushroom.getBufferedImage(), x, y, width, height, null);

    }

    public void tick(){
        x+=velX;
        y+=velY;

        for (Tile t : handler.tile) {
            if (!t.solid) break;
            if (t.isSolid()) {
                if (getBoundsBottom().intersects(t.getBounds())) {
                    setVelY(0);
                    //y = t.getY()-t.height;
                    if (falling) falling = false;
                }
                else if (!falling) {
                        gravity = 0.8;
                        falling = true;
                }
                if (getBoundsLeft().intersects(t.getBounds())) {
                    setVelX(5);
                }
                if (getBoundsRight().intersects(t.getBounds())) {
                    setVelX(-5);
                }
                //for bouncing off the wall

            }
        }
        if (falling) {
            gravity += 0.1;
            setVelY((int) gravity);
        }
    }
}
