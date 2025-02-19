package main;


import Tile.TileManager;
import entity.Player;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{
    //Initializing variables like dimension, tilesize, keyboard etc-
    final int originalTileSize = 16;
    final int scale = 3;

    public final int tileSize = originalTileSize * scale;  // 48 pixels
    public final int maxScreenCol = 18;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;  // 768 pixels
    public final int screenHeight = tileSize * maxScreenRow;  //576 pixels

    int FPS = 60;

    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    public Player player = new Player(this, keyH);
    public TileManager tileM = new TileManager(this);

    //Setting the Settings for the gamePanel like the dimesions, color
    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.GRAY);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }
    //Litrally starts the gameloop
    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }
    //A delta timer for limiting fps to 60
    @Override
    public void run() {
        double drawInterval = 1000000000/FPS; // 0.01666
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int frameCount = 0;
        while (gameThread != null){

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if(delta >= 1){
                update();
                repaint();
                delta--;
                frameCount++;
            }

            if (delta > 10) delta = 10;

            if (timer >= 1000000000) {
                System.out.println("FPS: " + frameCount);
                frameCount = 0;
                timer = 0;
            }
        }
    }

    public void update() {
        player.update();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        tileM.draw(g2);
        player.draw(g2);


        g2.dispose();

    }
}
