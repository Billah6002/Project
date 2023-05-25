package entity;

import java.awt.Graphics2D;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

// import java.awt.Color;
import main.KeyHandler;
import main.GamePanel;

public class Player extends Entity{
    GamePanel gp;
    KeyHandler keyH;

    public Player(GamePanel gp, KeyHandler keyH){
        this.gp = gp;
        this.keyH = keyH;
        setDefaultValue();
        setDefaultImage();
    }

    public void setDefaultValue(){
        x = 100;
        y = 100;
        speed = 4;
        direction = "down";
    }

    public void setDefaultImage(){
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/Res/player/boy_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/Res/player/boy_up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/Res/player/boy_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/Res/player/boy_down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/Res/player/boy_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/Res/player/boy_left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/Res/player/boy_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/Res/player/boy_right_2.png"));


        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public void update(){
        if(keyH.downPressed == true || keyH.leftPressed == true || 
        keyH.rightPressed == true || keyH.upPressed == true){

            if(keyH.upPressed == true){
                direction = "up";
                y -= speed;
                // if(y<0) y = gp.screenHeight;
            }
            else if(keyH.downPressed == true){
                direction = "down";
                y += speed;
                // if(y > gp.screenHeight) y = 0;
            }
    
            else if(keyH.leftPressed == true){
                direction = "left";
                x -= speed;
                // if(x < 0) x = gp.screenWidth;
            }
            else if(keyH.rightPressed == true){
                direction = "right";
                x += speed;
                // if(x > gp.screenWidth) x = 0;
            }
    
            sprite_counter++;
            if(sprite_counter > 12){
                if(sprite_number == 1){
                    sprite_number = 2;
                }
                else if(sprite_number == 2){
                    sprite_number = 1;
                }
                sprite_counter = 0; 
            }
        }
        

    }

    public void draw(Graphics2D g2){
        // g2.setColor(Color.white);
        // g2.fillRect(x, y, gp.tileSize, gp.tileSize);

        BufferedImage image = null;

        switch (direction) {
            case "up":
                if(sprite_number == 1){
                    image = up1;
                }
                if(sprite_number == 2){
                    image = up2;
                }
            break;

            case "down":
                if(sprite_number == 1){
                    image = down1;
                }
                if(sprite_number == 2){
                    image = down2;
                }
                    
            break;

            case "left":
                if(sprite_number == 1){
                    image = left1;
                }
                if(sprite_number == 2){
                    image = left2;
                }
                
            break;

            case "right":
                if(sprite_number == 1){
                    image = right1;
                }
                if(sprite_number == 2){
                    image = right2;
                }
            break;
        }
        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);

    }
}
