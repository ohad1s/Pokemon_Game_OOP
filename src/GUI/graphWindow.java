package GUI;



import graph.DirectedWeightedGraphAlgorithmsClass;
import graph.NodeDataClass;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.LinkedList;
import java.util.List;


public class graphWindow extends JFrame implements ActionListener {
    DirectedWeightedGraphAlgorithmsClass graph;
    Panel panel;
    final Dimension ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
    MenuBar menuBar;
    Menu menu, algo,actions;
    MenuItem load, save;
    MenuItem i1, i3, i4, i5;
    MenuItem remE, con,addN,remN;

    /**
     * this method is the constructor of graphWindow
     */
    public graphWindow(DirectedWeightedGraphAlgorithmsClass graph) {
        super();
        this.graph=graph;
        this.panel = new Panel(this.graph.getGraph());
        this.menuBar = new MenuBar();
        this.menu = new Menu("Menu");
        this.load = new MenuItem("load");
        this.save = new MenuItem("save");
        this.algo = new Menu("Choose Algorithm");
        this.i1 = new MenuItem("isConnected");
        this.i3 = new MenuItem("shortestPath");
        this.i4 = new MenuItem("center");
        this.i5 = new MenuItem("tsp");
        this.actions= new Menu("Actions on the graph");
        this.remE= new MenuItem("remove Edge");
        this.con=new MenuItem("connect Nodes");
        this.addN=new MenuItem("add Node");
        this.remN=new MenuItem("remove Node");
        this.menu.add(this.load);
        this.menu.add(this.save);
        this.menu.add(this.algo);
        this.menu.add(this.actions);
        this.actions.add(this.addN);
        this.actions.add(this.remN);
        this.actions.add(this.con);
        this.actions.add(this.remE);
        this.algo.add(this.i1);
        this.algo.add(this.i3);
        this.algo.add(this.i4);
        this.algo.add(this.i5);
        this.menuBar.add(this.menu);
        this.setMenuBar(this.menuBar);
        this.add(this.panel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(ScreenSize.width, ScreenSize.height);
        this.setBackground(Color.CYAN);
        this.setVisible(true);
        this.load.addActionListener(this);
        this.save.addActionListener(this);
        this.i1.addActionListener(this);
        this.i3.addActionListener(this);
        this.i4.addActionListener(this);
        this.i5.addActionListener(this);
        this.addN.addActionListener(this);
        this.remE.addActionListener(this);
        this.remN.addActionListener(this);
        this.con.addActionListener(this);


    }

    /**
     * this method checks which button from the menu is selected,
     * and activates its function on the graph according to the button
     * @param e
     * @throws HeadlessException
     */
    @Override
    public void actionPerformed(ActionEvent e) throws HeadlessException{
        if (e.getSource() == this.save) {
            JFileChooser j = new JFileChooser();
            int returnValue = j.showSaveDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = j.getSelectedFile();
                System.out.println("save....");
                this.graph.save(selectedFile.getAbsolutePath());
            }
        }
        else if (e.getSource() == this.load) {
            JFileChooser j = new JFileChooser();
            int returnValue = j.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = j.getSelectedFile();
                this.graph.load(selectedFile.getAbsolutePath());
            }

            this.remove(this.panel);
            this.panel=new Panel(this.graph.getGraph());
            this.add(this.panel);
            this.repaint();
            this.revalidate();

        }
        else if (e.getSource() == this.i1){
            Boolean isCon= (this.graph.isConnected());
            if (isCon==true){
                JOptionPane.showMessageDialog(this.panel,
                        "Yes! the graph is connected!",
                        "Connected!",
                        JOptionPane.INFORMATION_MESSAGE);
            }
            else{
                JOptionPane.showMessageDialog(this.panel,
                        "No, the graph isn't connected...",
                        "Not Connected!",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        else if (e.getSource() == this.i3){
            String NodesToCheck;
            NodesToCheck = JOptionPane.showInputDialog(this.panel, "Enter 2 Nodes to check\n (Example: 1,5)");
            String[]MyNodes=NodesToCheck.split(",");
            try {
                int first = Integer.valueOf(MyNodes[0]);
                int last = Integer.valueOf(MyNodes[1]);
                List<NodeDataClass> Shortest = this.graph.shortestPath(first, last);
                this.remove(this.panel);
                this.panel = new Panel(this.graph.getGraph());
                this.panel.shorted = true;
                this.panel.shortedP = Shortest;
                this.add(this.panel);
                this.repaint();
                this.revalidate();
                double dist = this.graph.shortestPathDist(first, last);
                JOptionPane.showMessageDialog(this.panel,
                        "The shortest path distance between " + first + " and " + last + " is:\n" + dist,
                        "You got a shortest path!",
                        JOptionPane.INFORMATION_MESSAGE);
            }
            catch (Exception E){
                JOptionPane.showMessageDialog(this.panel,
                        "An error has occurred, please try again!",
                        "Error!",
                        JOptionPane.ERROR_MESSAGE);
            }

        }
        else if (e.getSource() == this.i4){
            NodeDataClass center = this.graph.center();
            this.remove(this.panel);
            this.panel = new Panel( this.graph.getGraph());
            this.panel.isCenter = true;
            this.panel.center = center;
            this.add(this.panel);
            this.repaint();
            this.revalidate();
            JOptionPane.showMessageDialog(this.panel,
                    "The center node is:\n " + center.getKey(),
                    "Center Node!",
                    JOptionPane.INFORMATION_MESSAGE);
        }
        else if (e.getSource() == this.i5){
            try {
                String NodesToCheck;
                NodesToCheck = JOptionPane.showInputDialog(this.panel, "Enter group of Nodes to check\n (Example: 1,5,7,8)");
                String[] MyNodes = NodesToCheck.split(",");
                List<NodeDataClass> tspNodes = new LinkedList<NodeDataClass>();
                for (int i = 0; i < MyNodes.length; i++) {
                    int key = Integer.valueOf(MyNodes[i]);
                    tspNodes.add(this.graph.getGraph().getNode(key));
                }
                List<NodeDataClass> tsp = this.graph.tsp(tspNodes);
                this.remove(this.panel);
                this.panel = new Panel( this.graph.getGraph());
                this.panel.isTsp = true;
                this.panel.tspList = tsp;
                this.add(this.panel);
                this.repaint();
                this.revalidate();
                String msg = "";
                for (NodeDataClass node : tsp) {
                    msg = msg + node.getKey() + " ";
                }
                JOptionPane.showMessageDialog(this.panel,
                        "Travelling salesman problem- got solution:\n " + msg,
                        "TSP!",
                        JOptionPane.INFORMATION_MESSAGE);
            }
            catch (Exception E){
                JOptionPane.showMessageDialog(this.panel,
                        "An error has occurred, please try again!",
                        "Error!",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        else if (e.getSource() == this.addN) {
            String NodesToAdd;
            NodesToAdd = JOptionPane.showInputDialog(this.panel, "Enter location (x,y) to add\n (Example: 1,5)");
            try {
            String[] MyNodes = NodesToAdd.split(",");
                double x1 = Double.valueOf(MyNodes[0]);
                double y1 = Double.valueOf(MyNodes[1]);
                NodeDataClass addMe = new NodeDataClass(this.graph.getGraph().nodeSize(), x1, y1, 0);
                this.graph.getGraph().addNode(addMe);
                this.remove(this.panel);
                this.panel = new Panel( this.graph.getGraph());
                this.add(this.panel);
                this.repaint();
                this.revalidate();
            }
            catch (Exception E){
                JOptionPane.showMessageDialog(this.panel,
                        "An error has occurred, please try again!",
                        "Error!",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        else if(e.getSource() == this.con) {
            String NodesToCon;
            NodesToCon = JOptionPane.showInputDialog(this.panel, "Enter src, dest node to connect with weight \n (Example: 1,5,8)");
            String[] MyCon = NodesToCon.split(",");
            try {
                int key1 = Integer.valueOf(MyCon[0]);
                int key2 = Integer.valueOf(MyCon[1]);
                double w = Double.valueOf(MyCon[1]);
                this.graph.getGraph().connect(key1,key2,w);
                this.remove(this.panel);
                this.panel = new Panel( this.graph.getGraph());
                this.add(this.panel);
                this.repaint();
                this.revalidate();
            }
            catch (Exception E){
                JOptionPane.showMessageDialog(this.panel,
                        "An error has occurred, please try again!",
                        "Error!",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        else if(e.getSource() == this.remN) {
            String NodesToRem;
            NodesToRem = JOptionPane.showInputDialog(this.panel, "Enter node key to remove\n (Example: 5)");
            try {
                int key = Integer.valueOf(NodesToRem);
                this.graph.getGraph().removeNode(key);
                this.remove(this.panel);
                this.panel = new Panel(this.graph.getGraph());
                this.add(this.panel);
                this.repaint();
                this.revalidate();
            }
            catch (Exception E){
                JOptionPane.showMessageDialog(this.panel,
                        "An error has occurred, please try again!",
                        "Error!",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        else if (e.getSource() == this.remE){
            String NodesToRem;
            NodesToRem = JOptionPane.showInputDialog(this.panel, "Enter src and dst in order to remove the edge\n (Example: 5,8)");
            try {
            String[] MyNodes = NodesToRem.split(",");
                int p1 = Integer.valueOf(MyNodes[0]);
                int p2 = Integer.valueOf(MyNodes[1]);
                this.graph.getGraph().removeEdge(p1,p2);
                this.remove(this.panel);
                this.panel = new Panel( this.graph.getGraph());
                this.add(this.panel);
                this.repaint();
                this.revalidate();
            }
            catch (Exception E){
                JOptionPane.showMessageDialog(this.panel,
                        "An error has occurred, please try again!",
                        "Error!",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
