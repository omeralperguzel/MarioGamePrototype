package input;

import entity.Entity;
import main.Id;
import main.Main;
import states.LauncherState;
import tile.Tile;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import static graphics.GUI.Launcher.launcherState;
import static main.Main.launcher;

public class KeyInput implements KeyListener  {

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        for(Entity en: Main.handler.entity){
            if(en.getId()== Id.player){
                if(en.goingDownPipe) return;

                switch(key){
                    case KeyEvent.VK_W:
                        for(int q=0; q < Main.handler.tile.size(); q++){
                            Tile t = Main.handler.tile.get(q);
                            if(t.isSolid()){
                                if(en.getBoundsBottom().intersects(t.getBounds())){
                                    if(!en.jumping){
                                        en.jumping = true;
                                        en.gravity = 10.0;

                                        Main.jump.play();
                                    }
                                }
                                else if(t.getId() == Id.pipe){
                                if(en.getBoundsBottom().intersects(t.getBounds())){
                                    if(!en.goingDownPipe) en.goingDownPipe = true;
                                }
                            }
                          }
                        }
                        break;

                case KeyEvent.VK_S:
                    for(int q=0; q < Main.handler.tile.size(); q++){
                        Tile t = Main.handler.tile.get(q);
                        if(t.getId() == Id.pipe){
                            if(en.getBoundsBottom().intersects(t.getBounds())){
                                if(!en.goingDownPipe) en.goingDownPipe = true;
                            }
                        }
                    }
                    //en.setVelY(5);
                    break;

                    case KeyEvent.VK_A:
                        en.setVelX(-5);
                        en.facing = 0;
                        break;

                    case KeyEvent.VK_D:
                        en.setVelX(5);
                        en.facing = 1;
                        break;

                    case KeyEvent.VK_ESCAPE:
                        System.exit(0);
                        break;

                    case KeyEvent.VK_BACK_SPACE:
                        if(launcherState == LauncherState.CREDITS || launcherState == LauncherState.HELP){
                            launcherState = LauncherState.BASE;
                        }
                        break;

                    case KeyEvent.VK_U:
                        en.die(1);

                }
            }
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        for(Entity en: Main.handler.entity){
            if(en.getId()== Id.player){
                switch(key){
                    case KeyEvent.VK_W:
                        en.setVelY(0);
                        break;
                    /*case KeyEvent.VK_S:
                        en.setVelY(0);
                        break;*/
                    case KeyEvent.VK_A:
                        en.setVelX(0);
                        break;
                    case KeyEvent.VK_D:
                        en.setVelX(0);
                        break;
                }
            }

        }
    }

    public void keyTyped(KeyEvent e) {
       //not used for now
    }

}
