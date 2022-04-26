package tile;

import main.Handler;
import main.Id;
import java.awt.*;

public abstract class Tile {

    public int x, y;
    public int width, height;

    public boolean solid;
    public boolean activated = false;

    public int velX, velY;

    public Id id;

    public Handler handler;

    public Tile(int x, int y, int width, int height, boolean solid, Id id, Handler handler){
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
        handler.removeTile(this);
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

    public void setSolid(boolean solid){
        this.solid = solid;
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

    //Entity test = new Entity(100,100,50,100,true);
}
