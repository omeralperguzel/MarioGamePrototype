package entity.mob;

import entity.Entity;
import main.Handler;
import main.Id;
import main.Main;
import tile.Tile;

import java.awt.*;
import java.util.Random;


public class Goomba extends Entity {

    private Random random = new Random();

    public Goomba(int x, int y, int width, int height, boolean solid, Id id, Handler handler) {
        super(x, y, width, height, solid, id, handler);

        int dir = random.nextInt(2);

        switch(dir){
            case 0:
                setVelX(-2);
                facing = 1;
                break;
            case 1:
                setVelX(2);
                facing = 0;
                break;
        }
    }

    public void render(Graphics g) {
//same as player movement
        //left
        if(facing==0){
            g.drawImage(Main.goomba[frame+5].getBufferedImage(),x,y,width,height,null);
        }
        //right
        else if(facing==1){
            g.drawImage(Main.goomba[frame].getBufferedImage(),x,y,width,height,null);
        }
    }

    public void tick(){
        x+=velX;
        y+=velY;
//same as mushroom movement
        for (Tile t : handler.tile) {
            if (!t.solid) break;
            if (t.isSolid()) {
                if (getBoundsBottom().intersects(t.getBounds())) {
                    setVelY(0);
                    //y = t.getY()-t.height;
                    if (falling) falling = false;

                }
                else {
                    if (!falling) {
                        gravity = 0.8;
                        falling = true;
                    }

                }
                if (getBoundsLeft().intersects(t.getBounds())) {
                    setVelX(5);
                    facing = 1;
                }
                if (getBoundsRight().intersects(t.getBounds())) {
                    setVelX(-5);
                    facing = 0;
                }
                //for bouncing off the wall

            }
        }
        if (falling) {
            gravity += 0.1;
            setVelY((int) gravity);
        }

        if(velX!=0){
            frameDelay++;
            if(frameDelay>=10){
                frame++;
                if(frame>=3){
                    frame = 0;
                }
                frameDelay = 0;
            }
        }
    }
}
