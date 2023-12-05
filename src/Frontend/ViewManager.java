package src.Frontend;

import src.Frontend.All_Views.*;
import src.users_code.Buyer;
import src.users_code.Seller;

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
    public void showSellerHomePageView(Seller seller){
        this.sp.getContentPane().removeAll();
        this.sp.getContentPane().add(new SellerHomePageView(this,seller).getMainPanel());
        this.sp.revalidate();
        this.sp.repaint();
    }

    public void showBuyerHomePage(Buyer buyer) {
        this.sp.getContentPane().removeAll();
        this.sp.getContentPane().add(new BuyerPageView(this,buyer).getBuyerPageMainPanel());
        this.sp.revalidate();
        this.sp.repaint();
    }

    public void showBuyerCheckoutView(Buyer buyer) {
        this.sp.getContentPane().removeAll();
        this.sp.getContentPane().add(new BuyerCheckoutView(this,buyer).getBuyerCheckOutMainPanel());
        this.sp.revalidate();
        this.sp.repaint();
    }
}
