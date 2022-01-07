package GUI;

import graph.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Random;

public class FinishPanel extends JPanel {
    Game myGame;
    final Dimension ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
    JButton quit;


    /**
     * this method is the constructor of Panel
     */
    public FinishPanel(Game myGame) {
        super();
        this.myGame = myGame;
        this.setSize(ScreenSize.width, ScreenSize.height);
        this.quit=new JButton("EXIT");
        this.quit.setLocation(0,0);
        this.quit.setBackground(Color.RED);
        this.quit.setForeground(Color.white);
        this.add(quit);
    }

    /**
     * this method is Override of paintComponent of JPanel
     * this method draws the graph on the graphWindow
     *
     * @param g
     */
    @Override
    protected void paintComponent(Graphics g) {
        try {
            BufferedImage img1 = ImageIO.read(new File("C:\\Users\\shira\\Desktop\\Ex4\\src\\GUI\\images\\background.png"));
            Image imgg1 = img1.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH);
            g.drawImage(imgg1, 0, 0, this);
        } catch (IOException ex) {
        }
        DrawInfo(g);
    }
    /**
     * this method draws the info of the game on the graphWindow
     *
     * @param g
     */
    private void DrawInfo(Graphics g) {
        Font StringInfoFont=new Font("Ariel",Font.HANGING_BASELINE,36);
        g.setFont(StringInfoFont);
        g.setColor(Color.BLACK);
        g.drawString("Moves: "+this.myGame.moves,(this.getWidth()/4)*3,this.getHeight()/12);
        g.drawString("Score: "+this.myGame.score,(this.getWidth()/2)-75,this.getHeight()/12);
        g.drawString("Time: "+this.myGame.ttl,this.getWidth()/12,this.getHeight()/12);
        Font StringFinishFont=new Font("Ariel",Font.BOLD,150);
        g.setFont(StringFinishFont);
        g.setColor(Color.RED);
        g.drawString("Game Over!",this.getWidth()/4,this.getHeight()/2);
    }




}


