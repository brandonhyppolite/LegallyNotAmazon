package src.GUI_code;

import src.GUI_code.All_Views.CreateAccountView;
import src.GUI_code.All_Views.EntryView;
import src.GUI_code.All_Views.SellerHomePageView;
import src.users_code.Seller;
import src.users_code.User;

import javax.swing.*;
import java.awt.*;

public class ViewManager {
    private static ViewManager instance;
    private static ShoppingCartApp sp;
    private ViewManager(ShoppingCartApp sp) {
        this.sp = sp;
    }

    public static ViewManager getInstance(ShoppingCartApp app) {
        if (instance == null) {
            instance = new ViewManager(app);
        }
        return instance;
    }

    public void showEntryView() {
        this.sp.getContentPane().removeAll();
        this.sp.getContentPane().add(new EntryView(this).getEntryViewMainPanel());
        this.sp.revalidate();
        this.sp.repaint();
    }

    public void showCreateAccountView() {
        this.sp.getContentPane().removeAll();
        this.sp.getContentPane().add(new CreateAccountView(this).getCreateAccountMainPanel());
        this.sp.revalidate();
        this.sp.repaint();
    }
    public void showSellerHomePageView(Seller u){
        this.sp.getContentPane().removeAll();
        this.sp.getContentPane().add(new SellerHomePageView(this,u).getMainPanel());
        this.sp.revalidate();
        this.sp.repaint();
    }
}
