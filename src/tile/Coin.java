package tile;

import main.Handler;
import main.Id;
import main.Main;

import java.awt.*;

public class Coin extends Tile{

    public Coin(int x, int y, int width, int height, boolean solid, Id id, Handler handler) {
        super(x, y, width, height, solid, id, handler);
    }

    public void render(Graphics g) {
        g.drawImage(Main.coin.getBufferedImage(), x, y, width, height, null);

    }

    public void tick() {

    }
}
