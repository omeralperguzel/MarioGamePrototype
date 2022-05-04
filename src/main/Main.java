package main;

import entity.Entity;
import graphics.GUI.Launcher;
import graphics.Sprite;
import graphics.SpriteSheet;
import input.KeyInput;
import input.MouseInput;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

//uses abstract windowing toolkit libraries for window dimensions etc.
public class Main<second> extends Canvas implements Runnable{

    public static final int WIDTH = 270;
    public static final int HEIGHT = WIDTH/14*10;
    public static final int SCALE = 4;
    public static final String TITLE = "Super Mario Bros";

    private Thread thread;
    private boolean running = false;
    private BufferedImage image;
    private BufferedImage darksoulsyoudied;

    public int secondscount;

    public static int coins = 0;
    public static int lives = 5;
    //time for showing game over screen on display
    public static int deathScreenTime = 0;

    public static boolean showDeathScreen = true;
    public static boolean gameOver = false;
    public static boolean playing = false;

    private static BufferedImage background;

    public static Handler handler;
    public static SpriteSheet sheet;
    public static Camera cam;
    public static Launcher launcher;
    public static MouseInput mouse;

    public static Sprite groundblock;
    public static Sprite[] player;
    public static Sprite mushroom;
    public static Sprite coin;
    public static Sprite powerUp;
    public static Sprite usedPowerUp;
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
        launcher = new Launcher();
        mouse = new MouseInput();

        addKeyListener(new KeyInput());
        addMouseListener(mouse);
        addMouseMotionListener(mouse);

        groundblock = new Sprite(sheet,1,1);
        player = new Sprite[10];
        //POWER UP SPRITES
        powerUp = new Sprite(sheet, 3,1);
        usedPowerUp = new Sprite(sheet, 1,1);
        mushroom = new Sprite(sheet,2, 1);
        coin = new Sprite(sheet,4,1);

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
            //background = ImageIO.read(getClass().getResource("/background.png"));
            background = ImageIO.read(getClass().getResource("/backgroundtest1.png"));
            darksoulsyoudied = ImageIO.read(getClass().getResource("/darksoulsyoudied.png"));
        } catch (IOException e){
            e.printStackTrace();
        }

        //Removed in part 26 for new lives system
        //handler.createLevel(image);

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
                secondscount++;
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

        if (showDeathScreen && playing) {
            if(!gameOver){
                g.setColor(new Color(0,0,0));
                g.fillRect(0,0,getWidth()-0,getHeight()-0);
                g.setColor(Color.WHITE);
                //show lives
                g.drawImage(Main.player[0].getBufferedImage(),WIDTH*4/2-120,HEIGHT*4/2-50,60,60,null);
                g.setFont(new Font("Pixel NES",Font.PLAIN,45));
                g.drawString("x" + lives, WIDTH*4/2-30, HEIGHT*4/2);
                if(playing) g.translate(cam.getX(),cam.getY());
                if(playing) handler.render(g);
            }
            else{
                g.setColor(new Color(0,0,0));
                g.fillRect(0,0,getWidth()-0,getHeight()-0);
                g.setColor(Color.RED);
                g.drawImage(Main.goomba[0].getBufferedImage(),WIDTH*4/2-190,HEIGHT*4/2-50,60,60,null);
                //g.setFont(new Font("Tahoma",Font.BOLD,45));
                //g.drawString("GAME OVER!", WIDTH*4/2-100, HEIGHT*4/2);
                //For Dark Souls references :D
                g.drawImage(darksoulsyoudied, 0, 0, getWidth(), getHeight(), null);
                secondscount = 0;
            }
        }
        else if(!playing) launcher.render(g);

            //g.setColor(new Color(125,125,185));
            //g.fillRect(0,0,getWidth(),getHeight());


            if(!showDeathScreen && playing){
                g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
                //COIN ADDITIONS
                g.drawImage(Main.coin.getBufferedImage(),50,20,60,60,null);
                g.setColor(Color.WHITE);
                g.setFont(new Font("Pixel NES",Font.PLAIN,45));
                g.drawString(":" + coins, 120, 80);
                //TIMER EXPERIMENT
                g.setFont(new Font("Pixel NES",Font.PLAIN,45));
                g.drawString("Time: " + secondscount, getWidth()/2-280, 80);
                //SCORE EXPERIMENT
                g.setFont(new Font("Pixel NES",Font.PLAIN,45));
                g.drawString("Score: " + scorecalctest1, getWidth()/2+40, 80);
                //LIVE SYSTEM ADDITIONS
                g.drawImage(Main.player[1].getBufferedImage(),getWidth()-172,20,60,60,null);
                g.setFont(new Font("Pixel NES",Font.PLAIN,45));
                g.drawString("x" + lives, getWidth()-100, 80);
                //for rendering blocks only if show death screen is false
                if(playing) g.translate(cam.getX(),cam.getY());
                if(playing) handler.render(g);

            }
            //

            //g.setColor(Color.RED);
            //g.fillRect(200,200,getWidth()-400,getHeight()-400);
            g.dispose();
            bs.show();
        }

    public void tick(){
        if(playing) handler.tick();

        for(int i=0; i<handler.entity.size(); i++){
            Entity e = handler.entity.get(i);
            if(e.getId()==Id.player){
                if(!e.goingDownPipe) cam.tick(e);
            }
        }

        for(Entity e:handler.entity){
            //look at what is entity and handler
            if(e.getId()==Id.player){
                cam.tick(e);
            }
        }
        if(showDeathScreen) deathScreenTime++;
        if(deathScreenTime>=180) {
            showDeathScreen = false;
            deathScreenTime = 0;
            handler.clearLevel();
            handler.createLevel(image);
        }
    }

    public static int getFrameWidth(){
        return WIDTH*SCALE;
    }

    public static int getFrameHeight(){
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
    }

    int scorecalctest1 = coins*10-secondscount/10;

    }



