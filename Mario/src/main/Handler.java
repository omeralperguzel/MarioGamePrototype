package main;

import entity.Entity;

import entity.mob.Goomba;
import entity.mob.Koopa;
import entity.mob.Player;
import entity.powerup.Mushroom;
import tile.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class Handler {

    public LinkedList<Entity> entity = new LinkedList<Entity>(); //entity ve tile kendiliðinden daha düzeltmek için eklendi
    public LinkedList<Tile> tile = new LinkedList<Tile>();

    /*public Handler(){
        createLevel();
    }*/

    public void render(Graphics g){
        for(Entity en:entity){ // deðiþti
          if(Main.getVisibleArea()!=null && en.getBounds().intersects(Main.getVisibleArea()))  en.render(g); //eklendi
        }
        for(Tile ti:tile){
            ti.render(g);
            if(Main.getVisibleArea()!=null && ti.getBounds().intersects(Main.getVisibleArea()))  ti.render(g);//eklendi
        }
        
        //Main'deki karýþmýþ olan iflerdeki g'li kodlarý istiyor //eklendi
        g.drawImage(Main.coin.getBufferedImage(),Main.getVisibleArea().x+20,Main.getVisibleArea().y+20,75,75,null);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Courier",Font.BOLD,20));
        g.drawString("x "+Main.coins,Main.getVisibleArea().x+100,Main.getVisibleArea().y+95);
    }

    public void tick() {
        for(int i=0;i<entity.size();i++){
            entity.get(i).tick();
        }
        for(Tile ti:tile){
        	if(Main.getVisibleArea()!=null && ti.getBounds().intersects(Main.getVisibleArea()))  ti.tick(); //eklendi
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
               // silinecek if(red==255 && green==0 && blue==0) addEntity(new Mushroom(x*64,y*64,64,64,true,Id.mushroom,this));
                //one orange pixel for goomba
                if(red==255 && green==119 && blue==0) addEntity(new Goomba(x*64,y*64,64,64,true,Id.goomba,this));
                //one yellow pixel for power up
                if(red==255 && green==255 && blue==0) addTile(new PowerUpBlock(x*64,y*64,64,64,true,Id.powerUp,this, Main.lifeMushroom, 1));
                //one green pixel for pipes                                                                                     //eklendi
                if(red==0 && (green>123 && green<129) && blue==0) addTile(new Pipe(x*64,y*64,64,64*15,true,Id.pipe,this, 128-green));
                //one yellow pixel for coins                                                        
                if(red==255 && green==250 && blue==0) addTile(new Coin(x*64,y*64,64,64,true,Id.coin,this));
                //one green pixel for koopa
                if(red==180 && green==250 && blue==180) addEntity(new Koopa(x*64,y*64,64,64,true,Id.koopa,this));
                if (red ==0 && green == 255 && blue ==0) addTile(new Flag(x*64,y*64,64,64*5,true,Id.flag,this));
            }
        }
    }

    //it will clear every entity and tile linked list instead of doing that one by one
    public void clearLevel(){
        entity.clear();
        tile.clear();
    }
}