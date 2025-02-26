package entity;

import main.GamePanel;
import main.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;

public class Player extends Entity {

    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);
        this.keyH = keyH;

        // Set the player's screen position to the center
        this.screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        this.screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        solidArea = new Rectangle(8,12,32, 32);

        setDefaultValues();
        getPlayerImage();
    }

    // Sets the player's starting position and attributes
    public void setDefaultValues() {
        worldX = gp.tileSize * 23; // Start position in world coordinates
        worldY = gp.tileSize * 21;
        speed = 5;                 // Movement speed
        direction = "down";         // Initial facing direction
    }

    // Loads player images for different movement directions
    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/up_1.png")));
            up2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/up_2.png")));
            down1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/down_1.png")));
            down2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/down_2.png")));
            left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/left_1.png")));
            left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/left_2.png")));
            right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/right_1.png")));
            right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/right_2.png")));
        } catch (IOException e) {
            System.err.println("Error loading player images: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Updates the player's position based on input
    public void update() {
        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            int dx = 0;  // Change in x-direction
            int dy = 0;  // Change in y-direction

            // Determine movement direction
            if (keyH.upPressed) {
                direction = "up";
                dy = -speed;
            }
            if (keyH.downPressed) {
                direction = "down";
                dy = speed;
            }
            if (keyH.leftPressed) {
                direction = "left";
                dx = -speed;
            }
            if (keyH.rightPressed) {
                direction = "right";
                dx = speed;
            }

            // Adjust speed for diagonal movement
            if (dx != 0 && dy != 0) {
                dx = (int) Math.round(dx * 0.707);  // Reduce speed by sqrt(2)/2
                dy = (int) Math.round(dy * 0.707);
            }

            // 1. Check for collision in the X direction (horizontal)
            collisonOn = false;
            gp.cChecker.checkTile(this); // Check collision in the X direction
            if (!collisonOn) {
                worldX += dx;
                worldY += dy;
            }

            // Handle sprite animation
            spriteCounter++;
            if (spriteCounter > 12) {
                spriteNum = (spriteNum == 1) ? 2 : 1;
                spriteCounter = 0;
            }
        }
    }


    // Draws the player on the screen
    public void draw(Graphics2D g2) {
        // Select correct sprite based on movement direction and animation frame
        BufferedImage image = switch (direction) {
            case "up" -> (spriteNum == 1) ? up1 : up2;
            case "down" -> (spriteNum == 1) ? down1 : down2;
            case "left" -> (spriteNum == 1) ? left1 : left2;
            case "right" -> (spriteNum == 1) ? right1 : right2;
            default -> null;
        };


        // Draw the player at the fixed screen position (centered)
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }
}
