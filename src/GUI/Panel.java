package GUI;

import graph.DirectedWeightedClass;
import graph.EdgeDataClass;
import graph.NodeDataClass;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;
import java.util.List;

public class Panel extends JPanel {
    DirectedWeightedClass graph;
    final Dimension ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
    double minX,minY,maxX,maxY;
    double X_par, Y_par;


    /**
     * this method is the constructor of Panel
     */
    public Panel(DirectedWeightedClass graph) {
        super();
        this.graph = graph;
        this.setSize(ScreenSize.width, ScreenSize.height);
        this.minX=Double.MAX_VALUE;
        this.maxY=Double.MIN_VALUE;
        this.maxX=Double.MIN_VALUE;
        this.minY=Double.MAX_VALUE;
        this.X_par=0;
        this.Y_par=0;
    }

    /**
     * this method is Override of paintComponent of JPanel
     * this method draws the graph on the graphWindow
     * @param g
     */
    @Override
    protected void paintComponent(Graphics g) {
        DrawNodes(g);
        DrawEdges(g);
    }

    /**
     * this method draws the nodes of the graph on the graphWindow
     * @param g
     */
    private void DrawNodes(Graphics g) {
        Iterator<NodeDataClass> iter1=this.graph.nodeIter();
        while (iter1.hasNext()) {
            NodeDataClass point =iter1.next();
            if (point.getLocation().x()>this.maxX){
                this.maxX=point.getLocation().x();
            }
            if (point.getLocation().y()<this.minY){
                this.minY=point.getLocation().y();
            }
            if (point.getLocation().x()<this.minX){
                this.minX=point.getLocation().x();
            }
            if (point.getLocation().y()>this.maxY){
                this.maxY=point.getLocation().y();
            }
        }
        this.X_par=(ScreenSize.width)/(maxX-minX)*0.9;
        this.Y_par=(ScreenSize.height)/(maxY-minY)*0.8;
        Iterator<NodeDataClass>iter=this.graph.nodeIter();
        while (iter.hasNext()) {
            NodeDataClass point = iter.next();
            g.setColor(Color.BLACK);
            g.fillOval((int) ((point.getLocation().x()-this.minX)*this.X_par),(int) ((point.getLocation().y()-this.minY)*this.Y_par),18,18);
            Graphics2D g2d=(Graphics2D)g;
            g2d.setColor(Color.RED);
            g2d.setStroke(new BasicStroke(12));
            int key= point.getKey();
            String keyS=String.valueOf(key);
            g2d.drawString(keyS,(int) ((point.getLocation().x()-this.minX)*this.X_par),(int) ((point.getLocation().y()-this.minY)*this.Y_par));
        }
    }

    /**
     ** this method draws the edges of the graph on the graphWindow
     * @param g
     */
    private void DrawEdges(Graphics g) {
        Iterator<EdgeDataClass>iter=this.graph.edgeIter();
        while (iter.hasNext()) {
            EdgeDataClass arrow = iter.next();
            NodeDataClass p1=this.graph.getNode(arrow.getSrc());
            NodeDataClass p2=this.graph.getNode(arrow.getDest());
            int x1=(int) ((p1.getLocation().x()-this.minX)*this.X_par);
            int y1=(int) ((p1.getLocation().y()-this.minY)*this.Y_par);
            int x2=(int) ((p2.getLocation().x()-this.minX)*this.X_par);
            int y2=(int) ((p2.getLocation().y()-this.minY)*this.Y_par);
            Arrow ar=new Arrow(x1+9,y1+9,x2+9,y2+9,Color.BLUE,1);
            ar.draw(g);
        }
    }


}

