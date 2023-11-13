package src.users_code;

import src.Inventory.Product;

import java.util.ArrayList;

public class Seller extends User{
    private ArrayList<Product> sellerProducts;
    private double costs;
    private double revenues;
    private double profits;

    public Seller(String firstName, String lastName, String username, String password, String email) {
        super(firstName, lastName, username, password, email);
        this.sellerProducts = new ArrayList<>();
    }

    public Seller(String firstName, String lastName, String username, String password, String email, double costs, double revenues, double profits) {
        super(firstName, lastName, username, password, email);
        this.costs = costs;
        this.revenues = revenues;
        this.profits = profits;
    }

    public double getCosts() {
        return costs;
    }

    public void setCosts(double costs) {
        this.costs = costs;
    }

    public double getRevenues() {
        return revenues;
    }

    public void setRevenues(double revenues) {
        this.revenues = revenues;
    }

    public double getProfits() {
        return profits;
    }

    public void setProfits(double profits) {
        this.profits = profits;
    }

    public int getProductSize(){
        return this.sellerProducts.size();
    }

    public ArrayList<Product> getSellerProducts(){
        return sellerProducts;
    }
}
