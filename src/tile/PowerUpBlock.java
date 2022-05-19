package tile;

import entity.powerup.Mushroom;
import graphics.Sprite;
import main.Handler;
import main.Id;
import main.Main;

import java.awt.*;

public class PowerUpBlock extends Tile{

    private Sprite powerUp;

    private boolean poppedUp = false;

    private int spriteY = getY();

    private int type;

    public PowerUpBlock(int x, int y, int width, int height, boolean solid, Id id, Handler handler, Sprite powerUp, int type) {
        super(x, y, width, height, solid, id, handler);
        this.powerUp = powerUp;
        this.type = type;
    }

    public void render(Graphics g) {
        if(!poppedUp) g.drawImage(Main.powerUp.getBufferedImage(), x, spriteY, width, height, null);
        if(!activated) g.drawImage(Main.powerUp.getBufferedImage(), x, y, width, height,null);
        else g.drawImage(Main.usedPowerUp.getBufferedImage(), x, y, width, height, null);
    }

    public void tick() {
        if(activated && !poppedUp){
            Main.mysteryblockbreak.play();
            spriteY--;
            if(spriteY<=y-height){
                handler.addEntity(new Mushroom(x, spriteY, width, height, solid, Id.mushroom, handler, type));
                poppedUp = true;
            }
        }
    }
}
