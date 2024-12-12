package model;

public class CardAcceptor implements PaymentMethod {
    private int balance;

    public CardAcceptor(int initialBalance) {
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
        return "Картой";
    }
}
