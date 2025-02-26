package main;


import Tile.TileManager;
import entity.Player;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{

    // Tile settings
    final int originalTileSize = 16;
    final int scale = 3;
    public final int tileSize = originalTileSize * scale;  // 48 pixels

    // Screen settings
    public final int maxScreenCol = 18;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;  // 768 pixels
    public final int screenHeight = tileSize * maxScreenRow;  //576 pixels

    //World Settings
    public  final int maxWorldCol = 50;
    public  final int maxWorldRow = 50;
    public  final int worldWidth = tileSize * maxWorldCol;
    public  final int worldHeight = tileSize * maxScreenRow;

    // FPS
    int FPS = 60;

    // Game components
    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    public Player player = new Player(this, keyH);
    public TileManager tileM = new TileManager(this);
    public CollisionChecker cChecker = new CollisionChecker(this);

    // Constructor: Sets up the game panel settings
    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.GRAY);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    // Starts the game loop in a new thread
    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    // The game loop, running at a fixed FPS
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

    // Updates the game state (e.g., player movement)
    public void update() {
        player.update();
    }

    // Draws game elements to the screen
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        tileM.draw(g2); // Draws tiles
        player.draw(g2); // Draws player

        g2.dispose();

    }
}
