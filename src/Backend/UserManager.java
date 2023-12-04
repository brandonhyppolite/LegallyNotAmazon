package src.Backend;

import src.users_code.Buyer;
import src.users_code.Seller;
import src.users_code.User;

import java.io.*;
import java.util.ArrayList;

/**
 * The `UserManager` class manages user-related operations such as user creation, login, and data persistence.
 */
public class UserManager {
    private final ArrayList<User> users;
    private static UserManager instance;
    private static final String USER_DATA_FILE = "src/Database/users.txt";
    private final ProductsManager productsManager;

    /**
     * Private constructor to enforce the singleton pattern. Reads user data from a file, initializes the
     * `ProductsManager`, and prints all existing users.
     */
    private UserManager() {
        this.users = new ArrayList<>();
        readUserDataFromFile();
        this.productsManager = new ProductsManager(this);
        printAllUsers();
    }

    /**
     * Gets the singleton instance of the `UserManager`.
     *
     * @return The singleton instance of the `UserManager`.
     */
    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    /**
     * Returns a string representation of the `UserManager`.
     *
     * @return A string representation of the `UserManager`.
     */
    @Override
    public String toString() {
        return "UserManager{" +
                "users=" + users +
                '}';
    }

    /**
     * Reads user data from the specified file and adds valid users to the user list.
     */
    public void readUserDataFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(";");
                User user = getUser(userData);
                if (user != null) {
                    this.users.add(user);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private User getUser(String[] userData){
        User user = null;
        String accountType = userData[userData.length-1];
        if(accountType.equals("Buyer")){
            String firstName = userData[0];
            String lastName = userData[1];
            String username = userData[2];
            String password = userData[3];
            String email = userData[4];
            String address = userData[5];
            String creditAccountNumber = userData[6];
            String creditCVV = userData[7];
            String creditExpiration = userData[8];
            user = new Buyer(firstName,lastName,username,password,email,address,creditAccountNumber,creditCVV,creditExpiration);

        }else if(accountType.equals("Seller")){
            String firstName = userData[0];
            String lastName = userData[1];
            String username = userData[2];
            String password = userData[3];
            String email = userData[4];
            double costs = Double.parseDouble(userData[5]);
            double revenues = Double.parseDouble(userData[6]);
            double profits = Double.parseDouble(userData[7]);
            String bankName = userData[8];
            String accountNumber = userData[9];
            String routingNumber = userData[10];
            user = new Seller(firstName,lastName,username,password,email,costs,revenues,profits,bankName,accountNumber,routingNumber);
        }
        return user;
    }

    /**
     * Creates a new user based on the provided data and adds it to the user list.
     *
     * @param firstName   The first name of the user.
     * @param lastName    The last name of the user.
     * @param username    The username of the user.
     * @param password    The password of the user.
     * @param email       The email of the user.
     * @param accountType The type of the user account (Buyer or Seller).
     */
    public boolean createNewUser(String firstName, String lastName, String username, String password,
                              String email, String accountType) {
        if(!isUserNameTaken(username)){
            switch (accountType) {
                case "Buyer":
                    this.users.add(new Buyer(firstName, lastName, username, password, email));
                    writeUserDataToFile();
                    break;
                case "Seller":
                    this.users.add(new Seller(firstName, lastName, username, password, email));
                    writeUserDataToFile();
                    break;
                default:
                    System.out.println("Unknown account type");
                    return false;

            }
            return true;
        }
        return false;

    }

    public boolean isUserNameTaken(String username){
        for(User u: this.users){
            if(u.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }

    /**
     * Attempts to log in a user with the provided username and password.
     *
     * @param username The username of the user attempting to log in.
     * @param password The password of the user attempting to log in.
     * @return True if the login is successful, false otherwise.
     */
    public boolean loginUser(String username, String password) {
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                if (u.getPassword().equals(password)) {
                    System.out.println("Logged in user " + u);
                    return true;
                } else {
                    System.out.println("Incorrect Password");
                }
            }
        }
        System.out.println("No such user!");
        return false;
    }

    /**
     * Retrieves a user object based on the provided username.
     *
     * @param username The username of the user to retrieve.
     * @return The User object corresponding to the given username, or null if not found.
     */
    public User getUserByUsername(String username) {
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                return u;
            }
        }
        return null;
    }

    /**
     * Prints information about all users in the user list.
     */
    public void printAllUsers() {
        for (User u : users) {
            System.out.println(u);
        }
    }

    /**
     * Gets the list of users managed by the `UserManager`.
     *
     * @return The list of users.
     */
    public ArrayList<User> getUsers() {
        return users;
    }

    /**
     * Gets the `ProductsManager` associated with this `UserManager`.
     *
     * @return The `ProductsManager` instance.
     */
    public ProductsManager getProductsManager() {
        return this.productsManager;
    }

    /**
     * Writes user data to the specified file.
     */
    public void writeUserDataToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_DATA_FILE, false))) {
            for (User user : users) {
                // Format the user data and write it to the file
                if (user instanceof Buyer) {
                    String userData = user.getFirstName() + ";" + user.getLastName() + ";"
                            + user.getUsername() + ";" + user.getPassword() + ";"
                            + user.getEmail() + ";"+ ((Buyer) user).getAddress() + ";" + ((Buyer) user).getCard().getCreditCardNumber() +
                            ";" + ((Buyer) user).getCard().getCvv() + ";" + ((Buyer) user).getCard().getExpirationDate() + ";"+ "Buyer";
                    writer.write(userData);
                    writer.newLine();
                } else if (user instanceof Seller) {
                    String userData = user.getFirstName() + ";" + user.getLastName() + ";"
                            + user.getUsername() + ";" + user.getPassword() + ";"
                            + user.getEmail() + ";" + ((Seller) user).getCosts() + ";" + ((Seller) user).getRevenues() + ";"
                    + ((Seller) user).getProfits()
                            + ";" + ((Seller) user).getBankName()+ ";"
                            + ((Seller) user).getAccountNumber()+ ";" + ((Seller) user).getRoutingNumber() +
                            ";" +"Seller";
                    writer.write(userData);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateBuyerAccountInformation(Buyer buyer, String context, String newValue) {
        switch (context) {
            case "username":
                buyer.setUsername(newValue);
                break;
            case "password":
                buyer.setPassword(newValue);
                break;
            case "email":
                buyer.setEmail(newValue);
                break;
            case "address":
                buyer.setAddress(newValue);
                break;
            case "creditCardAccount":
                buyer.getCard().setCreditCardNumber(newValue);
                break;
            case "creditCardCVV":
                buyer.getCard().setCvv(newValue);
                break;
            case "creditCardExpiration":
                buyer.getCard().setExpirationDate(newValue);
                break;
            default:
                System.out.println("Invalid context: " + context);
                break;
        }
    }

    public String getBuyerAccountInformation(Buyer buyer, String context) {
        switch (context) {
            case "username":
                return buyer.getUsername();
            case "password":
                return buyer.getPassword();
            case "email":
                return buyer.getEmail();
            case "address":
                return buyer.getAddress();
            case "creditCardAccount":
                return buyer.getCard().getCreditCardNumber();
            case "creditCardCVV":
                return buyer.getCard().getCvv();
            case "creditCardExpiration":
                return buyer.getCard().getExpirationDate();
            default:
                System.out.println("Invalid context: " + context);
                return null;
        }
    }

    public void updateSellerAccountInformation(Seller seller, String context, String newValue){
        switch (context) {
            case "username":
                seller.setUsername(newValue);
                break;
            case "password":
                seller.setPassword(newValue);
                break;
            case "email":
                seller.setEmail(newValue);
                break;
            case "bank name":
                seller.setBankName(newValue);
                break;
            case "account number":
                seller.setAccountNumber(newValue);
                break;
            case "routing number":
                seller.setRoutingNumber(newValue);
                break;
            default:
                System.out.println("Invalid context: " + context);
                break;
        }
    }

    public String getSellerAccountInformation(Seller seller, String context) {
        switch (context) {
            case "username":
                return seller.getUsername();
            case "password":
                return seller.getPassword();
            case "email":
                return seller.getEmail();
            case "bank name":
                return seller.getBankName();
            case "account number":
                return seller.getAccountNumber();
            case "routing number":
                return seller.getRoutingNumber();
            default:
                System.out.println("Invalid context: " + context);
                return null;
        }
    }


}
