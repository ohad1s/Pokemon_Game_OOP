package GUI;



import graph.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class graphWindow extends JFrame implements ActionListener {
    DiGraphAlgo graph;
    Panel panel;
    final Dimension ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Game myGame;
    int ttl;
    /**
     * this method is the constructor of graphWindow
     */
    public graphWindow(DiGraphAlgo graph, Game myGame) {
        super();
        this.myGame =myGame;
        this.graph=graph;
        this.panel = new Panel(this.graph.getGraph(),this.myGame);
        this.add(this.panel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(ScreenSize.width, ScreenSize.height);
        this.setResizable(true);
//        this.setBackground(Color.GREEN);
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
