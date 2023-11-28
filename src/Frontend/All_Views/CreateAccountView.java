package src.Frontend.All_Views;

import src.Backend.UserManager;
import src.Frontend.ViewManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    private JPanel createAccountButtonPanel;
    private JButton createAccountButton;
    private JButton backToLoginButton;
    private JPanel accountTypeRadioPanel;
    private JRadioButton buyerRadio;
    private JRadioButton sellerRadio;
    private JLabel createAccountWarningLabel;
    private String accountType;
    private UserManager system;
    private ViewManager vm;

    public CreateAccountView(ViewManager v){
        this.vm = v;
        this.system = UserManager.getInstance();
        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String firstNameText = firstName.getText();
                String lastNameText = lastName.getText();
                String usernameText = username.getText();
                char[] passwordText = password.getPassword();
                String emailText = email.getText();

                if (firstNameText.equals("") || lastNameText.equals("") || usernameText.equals("") || passwordText.length ==0 || emailText.equals("")|| accountType.equals("")) {
                    createAccountWarningLabel.setText("Please fill out all the fields!");
                    createAccountWarningLabel.setForeground(Color.RED);

                } else {
                    String password = String.valueOf(passwordText);
                    boolean canCreateAccount = system.createNewUser(firstNameText, lastNameText, usernameText, password, emailText, accountType);
                    if(!canCreateAccount){
                        createAccountWarningLabel.setText("Username already taken!");
                        createAccountWarningLabel.setForeground(Color.RED);
                    }else{
                        createAccountWarningLabel.setText("Successfully created account! Go back and login!");
                        createAccountWarningLabel.setForeground(Color.GREEN);
                        System.out.println(system.getUserByUsername(usernameText).getPassword());
                    }

                }
            }
        });

        backToLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vm.showEntryView();
            }
        });
        buyerRadio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accountType = "Buyer";
                System.out.println(accountType);
            }
        });

        sellerRadio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accountType = "Seller";
                System.out.println(accountType);
            }
        });

    }

    public JPanel getCreateAccountMainPanel() {
        return createAccountMainPanel;
    }

    public void resetFields() {
        firstName.setText("");
        lastName.setText("");
        username.setText("");
        password.setText("");
        email.setText("");
        createAccountWarningLabel.setText(""); // Clear any warning messages
    }

}
