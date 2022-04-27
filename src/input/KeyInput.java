package input;

import entity.Entity;
import main.Id;
import main.Main;
import tile.Tile;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInput implements KeyListener  {

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        //for(Entity en: Main.handler.entity)
        for(int i=0; i<Main.handler.entity.size();i++){
            Entity en = Main.handler.entity.get(i);
            if(en.getId()== Id.player){
                if(en.goingDownPipe) return;
                //after going down pipe it makes sure no code under that is running
                switch(key){
                    case KeyEvent.VK_W:
                        if(!en.jumping){
                            en.jumping = true;
                            en.gravity = 9.0;
                        }
                        break;
                case KeyEvent.VK_S:
                    for(int j=0;j<Main.handler.tile.size();j++){
                        Tile t = Main.handler.tile.get(j);
                        //en.setVelY(5);
                        if(t.getId() == Id.pipe){
                            if(en.getBoundsBottom().intersects(t.getBounds())){
                                if(!en.goingDownPipe) en.goingDownPipe = true;
                            }
                        }
                    }
                    break;

                    case KeyEvent.VK_A:
                        en.setVelX(-5);
                        en.facing = 0;
                        break;
                    case KeyEvent.VK_D:
                        en.setVelX(5);
                        en.facing = 1;
                        break;
                }
            }
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        for(Entity en: Main.handler.entity){
            if(en.getId() == Id.player){
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
