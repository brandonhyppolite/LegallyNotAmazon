package src.Frontend;

import src.Frontend.All_Views.*;
import src.users_code.Buyer;
import src.users_code.Seller;

/**
 * The `ViewManager` class manages the display of different views in the user interface.
 */
public class ViewManager {
    private static ViewManager instance;
    private static ShoppingCartApp sp;
    /**
     * Constructs a `ViewManager` object with the given `ShoppingCartApp` instance.
     *
     * @param sp The `ShoppingCartApp` instance.
     */
    private ViewManager(ShoppingCartApp sp) {
        this.sp = sp;
    }
    /**
     * Returns the singleton instance of the `ViewManager` class.
     *
     * @param app The `ShoppingCartApp` instance.
     * @return The `ViewManager` instance.
     */
    public static ViewManager getInstance(ShoppingCartApp app) {
        if (instance == null) {
            instance = new ViewManager(app);
        }
        return instance;
    }
    /**
     * Shows the entry view in the user interface.
     */
    public void showEntryView() {
        this.sp.getContentPane().removeAll();
        this.sp.getContentPane().add(new EntryView(this).getEntryViewMainPanel());
        this.sp.revalidate();
        this.sp.repaint();
    }
    /**
     * Shows the create account view in the user interface.
     */
    public void showCreateAccountView() {
        this.sp.getContentPane().removeAll();
        this.sp.getContentPane().add(new CreateAccountView(this).getCreateAccountMainPanel());
        this.sp.revalidate();
        this.sp.repaint();
    }
    /**
     * Shows the seller's home page view in the user interface.
     *
     * @param seller The seller object.
     */
    public void showSellerHomePageView(Seller seller){
        this.sp.getContentPane().removeAll();
        this.sp.getContentPane().add(new SellerHomePageView(this,seller).getMainPanel());
        this.sp.revalidate();
        this.sp.repaint();
    }
    /**
     * Shows the buyer's home page view in the user interface.
     *
     * @param buyer The buyer object.
     */
    public void showBuyerHomePage(Buyer buyer) {
        this.sp.getContentPane().removeAll();
        this.sp.getContentPane().add(new BuyerPageView(this,buyer).getBuyerPageMainPanel());
        this.sp.revalidate();
        this.sp.repaint();
    }
    /**
     * Shows the buyer's checkout view in the user interface.
     *
     * @param buyer The buyer object.
     */
    public void showBuyerCheckoutView(Buyer buyer) {
        this.sp.getContentPane().removeAll();
        this.sp.getContentPane().add(new BuyerCheckoutView(this,buyer).getBuyerCheckOutMainPanel());
        this.sp.revalidate();
        this.sp.repaint();
    }
}
