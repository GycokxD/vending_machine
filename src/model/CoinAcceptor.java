package model;

public class CoinAcceptor implements PaymentMethod {
    private int balance;

    public CoinAcceptor(int initialBalance) {
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
        return "Монетами";
    }
}
