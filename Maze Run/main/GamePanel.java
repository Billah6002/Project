package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

import entity.Player;
import tile.TileManeger;

public class GamePanel extends JPanel implements Runnable{

    //SCREEN SETING
    final int originalTileSize = 16;
    final int scale = 3;
    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;
    int FPS = 60;

    TileManeger tileM = new TileManeger(this);
    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    Player player = new Player(this, keyH);

    public GamePanel(){

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void StartGameThread(){

        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lasttime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {

            currentTime = System.nanoTime();
            delta += (currentTime - lasttime) / drawInterval;
            lasttime = currentTime;

            if (delta >= 1) {
                // upadation carecter information etc
                update();

                //drawing the updated information
                repaint();
                delta--;

            }
           
        }
        
    }

    public void update(){

        player.update();

    }

    public void paintComponent(Graphics g){

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        tileM.draw(g2);
        player.draw(g2);
        g2.dispose();
    }

}
