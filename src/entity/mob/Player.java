package entity.mob;

import entity.Entity;
import main.Handler;
import main.Id;
import main.Main;
import tile.Tile;

import java.awt.*;

public class Player<frameDelay> extends Entity {

    private boolean animate = false;

    public Player(int x, int y, int width, int height, boolean solid, Id id, Handler handler){
        super(x, y, width, height, solid, id, handler);
        //setVelX(5);
    }

    public void render(Graphics g) {
        //left
        if(facing==0){
            g.drawImage(Main.player[frame+5].getBufferedImage(),x,y,width,height,null);
        }
        //right
        else if(facing==1){
            g.drawImage(Main.player[frame].getBufferedImage(),x,y,width,height,null);
        }

    }

    public void tick() {
        x += velX;
        y += velY;
        if (x <= 0) x = 0;
        //if(y<=0) y = 0;
        //if(x + width >= 1080) x = 1080 - width;
        //if(y + height>=771) y = 771-height;
        if(velX!=0) animate = true;
        else animate = false;
        //Removed for falling physics
        for (Tile t : handler.tile) {
            //if (!t.solid) break;
            //if (t.getId() == Id.wall) {
                if (getBoundsTop().intersects(t.getBounds())) {
                    setVelY(0);
                    //y = t.getY()+t.height;
                    if(jumping){
                        jumping = false;
                        gravity -= 0.4;
                        falling = true;
                    }
                    if(t.getId()==Id.powerUp){
                        if(getBoundsTop().intersects(t.getBounds())){
                            t.activated = true;
                        }
                    }
                }
                if (getBoundsBottom().intersects(t.getBounds())) {
                    setVelY(0);
                    //y = t.getY()-t.height;
                    if (falling) falling = false;

                }
                else {
                    if (!falling && !jumping) {
                        gravity = 0.8;
                        falling = true;
                    }

                }
                    if (getBoundsLeft().intersects(t.getBounds())) {
                        setVelX(0);
                        x = t.getX() + t.width;
                    }
                    if (getBoundsRight().intersects(t.getBounds())) {
                        setVelX(0);
                        x = t.getX() - t.width;
                    }
                //}
            }
        //EXPANSION FOR MUSHROOM
            for(int i=0;i<handler.entity.size();i++){
                Entity e = handler.entity.get(i);

                if(e.getId()==Id.mushroom){
                    if(getBounds().intersects(e.getBounds())){
                        int tpX = getX();
                        int tpY = getY();
                        width*=2;
                        height*=2;
                        setX(tpX-width);
                        setY(tpY-height);
                        e.die();
                    }
                }
                else if(e.getId()==Id.goomba){
                    if(getBoundsBottom().intersects(e.getBoundsTop())){
                        e.die();
                    }
                    else if(getBounds().intersects(e.getBounds())){
                        die();
                    }
                }
            }
            //this loop is scanning our whole entity linked list in our handler class
            //then whatever entity it scans it will create an entity object out (part 19)

            if (jumping) {
                gravity -= 0.1;
                setVelY((int) -gravity);
                if (gravity <= 0.0) {
                    jumping = false;
                    falling = true;
                }
            }
            if (falling) {
                gravity += 0.1;
                setVelY((int) gravity);
            }
            if(animate){
                frameDelay++;
                if(frameDelay>=3){
                    frame++;
                    if(frame>=5){
                        frame = 0;
                    }
                    frameDelay = 0;
                }
            }

    }


}
