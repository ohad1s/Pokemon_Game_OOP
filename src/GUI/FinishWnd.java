package GUI;


import graph.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class FinishWnd extends JFrame implements ActionListener {

    FinishPanel panel;
    final Dimension ScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Game myGame;
    int ttl;

    /**
     * this method is the constructor of graphWindow
     */
    public FinishWnd(Game myGame) {
        super();
        this.myGame = myGame;
        this.panel = new FinishPanel(this.myGame);
        this.add(this.panel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(ScreenSize.width, ScreenSize.height);
        this.setResizable(true);
//        this.setBackground(Color.GREEN);
        this.panel.quit.addActionListener(this);
        this.setVisible(true);
    }

    /**
     * this method checks which button from the menu is selected,
     * and activates its function on the graph according to the button
     *
     * @param e
     * @throws HeadlessException
     */
    @Override
    public void actionPerformed(ActionEvent e) throws HeadlessException {
        if (e.getSource() == this.panel.quit) {
            System.exit(0);
        }
    }
}
