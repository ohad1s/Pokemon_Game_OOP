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
    /**
     * this method is the constructor of graphWindow
     */
    public graphWindow(DirectedWeightedGraphAlgorithmsClass graph) {
        super();
        this.graph=graph;
        this.panel = new Panel(this.graph.getGraph());
        this.menuBar = new MenuBar();
        this.setMenuBar(this.menuBar);
        this.add(this.panel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(ScreenSize.width, ScreenSize.height);
        this.setBackground(Color.GREEN);
        this.setVisible(true);
    }

    /**
     * this method checks which button from the menu is selected,
     * and activates its function on the graph according to the button
     * @param e
     * @throws HeadlessException
     */
    @Override
    public void actionPerformed(ActionEvent e) throws HeadlessException{

    }
}
