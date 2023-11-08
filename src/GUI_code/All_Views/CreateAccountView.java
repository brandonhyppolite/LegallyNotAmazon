package src.GUI_code.All_Views;

import src.GUI_code.ViewManager;

import javax.swing.*;

public class CreateAccountView {
    private JPanel createAccountMainPanel;
    private JLabel createViewText;
    private JPanel createAccountInfoPanel;
    private JTextField firstName;
    private JTextField lastName;
    private JTextField username;
    private JPasswordField password;
    private JTextField email;
    private JLabel firstNameLabel;
    private JLabel lastNameLabel;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JLabel emailLabel;
    private JLabel accountTypeLabel;
    private JRadioButton buyerRadio;
    private JRadioButton sellerRadio;
    private JPanel createAccountButtonPanel;
    private JButton createAccountButton;
    private JButton backToLoginButton;

    private ViewManager vm;

    public CreateAccountView(ViewManager v){
        this.vm = v;

    }

    public JPanel getCreateAccountMainPanel() {
        return createAccountMainPanel;
    }
}
