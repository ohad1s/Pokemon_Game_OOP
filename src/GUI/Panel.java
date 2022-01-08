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

public class Panel extends JPanel {
    DiGraph graph;
    Game myGame;
    final Dimension ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
    double minX, minY, maxX, maxY;
    double X_par, Y_par;
    Image[] pokemonsImages; //= new Image[10];
    JButton quit;


    /**
     * this method is the constructor of Panel
     */
    public Panel(DiGraph graph, Game myGame) {
        super();
        this.myGame = myGame;
        this.graph = graph;
        this.setSize(ScreenSize.width, ScreenSize.height);
        this.minX = Double.MAX_VALUE;
        this.maxY = Double.MIN_VALUE;
        this.maxX = Double.MIN_VALUE;
        this.minY = Double.MAX_VALUE;
        this.X_par = 0;
        this.Y_par = 0;
        this.quit=new JButton("EXIT");
        this.quit.setLocation(0,0);
        this.quit.setBackground(Color.RED);
        this.quit.setForeground(Color.white);
        this.add(quit);
        this.pokemonsImages = new Image[10];
        try {
            BufferedImage img1 = ImageIO.read(new File("Pokemon_Game_OOP-main\\src\\GUI\\images\\charizard.png"));
            BufferedImage img2 = ImageIO.read(new File("Pokemon_Game_OOP-main\\src\\GUI\\images\\poke1.png"));
            Image imgg1 = img1.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            Image imgg2 = img2.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            this.pokemonsImages[0] = imgg1;
            this.pokemonsImages[1] = imgg2;
        }
        catch (Exception e){System.out.println("FileNotFound");}




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
        DrawNodes(g);
        DrawEdges(g);
        DrawPokemons(g);
        DrawAgents(g);
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
    }

    /**
     * this method draws the agents of the game on the graphWindow
     *
     * @param g
     */
    private void DrawAgents(Graphics g) {
        if (myGame.agents.size() > 0) {
            Iterator<Agent> iter = this.myGame.agents.iterator();
            while (iter.hasNext()) {
                Agent a = iter.next();
                int x1 = (int) ((a.x() - this.minX) * this.X_par);
                int y1 = (int) ((a.y() - this.minY) * this.Y_par)+this.getHeight()/11;
//                g.setColor(Color.BLUE);
//                g.fillOval(x1, y1, 18, 18);
                try {
                    BufferedImage poke = ImageIO.read(new File("C:\\Users\\shira\\Desktop\\Ex4\\src\\GUI\\images\\POKEBALL.gif"));
                    Image pokeball = poke.getScaledInstance(35,35, Image.SCALE_SMOOTH);
                    g.drawImage(pokeball,x1-6,y1-6,this);
                }
                catch (Exception e){}
            }
        }
    }


    /**
     * this method draws the pokemons of the game on the graphWindow
     *
     * @param g
     */
    private void DrawPokemons(Graphics g) {
        if (myGame.pokemons.size() > 0) {
//        for (Pokemon p : this.myGame.pokemons) {
            Iterator<Pokemon> iter = this.myGame.pokemons.iterator();
            while (iter.hasNext()) {
                Pokemon p = iter.next();
                int x1 = (int) ((p.x() - this.minX) * this.X_par);
                int y1 = (int) ((p.y() - this.minY) * this.Y_par)+this.getHeight()/11;
                Image to_draw;
                if(p.getType() > 0) {
                    to_draw=this.pokemonsImages[1];
                }else{
                    to_draw=this.pokemonsImages[0];
                }
                g.drawImage(to_draw,x1-15,y1-15,this);
//                g.fillOval(x1, y1, 18, 18);
            }
        }
    }

    /**
     * this method draws the nodes of the graph on the graphWindow
     *
     * @param g
     */
    private void DrawNodes(Graphics g) {
        Iterator<Vertex> iter1 = this.graph.nodeIter();
        while (iter1.hasNext()) {
            Vertex point = iter1.next();
            if (point.getLocation().x() > this.maxX) {
                this.maxX = point.getLocation().x();
            }
            if (point.getLocation().y() < this.minY) {
                this.minY = point.getLocation().y();
            }
            if (point.getLocation().x() < this.minX) {
                this.minX = point.getLocation().x();
            }
            if (point.getLocation().y() > this.maxY) {
                this.maxY = point.getLocation().y();
            }
        }
        this.X_par = (this.getWidth()) / (maxX - minX) * 0.9;
        this.Y_par = (this.getHeight()) / (maxY - minY) * 0.8;
        Iterator<Vertex> iter = this.graph.nodeIter();
        while (iter.hasNext()) {
            Vertex point = iter.next();
            g.setColor(Color.BLACK);
            g.fillOval((int) ((point.getLocation().x() - this.minX) * this.X_par), (int) ((point.getLocation().y() - this.minY) * this.Y_par)+this.getHeight()/11, 18, 18);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(Color.RED);
            g2d.setStroke(new BasicStroke(12));
            int key = point.getKey();
            String keyS = String.valueOf(key);
            g2d.drawString(keyS, (int) ((point.getLocation().x() - this.minX) * this.X_par), (int) ((point.getLocation().y() - this.minY) * this.Y_par)+this.getHeight()/11);
        }
    }

    /**
     * * this method draws the edges of the graph on the graphWindow
     *
     * @param g
     */
    private void DrawEdges(Graphics g) {
        Iterator<Edge> iter = this.graph.edgeIter();
        while (iter.hasNext()) {
            Edge arrow = iter.next();
            Vertex p1 = this.graph.getNode(arrow.getSrc());
            Vertex p2 = this.graph.getNode(arrow.getDest());
            int x1 = (int) ((p1.getLocation().x() - this.minX) * this.X_par);
            int y1 = (int) ((p1.getLocation().y() - this.minY) * this.Y_par)+this.getHeight()/11;
            int x2 = (int) ((p2.getLocation().x() - this.minX) * this.X_par);
            int y2 = (int) ((p2.getLocation().y() - this.minY) * this.Y_par)+this.getHeight()/11;
            Arrow ar = new Arrow(x1 + 9, y1 + 9, x2 + 9, y2 + 9, Color.BLUE, 1);
            ar.draw(g);
        }
    }


}

