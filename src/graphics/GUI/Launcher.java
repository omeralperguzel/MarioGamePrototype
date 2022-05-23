package graphics.GUI;

import main.Main;
import states.LauncherState;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static states.LauncherState.*;

public class Launcher{

    public Button[] buttons;
    public BufferedImage mainmenu1;
    public BufferedImage[] mainmenu;
    public BufferedImage menubackgroundblock1;
    public BufferedImage test;
    public BufferedImage thankyouforplaying;
    public static LauncherState launcherState;

    {
        try {
            mainmenu1 = ImageIO.read(getClass().getResource("/mainmenu1.png"));
            //mainmenu[0] = ImageIO.read(getClass().getResource("/mainmenu1.png"));
            //mainmenu[1] = ImageIO.read(getClass().getResource("/menubackgroundblock1.png"));
            menubackgroundblock1 = ImageIO.read(getClass().getResource("/menubackgroundblock1.png"));
            thankyouforplaying = ImageIO.read(getClass().getResource("/thankyouscreen.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    ;

    public Launcher(){

        launcherState = LauncherState.BASE;

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
        if(launcherState == HELP){
            g.drawImage(menubackgroundblock1, game.getFrameWidth()/6-20,game.getFrameHeight()/6-90,game.getFrameWidth()-3*game.getFrameWidth()/10,game.getFrameHeight()-3*game.getFrameHeight()/10, null);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Pixel NES",Font.PLAIN,35));
            g.drawString("Use WASD for move", game.getFrameWidth()/6+33, 120);
            g.drawString("Esc for quick shutdown", game.getFrameWidth()/6+33, 180);
            g.setFont(new Font("Pixel NES",Font.PLAIN,25));
            g.drawString("For Debug purposes press", game.getFrameWidth()/6+33, 240);
            g.drawString("I for return to main menu", game.getFrameWidth()/6+33, 290);
            if(!Main.presentationmode){
                g.setFont(new Font("Pixel NES",Font.PLAIN,25));
                g.drawString("U for calling death screen", game.getFrameWidth()/6+33, 340);
                g.drawString("L for level switching", game.getFrameWidth()/6+33, 390);
                g.drawString("P for presentation switch", game.getFrameWidth()/6+33, 440);
            }
            g.setFont(new Font("Pixel NES",Font.PLAIN,35));
            g.drawString("Press backspace to return", game.getFrameWidth()/6+29, 520);
        }

        if(launcherState == CREDITS){
            g.drawImage(menubackgroundblock1, game.getFrameWidth()/6-20,game.getFrameHeight()/6-90,game.getFrameWidth()-3*game.getFrameWidth()/10,game.getFrameHeight()-3*game.getFrameHeight()/10, null);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Pixel NES",Font.BOLD,45));
            g.drawString("Created by:", game.getFrameWidth()/6+75, 120);
            g.setFont(new Font("Pixel NES",Font.PLAIN,43));
            g.drawString("Ömer Alper Güzel", game.getFrameWidth()/6+75, 190);
            g.setFont(new Font("Pixel NES",Font.PLAIN,40));
            g.drawString("Tugba Açik", game.getFrameWidth()/6+75, 260);
            g.drawString("Yagmur Saglam", game.getFrameWidth()/6+75, 330);
            g.drawString("Gizem Öz", game.getFrameWidth()/6+75, 400);
            g.setFont(new Font("Pixel NES",Font.PLAIN,35));
            g.drawString("Press backspace to return", game.getFrameWidth()/6+29, 520);
        }

        if(launcherState == THANKS){
            g.drawImage(thankyouforplaying, 0, 0, game.getFrameWidth(), game.getFrameHeight(), null);
            Main.oneup.play();

            //launcherState = launcherState.BASE;
        }
    }

}
