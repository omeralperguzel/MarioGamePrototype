package graphics;

import java.awt.image.BufferedImage;

public class Sprite {

    public SpriteSheet sheet;

    public BufferedImage image;
    public int length;

    public Sprite(SpriteSheet sheet, int x, int y){
        image = sheet.getSprite(x, y);
    }

    public BufferedImage getBufferedImage(){
        return image;
    }
}
