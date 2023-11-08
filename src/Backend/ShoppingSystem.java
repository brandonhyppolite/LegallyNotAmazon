package src.Backend;

import src.users_code.Buyer;
import src.users_code.Seller;
import src.users_code.User;

import java.util.ArrayList;
import java.util.HashMap;

public class ShoppingSystem {
    private ArrayList<User> users;
    private static ShoppingSystem instance;


    private ShoppingSystem() {
        this.users = new ArrayList<>();
    }

    public static ShoppingSystem getInstance() {
        if (instance == null) {
            instance = new ShoppingSystem();
        }
        return instance;
    }

    @Override
    public String toString() {
        return "ShoppingSystem{" +
                "users=" + users +
                '}';
    }

    public void createNewUser(String firstName,String lastName,String username,String password, String email,String accountType){
        switch(accountType){
            case "Buyer":
                this.users.add(new Buyer(firstName,lastName,username,password,email));
                break;
            case "Seller":
                this.users.add(new Seller(firstName,lastName,username,password,email));
                break;
            default:
                System.out.println("Unknown account type");
                break;
        }
    }

    public void loginUser(String username,String password){
        for(User u: users){
            if(u.getUsername().equals(username)){
                if(u.getPassword().equals(password)){
                    System.out.println("Logged in user " + u);
                }else{
                    System.out.println("Incorrect Password");
                }
                return;
            }else{
                System.out.println("Incorrect Username");
                return;
            }
        }
        System.out.println("No such user!");
    }
}
