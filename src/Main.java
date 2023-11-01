package src;

import src.users_code.Buyer;
import src.users_code.CreditCard;

import java.time.LocalDate;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        CreditCard card = new CreditCard("424242424242242", LocalDate.of(2026,10,23), "345");
        Buyer b= new Buyer("topgun97","atheek99@yahoo.com","123424v",card);
        System.out.println(b.getCard());

    }
}