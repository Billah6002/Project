package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

// import java.awt.Color;
import main.KeyHandler;
import main.GamePanel;

public class Player extends Entity{
    GamePanel gp;
    KeyHandler keyH;
    public final int screenX;
    public final int screenY;
    public int hasKey = 0;

    public Player(GamePanel gp, KeyHandler keyH){
        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;

        setDefaultValue();
        setDefaultImage();
    }

    public void setDefaultValue(){
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
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
            }
            else if(keyH.downPressed == true){
                direction = "down";
            }   
            else if(keyH.leftPressed == true){
                direction = "left";                
            }
            else if(keyH.rightPressed == true){
                direction = "right";
            }

            //Check Collusion
            collusionOn = false;
            gp.cChecker.checkTile(this);

            //Check Object Collision
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);
            
            //if collusionOn is false the player can move
            if(collusionOn == false){
                switch(direction){
                    case "up":
                    worldY -= speed;
                    break;
                    case "down":
                    worldY += speed;
                    break;
                    case "left":
                    worldX -= speed;
                    break;
                    case "right":
                    worldX += speed;
                    break;
                }
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

    public void pickUpObject(int i){
        if(i != 999){
            String objectName = gp.obj[i].name;
            switch(objectName){
                case "Key":
                gp.playSE(1);
                hasKey++;
                gp.obj[i] = null;
                gp.ui.showMassage("You got a Key!");
                break;

                case "Door":
                
                if(hasKey > 0){
                    gp.ui.showMassage("You opened the door!");
                    gp.playSE(3);
                    gp.obj[i] = null;
                    hasKey--;
                }
                else{
                    gp.ui.showMassage("You need a key");
                }
                break;

                case "Boots":
                gp.ui.showMassage("Speed UP!");
                gp.playSE(2);
                speed += 2;
                gp.obj[i] = null;
                break;

                case "Chest":
                gp.ui.gameFinished = true;
                gp.stopMusic();
                gp.playSE(4);
                break;
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
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

    }
}
