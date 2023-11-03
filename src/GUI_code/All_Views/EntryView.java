package src.GUI_code.All_Views;

import src.GUI_code.ViewManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EntryView {
    private JPanel entryViewMainPanel;
    private JLabel welcomeLabel;
    private JLabel usernameLabel;
    private JTextField usernameField;
    private ViewManager vm;


    private JPasswordField passwordPasswordField;
    private JLabel passwordLabel;
    private JButton loginButton;
    private JButton createAccountButton;
    private JPanel loginPanel;
    private JButton forgotPasswordButton;

    public EntryView(ViewManager viewManager) {
        this.vm = viewManager;
        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vm.showCreateAccountView();
            }
        });
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        forgotPasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public JPanel getEntryViewMainPanel() {
        return entryViewMainPanel;
    }
}
