package Tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class TileManager {

    GamePanel gp;
    Tile[] tile;        // Array storing different tile types
    int[][] mapTileNum; // 2D array representing the map using tile indices

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[10]; // Stores different tile types (grass, water, wall, etc.)
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow]; // Stores the tile layout of the world

        loadMap("/maps/world01.txt"); // Load the map data from a file
        getTileImage(); // Load tile images
    }

    // Loads tile images and sets their collision properties
    public void getTileImage() {
        try {
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/grass.png")));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/wall.png")));
            tile[1].collision = true;

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/water.png")));
            tile[2].collision = true;

            tile[3] = new Tile();
            tile[3].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/earth.png")));

            tile[4] = new Tile();
            tile[4].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/tree.png")));
            tile[4].collision = true;

            tile[5] = new Tile();
            tile[5].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/sand.png")));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Reads a map file and stores the tile layout in `mapTileNum`
    public void loadMap(String filePath) {
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            if (is == null) {
                throw new RuntimeException("Map file not found: " + filePath);
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int row = 0;
            while (row < gp.maxWorldRow) {
                String line = br.readLine();
                if (line == null) break; // Prevents null pointer exception

                String[] numbers = line.split(" "); // Splits the row into tile indices

                for (int col = 0; col < gp.maxWorldCol && col < numbers.length; col++) {
                    mapTileNum[col][row] = Integer.parseInt(numbers[col]); // Store tile index
                }
                row++;
            }
            br.close();
        } catch (Exception e) {
            throw new RuntimeException("Error loading map: " + e.getMessage(), e);
        }
    }

    // Draws the tiles on the screen relative to the player's position (camera effect)
    public void draw(Graphics2D g2) {
        for (int row = 0; row < mapTileNum.length; row++) {
            for (int col = 0; col < mapTileNum.length; col++) {
                int worldX = col * gp.tileSize; // Tile's world position (X)
                int worldY = row * gp.tileSize; // Tile's world position (Y)

                // Calculate the tile's position on the screen relative to the player's position
                int screenX = worldX - gp.player.worldX + gp.player.screenX;
                int screenY = worldY - gp.player.worldY + gp.player.screenY;

                // Only draw tiles within the screen boundaries for performance optimization
                if (screenX + gp.tileSize > 0 && screenX < gp.screenWidth &&
                        screenY + gp.tileSize > 0 && screenY < gp.screenHeight) {

                    int tileIndex = mapTileNum[col][row];

                    // Draw the tile at the calculated screen position
                    g2.drawImage(tile[tileIndex].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
                }
            }
        }
    }
}
