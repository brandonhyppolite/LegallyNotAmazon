package src.users_code;

import src.Product.Product;

import java.util.ArrayList;

/**
 * The `Seller` class represents a seller user.
 */
public class Seller extends User{
    private double costsOfProductsForSale;
    private double totalAcquiredCosts;
    private double revenues;
    private double profits;
    private String BankName;
    private String RoutingNumber;
    private String AccountNumber;
    private ArrayList<Product> productsForSale;

    /**
     * Constructor for creating a new `Seller` object.
     *
     * @param firstName The first name of the seller.
     * @param lastName  The last name of the seller.
     * @param username  The username of the seller.
     * @param password  The password of the seller.
     * @param email     The email of the seller.
     */
    public Seller(String firstName, String lastName, String username, String password, String email) {
        super(firstName, lastName, username, password, email);
        this.productsForSale = new ArrayList<>();
        this.costsOfProductsForSale = 0.00;
        this.revenues = 0.00;
        this.profits = 0.00;
        this.totalAcquiredCosts = 0.00;
    }

    /**
     * Constructor for creating a new `Seller` object with additional information.
     *
     * @param firstName      The first name of the seller.
     * @param lastName       The last name of the seller.
     * @param username       The username of the seller.
     * @param password       The password of the seller.
     * @param email          The email of the seller.
     * @param costsOfProductsForSale          The costs incurred by the seller.
     * @param revenues       The revenues earned by the seller.
     * @param profits        The profits made by the seller.
     * @param bankName       The name of the seller's bank.
     * @param routingNumber  The routing number of the seller's bank account.
     * @param accountNumber  The account number of the seller's bank account.
     */
    public Seller(String firstName, String lastName, String username, String password, String email, double costsOfProductsForSale, double revenues, double profits,double totalAcquiredCosts, String bankName, String routingNumber, String accountNumber) {
        super(firstName, lastName, username, password, email);
        this.productsForSale = new ArrayList<>();
        this.costsOfProductsForSale = costsOfProductsForSale;
        this.revenues = revenues;
        this.profits = profits;
        this.totalAcquiredCosts = totalAcquiredCosts;
        BankName = bankName;
        RoutingNumber = routingNumber;
        AccountNumber = accountNumber;
    }
    /**
     * Gets the list of products for sale by the seller.
     *
     * @return The list of products for sale.
     */
    public ArrayList<Product> getProductsForSale() {
        return productsForSale;
    }
    /**
     * Gets the name of the seller's bank.
     *
     * @return The name of the bank.
     */
    public String getBankName() {
        return BankName;
    }
    /**
     * Sets the name of the seller's bank.
     *
     * @param bankName The name of the bank to set.
     */
    public void setBankName(String bankName) {
        BankName = bankName;
    }
    /**
     * Gets the routing number of the seller's bank account.
     *
     * @return The routing number of the bank account.
     */
    public String getRoutingNumber() {
        return RoutingNumber;
    }
    /**
     * Sets the routing number of the seller's bank account.
     *
     * @param routingNumber The routing number to set.
     */
    public void setRoutingNumber(String routingNumber) {
        RoutingNumber = routingNumber;
    }
    /**
     * Gets the account number of the seller's bank account.
     *
     * @return The account number of the bank account.
     */
    public String getAccountNumber() {
        return AccountNumber;
    }
    /**
     * Sets the account number of the seller's bank account.
     *
     * @param accountNumber The account number to set.
     */
    public void setAccountNumber(String accountNumber) {
        AccountNumber = accountNumber;
    }
    /**
     * Gets the costs incurred by the seller.
     *
     * @return The costs incurred.
     */
    public double getCostsOfProductsForSale() {
        return costsOfProductsForSale;
    }
    /**
     * Sets the costs incurred by the seller.
     *
     * @param costsOfProductsForSale The costs to set.
     */
    public void setCostsOfProductsForSale(double costsOfProductsForSale) {
        this.costsOfProductsForSale = costsOfProductsForSale;
    }
    /**
     * Gets the revenues earned by the seller.
     *
     * @return The revenues earned.
     */
    public double getRevenues() {
        return revenues;
    }
    /**
     * Sets the revenues earned by the seller.
     *
     * @param revenues The revenues to set.
     */
    public void setRevenues(double revenues) {
        this.revenues = revenues;
    }
    /**
     * Gets the profits made by the seller.
     *
     * @return The profits made.
     */
    public double getProfits() {
        return profits;
    }
    /**
     * Sets the profits made by the seller.
     *
     * @param profits The profits to set.
     */
    public void setProfits(double profits) {
        this.profits = profits;
    }


    public double getTotalAcquiredCosts() {
        return totalAcquiredCosts;
    }

    public void setTotalAcquiredCosts(double totalAcquiredCosts) {
        this.totalAcquiredCosts = totalAcquiredCosts;
    }

    /**
     * Adds a product to the list of products for sale by the seller.
     *
     * @param p The product to add.
     */
    public void addProductForSale(Product p){
        this.productsForSale.add(p);
        setTotalAcquiredCosts(getTotalAcquiredCosts() + (p.getInvoicePrice()* p.getQuantity()));
    }
    public void removeProductFromSale(Product p){
        this.productsForSale.remove(p);
        setTotalAcquiredCosts(getTotalAcquiredCosts() - (p.getInvoicePrice()* p.getQuantity()));
    }
    /**
     * Sets the sales data for the seller, including costs and profits.
     */
    public void setSalesData(){
        double costs =0.0;
        for(Product p : this.productsForSale){
            costs+= (p.getInvoicePrice() * p.getQuantity());
        }
        setCostsOfProductsForSale(costs);
        setProfits(getRevenues() - getTotalAcquiredCosts());


    }
}
