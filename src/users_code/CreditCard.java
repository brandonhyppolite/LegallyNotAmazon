package src.users_code;
/**
 * The `CreditCard` class represents a credit card.
 */
public class CreditCard {
    private String creditCardNumber;
    private String expirationDate;
    private String cvv;
    /**
     * Default constructor for creating a new `CreditCard` object.
     */
    public CreditCard() {

    }
    /**
     * Gets the credit card number.
     *
     * @return The credit card number.
     */
    public String getCreditCardNumber() {
        return creditCardNumber;
    }
    /**
     * Sets the credit card number.
     *
     * @param creditCardNumber The credit card number to set.
     */
    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }
    /**
     * Gets the expiration date of the credit card.
     *
     * @return The expiration date of the credit card.
     */
    public String getExpirationDate() {
        return expirationDate;
    }
    /**
     * Sets the expiration date of the credit card.
     *
     * @param expirationDate The expiration date to set.
     */
    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }
    /**
     * Gets the CVV (Card Verification Value) of the credit card.
     *
     * @return The CVV of the credit card.
     */
    public String getCvv() {
        return cvv;
    }
    /**
     * Sets the CVV (Card Verification Value) of the credit card.
     *
     * @param cvv The CVV to set.
     */
    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    @Override
    public String toString() {
        return "CreditCard{" +
                "creditCardNumber='" + creditCardNumber + '\'' +
                ", expirationDate=" + expirationDate +
                ", cvv='" + cvv + '\'' +
                '}';
    }
}
