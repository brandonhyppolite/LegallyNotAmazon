package src.GUI_code;

import javax.swing.*;

public class ShoppingCartApp extends JFrame {

    public ShoppingCartApp(EntryScreen e){
        add(e);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600,600);
        setTitle("Amazon");
        setVisible(true);
    }
}
