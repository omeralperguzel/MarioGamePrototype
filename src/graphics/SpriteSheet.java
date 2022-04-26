package graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SpriteSheet {

    public SpriteSheet i;
    private BufferedImage sheet;

    public SpriteSheet(String path){
        try {
            sheet = ImageIO.read(getClass().getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //"spritesheet.png"
    }

    public BufferedImage getSprite(int x, int y){
        return sheet.getSubimage(x*32-32, y*32-32, 32, 32);
    }
}
