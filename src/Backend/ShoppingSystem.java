package src.Backend;

import src.Inventory.Product;
import src.Inventory.StoreInventory;
import src.users_code.Buyer;
import src.users_code.Seller;
import src.users_code.User;

import java.io.*;
import java.util.ArrayList;

public class ShoppingSystem {
    private ArrayList<User> users;
    private static ShoppingSystem instance;
    private StoreInventory storeInventory;
    private static final String USER_DATA_FILE = "src/users.txt";

    private ShoppingSystem() {
        this.users = new ArrayList<>();
        this.storeInventory = StoreInventory.getInstance();
        readUserDataFromFile();
        printAllUsers();
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

    private void readUserDataFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(";");
                if (userData.length == 6) { // Ensure we have exactly 6 data elements
                    User user = getUser(userData);
                    if (user != null) {
                        this.users.add(user);
                    }
                } else {
                    System.out.println("Skipping invalid data: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private User getUser(String[] userData){
        User user = null;
        String firstName = userData[0];
        String lastName = userData[1];
        String username = userData[2];
        String password = userData[3];
        String email = userData[4];
        String accountType = userData[5];
        if (accountType.equals("Buyer")) {
            user = new Buyer(firstName, lastName, username, password,email);
        } else if (accountType.equals("Seller")) {
            user = new Seller(firstName, lastName, username, password,email);
        }
        return user;
    }

    private void writeUserDataToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_DATA_FILE))) {
            for (User user : users) {
                // Format the user data and write it to the file
                if(user instanceof Buyer){
                    String userData = user.getFirstName() + ";" + user.getLastName() + ";"
                            + user.getUsername() + ";" + user.getPassword() + ";"
                            + user.getEmail() + ";" + "Buyer";
                    writer.write(userData);
                    writer.newLine();
                } else if (user instanceof Seller) {
                    String userData = user.getFirstName() + ";" + user.getLastName() + ";"
                            + user.getUsername() + ";" + user.getPassword() + ";"
                            + user.getEmail() + ";" + "Seller";
                    writer.write(userData);
                    writer.newLine();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void createNewUser(String firstName,String lastName,String username,String password, String email,String accountType){
        switch(accountType){
            case "Buyer":
                this.users.add(new Buyer(firstName,lastName,username,password,email));
                writeUserDataToFile();
                break;
            case "Seller":
                this.users.add(new Seller(firstName,lastName,username,password,email));
                writeUserDataToFile();
                break;
            default:
                System.out.println("Unknown account type");
                break;
        }
    }

    public boolean loginUser(String username,String password){
        for(User u: users){
            if(u.getUsername().equals(username)){
                if(u.getPassword().equals(password)){
                    System.out.println("Logged in user " + u);
                    return true;
                }else{
                    System.out.println("Incorrect Password");
                }
            }
        }
        System.out.println("No such user!");
        return false;
    }

    public User getUserByUsername(String username){
        for(User u: users){
            if(u.getUsername().equals(username)){
                return u;
            }
        }
        return null;
    }

    public void printAllUsers(){
        for(User u: users){
            System.out.println(u);
        }
    }

}
