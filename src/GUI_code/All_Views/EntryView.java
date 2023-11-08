package src.GUI_code.All_Views;

import src.Backend.ShoppingSystem;
import src.GUI_code.ViewManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class EntryView {
    private JPanel entryViewMainPanel;
    private JLabel entryText;
    private JPanel entryInfoPanel;
    private JTextField username;
    private JPasswordField password;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JPanel entryButtonPanel;
    private ViewManager vm;
    private ShoppingSystem system;

    private JButton loginButton;
    private JButton createAccountButton;


    public EntryView(ViewManager viewManager) {
        this.vm = viewManager;
        this.system = ShoppingSystem.getInstance();
        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vm.showCreateAccountView();
            }
        });
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                char[] passwordArray = password.getPassword();
                String password = String.valueOf(passwordArray);
                system.loginUser(username.getText(), password);
            }
        });

    }

    public JPanel getEntryViewMainPanel() {
        return entryViewMainPanel;
    }

    public void resetFields(){
        username.setText("");
        password.setText("");
    }
}
