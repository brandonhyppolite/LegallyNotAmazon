package src.GUI_code;

import src.Backend.ShoppingSystem;

import javax.swing.*;

public class ShoppingCartApp extends JFrame {
    private ShoppingSystem system;
    public ShoppingCartApp(){
        this.system = ShoppingSystem.getInstance();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(800, 600);

        JPanel mainPanel = new JPanel();
        this.add(mainPanel);

        ViewManager viewManager = ViewManager.getInstance(mainPanel);
//        viewManager.showSellerHomePageView();
        this.setVisible(true);

    }
}
