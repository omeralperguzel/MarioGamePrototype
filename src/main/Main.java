package main;

import entity.Entity;
import graphics.Sprite;
import graphics.SpriteSheet;
import input.KeyInput;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

//uses abstract windowing toolkit libraries for window dimensions etc.
public class Main extends Canvas implements Runnable{

    public static final int WIDTH = 270;
    public static final int HEIGHT = WIDTH/14*10;
    public static final int SCALE = 4;
    public static final String TITLE = "Super Mario Bros";

    private Thread thread;
    private boolean running = false;
    private BufferedImage image;

    public static Handler handler;
    public static SpriteSheet sheet;
    public static Camera cam;

    public static Sprite groundblock;
    public static Sprite[] player = new Sprite[10];
    public static Sprite mushroom;
    public static Sprite[] goomba;
    //had to think about that power up one day due to 2 character reasons

    private synchronized void start() {
        if(running) return;
        running = true;
        thread = new Thread(this,"Thread");
        thread.start();
    }

    private synchronized void stop() {
        if(!running) return;
        //since it tries to run risky code
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Main() {
        Dimension size = new Dimension(WIDTH*SCALE,HEIGHT*SCALE);
        setPreferredSize(size);
        setMaximumSize(size);
        setMinimumSize(size);
    }

    private void init(){
        /* abbreviation of initialize, initializing the player object in this game */
        handler = new Handler();
        sheet = new SpriteSheet("/spritesheet.png");
        cam = new Camera();

        addKeyListener(new KeyInput());

        groundblock = new Sprite(sheet,1,1);
        //player = new Sprite[8];
        mushroom = new Sprite(sheet,2, 1);
        goomba = new Sprite[8];
        //PLAYER 1 SPRITES
        for(int i=0; i<player.length; i++){
            player[i] = new Sprite(sheet,i+1,16);
        }
        //GOOMBA SPRITES
        for(int i=0; i<goomba.length; i++){
            goomba[i] = new Sprite(sheet,i+1,15);
        }

        try {
            image = ImageIO.read(getClass().getResource("/leveltest0.png"));
        } catch (IOException e){
            e.printStackTrace();
        }

        handler.createLevel(image);

        //Removed in part 18 for new level design system
        //handler.addEntity(new Player(300,512,64,64,true,Id.player,handler));
        //Removed in part 13 (remove this note before deadline)
        //handler.addTile(new Wall(200,200,64,64,true,Id.wall,handler));
    }

    public void run(){

        init();
        requestFocus();
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        //current time in milliseconds.
        double delta = 0.0;
        double ns = 1000000000.0/60.0;
        int frames = 0;
        int ticks = 0;

        while(running) {
            long now = System.nanoTime();
            delta+=(now-lastTime)/ns;
            lastTime = now;
            while(delta>=1){
                tick();
                ticks++;
                delta--;
            }
            render();
            frames++;
            if(System.currentTimeMillis()-timer>1000){
                timer+=1000;
                System.out.println(frames + " fps " + ticks + " updates per second");
                frames = 0;
                ticks = 0;
            }
        }
        stop();
    }

    public void render(){
        BufferStrategy bs = getBufferStrategy();
            if(bs==null){
                createBufferStrategy(3);
                return;
            }
            Graphics g = bs.getDrawGraphics();
            g.setColor(Color.BLACK);
            //g.setColor(new Color(125,125,185));
            g.fillRect(0,0,getWidth(),getHeight());
            g.translate(cam.getX(),cam.getY());
            handler.render(g);
            //g.setColor(Color.RED);
            //g.fillRect(200,200,getWidth()-400,getHeight()-400);
            g.dispose();
            bs.show();
        }

    public void tick(){
        handler.tick();

        for(Entity e:handler.entity){
            //look at what is entity and handler
            if(e.getId()==Id.player){
                cam.tick(e);
            }
        }
    }

    public int getFrameWidth(){
        return WIDTH*SCALE;
    }

    public int getFrameHeight(){
        return HEIGHT*SCALE;
    }

    public static void main(String [] args){
        Main game = new Main();
        JFrame frame = new JFrame(TITLE);
        frame.add(game);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        game.start();
//ne olduklarini ogren
    }

}

