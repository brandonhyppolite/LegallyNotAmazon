package src.Frontend.All_Views;

import src.Backend.UserManager;
import src.Frontend.ViewManager;
import src.users_code.Seller;
import src.users_code.Buyer;
import src.users_code.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    private UserManager system;

    private JButton loginButton;
    private JButton createAccountButton;


    public EntryView(ViewManager viewManager) {
        this.vm = viewManager;
        this.system = UserManager.getInstance();
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
                if(system.loginUser(username.getText(), password)){
                    User u = system.getUserByUsername(username.getText());
                    if(u instanceof Seller){
                        vm.showSellerHomePageView((Seller) u);
                    }else if(u instanceof Buyer){
                        //Show Buyer View
                        vm.showBuyerHomePage((Buyer) u);
                    }
                }else{
                    //Show an Error for logging in
                }

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
