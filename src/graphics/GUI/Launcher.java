package graphics.GUI;

import main.Main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Launcher {

    public Button[] buttons;
    public BufferedImage mainmenu1;

    {
        try {
            mainmenu1 = ImageIO.read(getClass().getResource("/mainmenu1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    ;

    public Launcher(){
        buttons = new Button[4];

        buttons[0] = new Button(game.getFrameWidth()/2-150,430,300,40,"Play game");
        buttons[1] = new Button(game.getFrameWidth()/2-150,480,300,40,"Help");
        buttons[2] = new Button(game.getFrameWidth()/2-150,530,300,40,"Credits");
        buttons[3] = new Button(game.getFrameWidth()/2-150,580,300,40,"Exit game");

    }

    Main game = new Main();

    public void render(Graphics g){
        //g.setColor(Color.CYAN);
        //g.fillRect(0, 0, game.getFrameWidth(), game.getFrameHeight());
        g.drawImage(mainmenu1, 0,0,game.getFrameWidth(),game.getFrameHeight(), null);

        for(int i=0; i<buttons.length; i++){
            buttons[i].render(g);
        }

    }
}
