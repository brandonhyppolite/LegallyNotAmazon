package src.users_code;

import src.Inventory.Product;

import java.util.ArrayList;

public class Seller extends User{
    private double costs;
    private double revenues;
    private double profits;


    //Constructor for brand new seller
    public Seller(String firstName, String lastName, String username, String password, String email) {
        super(firstName, lastName, username, password, email);
        this.costs = 0;
        this.revenues = 0;
        this.profits = 0;
    }

    //Constructor for existing seller that gets read from a text file with values
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
}
