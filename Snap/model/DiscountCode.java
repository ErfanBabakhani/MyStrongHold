package model;
public class DiscountCode {
    private String name;
    private int amount;
    private String customerUsername;

    public DiscountCode(String name, int amount, String customerUsername) {
        setAmount(amount);
        setName(name);
        setCustomerUsername(customerUsername);
    }

    private void setAmount(int amount) {
        this.amount = amount;
    }

    private void setName(String name) {
        this.name = name;
    }

    public void setCustomerUsername(String customerUsername) {
        this.customerUsername = customerUsername;
    }

    public int getAmount() {
        return amount;
    }

    public String getName() {
        return name;
    }

    public String getCustomerUsername() {
        return customerUsername;
    }
}
