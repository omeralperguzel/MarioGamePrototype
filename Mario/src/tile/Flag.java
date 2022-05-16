package tile; // class sýfýrdan eklendi

import java.awt.Graphics;


import main.Handler;
import main.Id;
import main.Main; // main error(drawImaginge)'u düzelsin diye otomatik yapýldý

public class Flag extends Tile {

	public Flag(int x, int y, int width, int height, boolean solid, Id id, Handler handler) {
		super(x, y, width, height, solid, id, handler);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(Main.flag[1].getBufferedImage(), getX(), getY(), width, 64, null);
		
		g.drawImage(Main.flag[2].getBufferedImage(), getX(), getY()+64, width, 64 , null);
		g.drawImage(Main.flag[2].getBufferedImage(), getX(), getY()+128, width, 64 , null);
		g.drawImage(Main.flag[2].getBufferedImage(), getX(), getY()+192, width, 64 , null);
		
		g.drawImage(Main.flag[0].getBufferedImage() , getX() , height-64, width , 64 , null);
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}


	

}
