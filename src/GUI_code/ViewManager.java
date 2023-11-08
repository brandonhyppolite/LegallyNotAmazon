package src.GUI_code;

import src.GUI_code.All_Views.CreateAccountView;
import src.GUI_code.All_Views.EntryView;

import javax.swing.*;
import java.awt.*;

public class ViewManager {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private EntryView entryView;
    private CreateAccountView createAccount;
    private static ViewManager instance;

    private ViewManager(JPanel mainPanel) {
        this.mainPanel = mainPanel;
        cardLayout = new CardLayout();
        mainPanel.setLayout(cardLayout);

        entryView = new EntryView(this);
        mainPanel.add(entryView.getEntryViewMainPanel(), "entryView");

        createAccount = new CreateAccountView(this);
        mainPanel.add(createAccount.getCreateAccountMainPanel(), "createAccountView");
    }

    public static ViewManager getInstance(JPanel mainPanel) {
        if (instance == null) {
            instance = new ViewManager(mainPanel);
        }
        return instance;
    }

    public void showEntryView() {
        entryView.resetFields();
        cardLayout.show(mainPanel, "entryView");
    }

    public void showCreateAccountView() {
        createAccount.resetFields();
        cardLayout.show(mainPanel, "createAccountView");
    }
}
