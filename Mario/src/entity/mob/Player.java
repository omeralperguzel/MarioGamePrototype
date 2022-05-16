package entity.mob;

import entity.Entity;

import states.KoopaState;
import states.PlayerState;
import main.Handler;
import main.Id;
import main.Main;
import tile.Tile;

import java.awt.*;
import java.util.Random;

public class Player<frameDelay> extends Entity {

    private PlayerState state;

    private int pixelsTravelled = 0;

    private Random random;

    private boolean animate = false;

    public Player(int x, int y, int width, int height, boolean solid, Id id, Handler handler){
        super(x, y, width, height, solid, id, handler);
        //setVelX(5);

        state = PlayerState.SMALL;

        random = new Random();
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

        /*if(goingDownPipe){
        }*/

        if (x <= 0) x = 0;
        //if(y<=0) y = 0;
        //if(x + width >= 1080) x = 1080 - width;
        //if(y + height>=771) y = 771-height;
        if(velX!=0) animate = true;
        else animate = false;
        //Removed for falling physics
        for (Tile t : handler.tile) {
            //if (!t.solid) break;
            if(t.isSolid() && !goingDownPipe){
            //if (t.getId() == Id.wall) {
                if (getBoundsTop().intersects(t.getBounds()) && t.getId() != Id.coin) {
                    setVelY(0);
                    //y = t.getY()+t.height;
                    if(jumping && !goingDownPipe){
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
                if (getBoundsBottom().intersects(t.getBounds()) && t.getId() != Id.coin) {
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
                    if (getBoundsLeft().intersects(t.getBounds()) && t.getId() != Id.coin) {
                        setVelX(0);
                        x = t.getX() + width;
                    }                 //t silindi
                    if (getBoundsRight().intersects(t.getBounds()) && t.getId() != Id.coin) {
                        setVelX(0);
                        x = t.getX() - width;
                    }                //t silindi
                    if(getBounds().intersects(t.getBounds())) { //eklendi
                    	if(t.getId()== Id.flag) Main.switchLevel();
                    	
                    }
                //}
            }
        //EXPANSION FOR MUSHROOM
            for(int i=0;i<handler.entity.size();i++){
                Entity e = handler.entity.get(i);

                if(e.getId()==Id.mushroom){
                	switch(e.getType()) {  //eklendi
                	case 0 : 
                		if(getBounds().intersects(e.getBounds())){ 
                            int tpX = getX();
                            int tpY = getY();
                            width*=2;
                            height*=2;
                            setX(tpX-width);
                            setY(tpY-height);
                            if (state == PlayerState.SMALL) state = PlayerState.BIG;
                            e.die();
                        }
                		break;
                	case 1 : 
                		if(getBounds().intersects(e.getBounds())) {
                      
                		Main.lives++; //ma'e d�zeltildi
                		e.die();
                		}
                		
                  	}
                }
                    
                //PLAYER-COIN INTERACTION
                if(getBounds().intersects(t.getBounds()) && t.getId() == Id.coin) {
                    Main.coins++;
                    t.die();
                }

                else if(e.getId()==Id.koopa){
                    if(e.koopaState == koopaState.WALKING){

                        if(getBoundsBottom().intersects(e.getBoundsTop())){
                            e.koopaState = KoopaState.SHELL;
                            
                            Main.goombastomp.play(); // eklendi ama if-else s�ralamas� farkl� oldu�u i�in yeri de�i�mesi gerekebilir.

                            jumping = true;
                            falling = false;
                            gravity = 3.5;
                        }
                        else if(getBounds().intersects(e.getBounds())){
                            die();
                        }
                    }
                    else if(e.koopaState == KoopaState.SHELL){

                        if(getBoundsBottom().intersects(e.getBoundsTop())){
                            e.koopaState = KoopaState.SPINNING;

                            int dir = random.nextInt(2);

                            switch(dir){
                                case 0:
                                    e.setVelX(-10);
                                    break;
                                case 1:
                                    e.setVelX(10);
                                    break;
                            }

                            if(getBoundsLeft().intersects(e.getBoundsRight())){
                                e.setVelX(-10);
                                e.koopaState = KoopaState.SPINNING;
                            }

                            if(getBoundsRight().intersects(e.getBoundsLeft())){
                                e.setVelX(10);
                                e.koopaState = KoopaState.SPINNING;
                            }

                            jumping = true;
                            falling = false;
                            gravity = 3.5;
                        }

                    }
                    else if(e.koopaState == KoopaState.SPINNING){
                        if(getBoundsBottom().intersects(e.getBoundsTop())){
                            e.koopaState = KoopaState.SHELL;

                            jumping = true;
                            falling = false;
                            gravity = 3.5;
                        }
                        else if(getBounds().intersects(e.getBounds())){
                            die();
                        }
                    }
                }

                else if(e.getId()==Id.goomba) {
                    if (getBoundsBottom().intersects(e.getBoundsTop())) {
                        e.die();
                    } else if (getBounds().intersects(e.getBounds())) {
                        if (state == PlayerState.BIG) {
                            state = PlayerState.SMALL;
                            width /= 2;
                            height /= 2;
                            x += width;
                            y += height;
                        } else if (state == PlayerState.SMALL) {
                            die();
                        }
                    }
                }//added for isSolid() command
                }
            }
            //this loop is scanning our whole entity linked list in our handler class
            //then whatever entity it scans it will create an entity object out (part 19)

            if (jumping && !goingDownPipe) {
                gravity -= 0.1;
                setVelY((int) -gravity);
                if (gravity <= 0.0) {
                    jumping = false;
                    falling = true;
                }
            }
            if (falling && !goingDownPipe) {
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
            if(goingDownPipe) {
                for(int i=0; i < Main.handler.tile.size(); i++){
                    Tile t = Main.handler.tile.get(i);
                    if(t.getId() == Id.pipe){
                        if(getBounds().intersects(t.getBounds())){
                            switch(t.facing){
                                case 0:
                                    setVelY(-5);
                                    setVelX(0);
                                    pixelsTravelled+=-velY;
                                    break;
                                case 2:
                                    setVelY(5);
                                    setVelX(0);
                                    pixelsTravelled+=velY;
                                    break;
                            }
                            if(pixelsTravelled >= t.height ){
                                goingDownPipe = false;
                                pixelsTravelled = 0;
                            }
                        }

                    }
                }
            }
    }


}