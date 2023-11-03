package src;

import src.GUI_code.EntryScreen;
import src.GUI_code.ShoppingCartApp;
import src.users_code.Buyer;
import src.users_code.CreditCard;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        EntryScreen e = new EntryScreen();
        ShoppingCartApp s = new ShoppingCartApp(e);
    }
}