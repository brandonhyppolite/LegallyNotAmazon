package src.GUI_code;

import javax.swing.*;

public class ShoppingCartApp extends JFrame {

    public ShoppingCartApp(){
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(800, 600);

        JPanel mainPanel = new JPanel();
        this.add(mainPanel);

        ViewManager viewManager = new ViewManager(mainPanel);

        this.setVisible(true);

    }
}
