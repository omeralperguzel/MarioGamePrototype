package entity.mob;

import entity.Entity;
import main.Handler;
import main.Id;
import main.Main;
import states.KoopaState;
import tile.Tile;

import java.awt.*;
import java.util.Random;

public class Koopa extends Entity {

    private Random random;

    private int shellCount;

    public Koopa(int x, int y, int width, int height, boolean solid, Id id, Handler handler) {
        super(x, y, width, height, solid, id, handler);

        random = new Random();

        int dir = random.nextInt(2);

        switch (dir) {
            case 0:
                setVelX(-2);
                facing = 1;
                break;
            case 1:
                setVelX(2);
                facing = 0;
                break;
        }

        koopaState = KoopaState.WALKING;
    }

    public void render(Graphics g) {
        //g.setColor(new Color(39, 227, 51));
        //g.fillRect(getX(), getY(), width, height);
        //same as player movement
        //left
        if(facing==0){
            g.drawImage(Main.koopa[frame+4].getBufferedImage(),x,y,width,height,null);
        }
        //right
        else if(facing==1){
            g.drawImage(Main.koopa[frame].getBufferedImage(),x,y,width,height,null);
        }
    }

    public void tick() {
        x += velX;
        y += velY;

        if (koopaState == KoopaState.SHELL) {
            setVelX(0);

            shellCount++;

            if (shellCount >= 400) {
                shellCount = 0;

                koopaState = KoopaState.WALKING;
            }
            if (koopaState == KoopaState.WALKING || koopaState == KoopaState.SPINNING) {
                shellCount = 0;

                if (velX == 0) {
                    int dir = random.nextInt(2);

                    switch (dir) {
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
            }

            //same as mushroom movement
            for (Tile t : handler.tile) {
                if (!t.solid) break;
                if (t.isSolid()) {
                    if (getBoundsBottom().intersects(t.getBounds())) {
                        setVelY(0);
                        //y = t.getY()-t.height;
                        if (falling) falling = false;

                    } else {
                        if (!falling) {
                            gravity = 0.8;
                            falling = true;
                        }

                    }
                    if (getBoundsLeft().intersects(t.getBounds())) {
                        if (koopaState == KoopaState.SPINNING) setVelX(10);
                        else setVelX(2);
                        facing = 1;
                    }
                    if (getBoundsRight().intersects(t.getBounds())) {
                        if (koopaState == KoopaState.SPINNING) setVelX(-10);
                        else setVelX(-2);
                        facing = 0;
                    }
                    //for bouncing off the wall

                }
            }
            if (falling) {
                gravity += 0.1;
                setVelY((int) gravity);
            }

            if (velX != 0) {
                frameDelay++;
                if (frameDelay >= 10) {
                    frame++;
                    if (frame >= 3) {
                        frame = 0;
                    }
                    frameDelay = 0;
                }
            }
        }
    }
}
