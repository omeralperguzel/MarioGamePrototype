package tile;

import main.Handler;
import main.Id;
import main.Main;

import java.awt.*;

public class Pipe extends Tile{

    public Pipe(int x, int y, int width, int height, boolean solid, Id id, Handler handler, int facing){
        super(x, y, width, height, solid, id, handler);
        this.facing = facing;
    }


    public void render(Graphics g) {
        //g.setColor(new Color(128,128,128));
        //g.fillRect(x,y,width,height);
        g.drawImage(Main.pipe.getBufferedImage(),x,y,width,height,null);

    }

    public void tick() {

    }
}
