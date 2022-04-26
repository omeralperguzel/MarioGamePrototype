package main;

import entity.Entity;
import entity.mob.Goomba;
import entity.mob.Player;
import entity.powerup.Mushroom;
import tile.PowerUpBlock;
import tile.Tile;
import tile.Wall;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class Handler {

    public LinkedList<Entity> entity = new LinkedList<>();
    public LinkedList<Tile> tile = new LinkedList<>();

    /*public Handler(){
        createLevel();
    }*/

    public void render(Graphics g){
        for(Entity en:entity){
            en.render(g);
        }
        for(Tile ti:tile){
            ti.render(g);
        }
    }

    public void tick() {
        for(Entity en:entity){
            en.tick();
        }
        for(Tile ti:tile){
            ti.tick();
        }
    }

    public void addEntity(Entity en){
        entity.add(en);
    }

    public void removeEntity(Entity en){
        entity.remove(en);
    }

    public void addTile(Tile ti){
        tile.add(ti);
    }

    public void removeTile(Tile ti){
        tile.remove(ti);
    }

    public void createLevel(BufferedImage level){
        int width = level.getWidth();
        int height = level.getHeight();

        for(int y=0;y<height;y++){
            for(int x = 0;x<width;x++){
                int pixel = level.getRGB(x,y);

                //swift pixel binary code to certain symbols

                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = (pixel) & 0xff;
                //complete black pixel for levels
                if(red==0 && green==0 && blue==0) addTile(new Wall(x*64,y*64,64,64,true,Id.wall,this));
                //complete blue pixel for player
                if(red==0 && green==0 && blue==255) addEntity(new Player(x*64,y*64,64,64,false,Id.player,this));
                //complete red pixel for mushroom
                if(red==255 && green==0 && blue==0) addEntity(new Mushroom(x*64,y*64,64,64,true,Id.mushroom,this));
                //one orange pixel for goomba
                if(red==255 && green==119 && blue==0) addEntity(new Goomba(x*64,y*64,64,64,true,Id.goomba,this));
                //one yellow pixel for power up
                if(red==255 && green==255 && blue==0) addTile(new PowerUpBlock(x*64,y*64,64,64,true,Id.powerUp,this, Main.mushroom));

            }
        }
    }
}
