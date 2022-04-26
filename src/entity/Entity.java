package entity;

import main.Handler;
import main.Id;
import java.awt.*;

public abstract class Entity {

    public int x, y;
    public int width, height;
    public int facing = 0; //0 -> left, 1 -> right

    public boolean solid;
    public boolean jumping = false;
    public boolean falling = true;

    protected int frame = 0;
    protected int frameDelay = 0;

    public int velX, velY;

    public Id id;

    public double gravity = 0.0;

    public Handler handler;

    public Entity(int x, int y, int width, int height, boolean solid, Id id, Handler handler){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.solid = solid;
        this.id = id;
        this.handler = handler;
    }

    public abstract void render (Graphics g);

    public abstract void tick();

    public void die(){
        handler.removeEntity(this);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y){
        this.y = y;
    }

    public boolean isSolid(){
        return solid;
    }

    public void setVelX(int velX) {
        this.velX = velX;
    }

    public void setVelY(int velY){
        this.velY = velY;
    }

    public Id getId(){
        return id;
    }

    public Rectangle getBounds() {
        return new Rectangle(getX(),getY(),width,height);
    }
    public Rectangle getBoundsTop(){
        return new Rectangle(getX()+10,getY()+10,width-20,5);
    }
    public Rectangle getBoundsBottom(){
        return new Rectangle(getX()+10,getY()+width-5,width-20,5);
    }
    public Rectangle getBoundsLeft(){
        return new Rectangle(getX(),getY()+10,5,height-20);
    }
    public Rectangle getBoundsRight(){
        return new Rectangle(getX()+width-5,getY()+10,5,height-20);
    }

    //Entity test = new Entity(100,100,50,100,true);
}
