package graphics.GUI;

import main.Main;

import java.awt.*;

public class Launcher {

    Main game = new Main();

    public void render(Graphics g){
        g.setColor(Color.CYAN);
        g.fillRect(0, 0, game.getFrameWidth(), game.getFrameHeight());

    }
}
