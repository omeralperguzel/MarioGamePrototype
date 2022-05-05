package graphics.GUI;

import main.Main;

import java.awt.*;
import java.util.Locale;

public class Button {

    public int x, y;
    public int width, height;

    public String label;

    public Button(int x, int y, int width, int height, String label) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.label = label;
    }

    public void render(Graphics g){
        g.setColor(Color.WHITE);
        g.setFont(new Font("Pixel NES",Font.PLAIN,45));
        //g.drawRect(getX(),getY(),getWidth(),getHeight());

        //aligning text in launcher
        FontMetrics fm = g.getFontMetrics();
        int stringX = (getWidth() - fm.stringWidth(getLabel())) / 2;
        int stringY = (fm.getAscent() + (getHeight() - (fm.getAscent()+fm.getDescent()))/2);
        g.drawString(getLabel(),getX()+stringX,getY()+stringY);
    }

    public void triggerEvent(){
        if(getLabel().toLowerCase().contains("play")) Main.playing = true;
        if(getLabel().toLowerCase().contains("help")) //return graphics.GUI.Launcher.drawHelpScreen();
        if(getLabel().toLowerCase().contains("credits")) //return graphics.GUI.Launcher.drawHelpScreen();
        if(getLabel().toLowerCase().contains("exit")) System.exit(0);
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

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
