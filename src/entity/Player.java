package entity;

import main.GamePanel;
import main.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
public class Player extends Entity{

    KeyHandler keyH;

    public Player(GamePanel gp, KeyHandler keyH){
        super(gp);
        this.keyH = keyH;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues(){
        worldX = 100;
        worldY = 100;
        speed = 4;
        direction = "down";
    }

    public void getPlayerImage(){
        try {

            up1 = ImageIO.read(getClass().getResourceAsStream("/player/up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/right_2.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void update(){
        if (keyH.upPressed == true | keyH.downPressed == true | keyH.leftPressed == true | keyH.rightPressed == true) {
            int dx = 0;
            int dy = 0;

            // Determine movement direction
            if (keyH.upPressed){
                dy -= 1;
                direction = "up";
            }
            if (keyH.downPressed){
                dy += 1;
                direction = "down";
            }
            if (keyH.leftPressed){
                dx -= 1;
                direction = "left";
            }
            if (keyH.rightPressed){
                dx += 1;
                direction = "right";
            }

            // Base speed
            int baseSpeed = speed;

            // Normalize diagonal movement
            if (dx != 0 && dy != 0) {
                baseSpeed = (int) Math.round(speed * 0.707); // Apply √2/2 for diagonal movement
            }

            // Update position
            worldX += dx * baseSpeed;
            worldY += dy * baseSpeed;

            spriteCounter++;
            if (spriteCounter > 12) {
                if (spriteNum == 1){
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }

    }

    public void draw(Graphics2D g2){


        BufferedImage image = null;

        switch (direction) {
            case "up":
                if(spriteNum == 1) {
                    image = up1;
                }
                if(spriteNum == 2) {
                    image = up2;
                }
                break;
            case "down":
                if(spriteNum == 1) {
                    image = down1;
                }
                if(spriteNum == 2) {
                    image = down2;
                }
                break;
            case "left":
                if(spriteNum == 1) {
                    image = left1;
                }
                if(spriteNum == 2) {
                    image = left2;
                }
                break;
            case "right":
                if(spriteNum == 1) {
                    image = right1;
                }
                if(spriteNum == 2) {
                    image = right2;
                }
                break;
        }

        g2.drawImage(image, worldX, worldY, gp.tileSize, gp.tileSize, null);
    }
}
