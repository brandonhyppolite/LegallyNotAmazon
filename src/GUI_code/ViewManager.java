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

    public ViewManager(JPanel mainPanel) {
        this.mainPanel = mainPanel;
        cardLayout = new CardLayout();
        mainPanel.setLayout(cardLayout);

        entryView = new EntryView(this);
        mainPanel.add(entryView.getEntryViewMainPanel(), "entryView");

        createAccount = new CreateAccountView(this);
        mainPanel.add(createAccount.getCreateAccountMainPanel(), "createAccountView");
    }

    public void showEntryView() {
        cardLayout.show(mainPanel, "entryView");
    }

    public void showCreateAccountView() {
        cardLayout.show(mainPanel, "createAccountView");
    }


}
