package src.GUI_code;

import src.GUI_code.All_Views.CreateAccountView;
import src.GUI_code.All_Views.EntryView;
import src.GUI_code.All_Views.SellerHomePageView;
import src.users_code.Seller;
import src.users_code.User;

import javax.swing.*;
import java.awt.*;

public class ViewManager {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private EntryView entryView;
    private CreateAccountView createAccount;
    private SellerHomePageView sellerView;
    private static ViewManager instance;

    private ViewManager(JPanel mainPanel) {
        this.mainPanel = mainPanel;
        cardLayout = new CardLayout();
        mainPanel.setLayout(cardLayout);

        entryView = new EntryView(this);
        mainPanel.add(entryView.getEntryViewMainPanel(), "entryView");

        createAccount = new CreateAccountView(this);
        mainPanel.add(createAccount.getCreateAccountMainPanel(), "createAccountView");
        sellerView = new SellerHomePageView(this);
        mainPanel.add(sellerView.getMainPanel(), "sellerHomePageView");
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
    public void showSellerHomePageView(Seller u){
        cardLayout.show(mainPanel,"sellerHomePageView");
        sellerView.displaySellerProducts(u.getUsername());
    }
}
