package src.GUI_code;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EntryScreen extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public EntryScreen() {
        // Set the layout for the panel
        setLayout(new BorderLayout());

        // Create a welcome label
        JLabel welcomeLabel = new JLabel("Welcome to Amazon");
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(welcomeLabel, BorderLayout.NORTH);

        // Create a panel for login information
        JPanel loginPanel = new JPanel(new GridLayout(3, 2, 5, 5));

        // Add labels and input fields
        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(20);

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(20);

        JButton loginButton = new JButton("Login");
        JButton createAccountButton = new JButton("Create New Account");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement login logic here
                String username = usernameField.getText();
                char[] password = passwordField.getPassword();

                // Perform authentication
                // Example: Check credentials against a database
            }
        });

        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement new account creation logic here
                // Show a registration form or switch to a registration screen
            }
        });

        // Add components to the login panel
        loginPanel.add(usernameLabel);
        loginPanel.add(usernameField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);
        loginPanel.add(loginButton);
        loginPanel.add(createAccountButton);

        // Add the login panel to the center of the EntryScreen
        add(loginPanel, BorderLayout.CENTER);
    }
}
