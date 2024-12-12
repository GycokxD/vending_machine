package model;

public class MoneyAcceptor implements PaymentMethod {
    private int balance;

    public MoneyAcceptor(int initialBalance) {
        this.balance = initialBalance;
    }

    @Override
    public void addAmount(int amount) {
        balance += amount;
    }

    @Override
    public int getBalance() {
        return balance;
    }

    @Override
    public String getName() {
        return "Наличными";
    }
}
