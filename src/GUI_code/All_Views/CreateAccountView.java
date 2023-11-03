package src.GUI_code.All_Views;

import src.GUI_code.ViewManager;

import javax.swing.*;

public class CreateAccountView {
    private JPanel createAccountMainPanel;
    private JLabel createAccountText;
    private JPanel createAccountPanel;
    private JTextField textField1;

    private ViewManager vm;

    public CreateAccountView(ViewManager v){
        this.vm = v;

    }

    public JPanel getCreateAccountMainPanel() {
        return createAccountMainPanel;
    }
}
