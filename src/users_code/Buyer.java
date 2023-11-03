package src.users_code;

public class Buyer extends User{
    private Cart cart;
    private CreditCard card;

    public Buyer(String username, String email, String password, CreditCard card) {
        super(username, email, password);
        this.card = card;
        this.cart = new Cart();
    }

    public CreditCard getCard(){
        return this.card;
    }
}
