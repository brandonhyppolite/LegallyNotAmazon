package src.Frontend.All_Views;

import src.Backend.UserManager;
import src.Frontend.ViewManager;
import src.users_code.Seller;
import src.users_code.Buyer;
import src.users_code.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The EntryView class is responsible for displaying the entry view in the user interface.
 */
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

    /**
     * Constructs an EntryView object with the specified ViewManager.
     *
     * @param viewManager The ViewManager object.
     */
    public EntryView(ViewManager viewManager) {
        this.vm = viewManager;
        this.system = UserManager.getInstance();
        // ActionListener for the create account button
        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vm.showCreateAccountView();
            }
        });
        // ActionListener for the login button
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
    /**
     * Returns the main panel of the entry view.
     *
     * @return The entry view main panel.
     */
    public JPanel getEntryViewMainPanel() {
        return entryViewMainPanel;
    }
    /**
     * Resets the input fields in the entry view.
     */
    public void resetFields(){
        username.setText("");
        password.setText("");
    }
}
