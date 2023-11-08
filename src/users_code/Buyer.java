package src.users_code;

public class Buyer extends User{
    private Cart cart;
    private CreditCard card;

    public Buyer(String firstName, String lastName, String username, String password, String email) {
        super(firstName, lastName, username, password,email);
        this.cart = new Cart();
    }


    public CreditCard getCard(){
        return this.card;
    }
}
