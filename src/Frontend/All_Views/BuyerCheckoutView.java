package src.Frontend.All_Views;

import src.Backend.UserManager;
import src.Frontend.ViewManager;
import src.users_code.Buyer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BuyerCheckoutView implements ActionListener {
    private JPanel buyerCheckOutMainPanel;
    private JPanel mainInfoPanel;
    private JLabel totalLabel;

    private final ViewManager vm;
    private final Buyer buyer;
    private final UserManager userManager;

    public BuyerCheckoutView(ViewManager vm, Buyer buyer){
        this.vm = vm;
        this.buyer = buyer;
        this.userManager = UserManager.getInstance();

        setUpMainView();
    }


    private void showCheckout(){
        SwingUtilities.invokeLater(() -> {
            clearPanels();
            mainInfoPanel.setLayout(new BorderLayout());
            mainInfoPanel.add(new JLabel("Enter your card information below or press Autofill"),BorderLayout.NORTH);
            mainInfoPanel.add(drawEnterCardInfoPanel(), BorderLayout.CENTER);
            mainInfoPanel.add(createGoBackButton(),BorderLayout.SOUTH);
            mainInfoPanel.revalidate();
            mainInfoPanel.repaint();
        });
    }

    public JPanel getBuyerCheckOutMainPanel(){
        return this.buyerCheckOutMainPanel;
    }

    private void clearPanels(){
        mainInfoPanel.removeAll();
    }

    private void setUpMainView(){
        totalLabel.setText("Your total is: $" + String.format("%.2f",this.buyer.getTotalOnCart()));
        showCheckout();
    }


    private JPanel drawEnterCardInfoPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        int width = 150, height = 25;
        JLabel creditCardAccount = new JLabel("Credit Card Account Number:");
        JLabel creditCardCVV = new JLabel("Credit Card CVV:");
        JLabel creditCardExpiration = new JLabel("Credit Card Expiration Date:");


        JTextField creditCardAccountField = new JTextField();
        creditCardAccountField.setPreferredSize(new Dimension(width + 50,height));

        JTextField creditCardCVVField = new JTextField();
        creditCardCVVField.setPreferredSize(new Dimension(width + 50,height));

        JTextField creditCardExpirationField = new JTextField();
        creditCardExpirationField.setPreferredSize(new Dimension(width + 50,height));

        JButton autoFill = createAutoFillButton(creditCardAccountField, creditCardCVVField, creditCardExpirationField);

        JButton pay = new JButton("Pay");
        pay.setActionCommand("pay");
        pay.addActionListener(this);


        panel.add(creditCardAccount);
        panel.add(creditCardAccountField);
        panel.add(creditCardCVV);
        panel.add(creditCardCVVField);
        panel.add(creditCardExpiration);
        panel.add(creditCardExpirationField);
        panel.add(autoFill);
        panel.add(pay);

        return panel;
    }

    private JButton createAutoFillButton(JTextField creditCardAccountField, JTextField creditCardCVVField, JTextField creditCardExpirationField) {
        JButton autoFill = new JButton("Autofill");

        autoFill.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                creditCardAccountField.setText(buyer.getCard().getCreditCardNumber());
                creditCardCVVField.setText(buyer.getCard().getCvv());
                creditCardExpirationField.setText(buyer.getCard().getExpirationDate());
            }
        });
        return autoFill;
    }

    private JButton createGoBackButton(){
        JButton back = new JButton("Go Back");
        back.setActionCommand("go back");
        back.addActionListener(this);
        return back;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch(command){
            case "go back":
                this.vm.showBuyerHomePage(this.buyer);
                break;
            case "pay":
                break;
        }
    }
}
