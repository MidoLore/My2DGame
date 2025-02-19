package Tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class TileManager {

    GamePanel gp;
    Tile[] tile;

    public TileManager(GamePanel gp){
        this.gp = gp;
        tile = new Tile[10];

        getTileImage();
    }

    public void getTileImage(){

        try {
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/earth.png"));

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/sand.png"));

            tile[3] = new Tile();
            tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));

            tile[4] = new Tile();
            tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tree.png"));

            tile[5] = new Tile();
            tile[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water.png"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void draw(Graphics2D g2){
        int[][] tileMap = {
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0},
                {1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1},
                {2, 2, 2, 2, 2, 2},
                {2, 2, 2, 2, 2, 2},
                {3, 3, 3, 3, 3, 3},
                {3, 3, 3, 3, 3, 3},
                {4, 4, 4, 4, 4, 4},
                {4, 4, 4, 4, 4, 4},
                {5, 5, 5, 5, 5, 5},
                {5, 5, 5, 5, 5, 5}
        };

        for (int row = 0; row < gp.maxScreenRow; row++) {
            for (int col = 0; col < gp.maxScreenCol; col++) {
                int x = col * gp.tileSize;
                int y = row * gp.tileSize;

                int tileIndex = tileMap[row % tileMap.length][col % tileMap[0].length]; // Wraps map if screen is bigger
                g2.drawImage(tile[tileIndex].image, x, y, gp.tileSize, gp.tileSize, null);
            }
        }
    }
}
