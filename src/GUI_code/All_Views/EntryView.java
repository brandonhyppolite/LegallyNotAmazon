package src.GUI_code.All_Views;

import src.GUI_code.ViewManager;

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


    private JButton loginButton;
    private JButton createAccountButton;


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

    }

    public JPanel getEntryViewMainPanel() {
        return entryViewMainPanel;
    }
}
