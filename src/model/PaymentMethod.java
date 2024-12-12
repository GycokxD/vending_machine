package model;

public interface PaymentMethod {
    void addAmount(int amount);
    int getBalance();
    String getName();
}
