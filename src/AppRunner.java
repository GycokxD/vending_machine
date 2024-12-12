import enums.ActionLetter;
import model.*;
import util.UniversalArray;
import util.UniversalArrayImpl;

import java.util.Scanner;

public class AppRunner {

    private final UniversalArray<Product> products = new UniversalArrayImpl<>();
    private final UniversalArray<PaymentMethod> paymentMethods = new UniversalArrayImpl<>();

    private static boolean isExit = false;

    private static Scanner sc = new Scanner(System.in);

    private AppRunner() {
        products.addAll(new Product[]{
                new Water(ActionLetter.B, 20),
                new CocaCola(ActionLetter.C, 50),
                new Soda(ActionLetter.D, 30),
                new Snickers(ActionLetter.E, 80),
                new Mars(ActionLetter.F, 80),
                new Pistachios(ActionLetter.G, 130)
        });

        paymentMethods.add(new CoinAcceptor(100));
        paymentMethods.add(new MoneyAcceptor(20));
        paymentMethods.add(new CardAcceptor(0));
    }

    public static void run() {
        AppRunner app = new AppRunner();
        while (!isExit) {
            app.startSimulation();
        }
    }

    private void startSimulation() {
        print("В автомате доступны:");
        showProducts(products);

        for (int i = 0; i < paymentMethods.size(); i++) {
            PaymentMethod method = paymentMethods.get(i);
            print(method.getName() + ": " + method.getBalance());
        }

        UniversalArray<Product> allowProducts = new UniversalArrayImpl<>();
        allowProducts.addAll(getAllowedProducts().toArray());
        chooseAction(allowProducts);
    }

    private UniversalArray<Product> getAllowedProducts() {
        UniversalArray<Product> allowProducts = new UniversalArrayImpl<>();
        for (int i = 0; i < products.size(); i++) {
            for (int j = 0; j < paymentMethods.size(); j++) {
                PaymentMethod method = paymentMethods.get(j);
                if (method.getBalance() >= products.get(i).getPrice()) {
                    allowProducts.add(products.get(i));
                    break;
                }
            }
        }
        return allowProducts;
    }

    private void chooseAction(UniversalArray<Product> products) {
        print(" a - Пополнить баланс");
        showActions(products);
        print(" h - Выйти");

        String action = fromConsole();

        if (action.isEmpty()) {
            print("Пустой ввод. Попробуйте еще раз.");
            chooseAction(products);
            return;
        }

        action = action.substring(0, 1);

        if ("a".equalsIgnoreCase(action)) {
            choosePayment();
            return;
        }
        try {
            for (int i = 0; i < products.size(); i++) {
                if (products.get(i).getActionLetter().equals(ActionLetter.valueOf(action.toUpperCase()))) {
                    for (int j = 0; j < paymentMethods.size(); j++) {
                        PaymentMethod method = paymentMethods.get(j);
                        if (method.getBalance() >= products.get(i).getPrice()) {
                            method.addAmount(-products.get(i).getPrice());
                            print("Вы купили " + products.get(i).getName());
                            break;
                        }
                    }
                    break;
                }
            }
        } catch (IllegalArgumentException e) {
            if ("h".equalsIgnoreCase(action)) {
                isExit = true;
            } else {
                print("Недопустимая буква. Попробуйте еще раз.");
                chooseAction(products);
            }
        }
    }


    private void showActions(UniversalArray<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            print(String.format(" %s - %s", products.get(i).getActionLetter().getValue(), products.get(i).getName()));
        }
    }

    private String fromConsole() {
        String input = sc.nextLine();
        return input.trim();
    }

    private void showProducts(UniversalArray<Product> products) {
        for (int i = 0; i < products.size(); i++) {
            print(products.get(i).toString());
        }
    }

    private void print(String msg) {
        System.out.println(msg);
    }

    private void choosePayment() {
        print("Выберите способ оплаты:");
        for (int i = 0; i < paymentMethods.size(); i++) {
            print((i + 1) + ". " + paymentMethods.get(i).getName());
        }

        int userChoose = sc.nextInt();
        if (userChoose >= 1 && userChoose <= paymentMethods.size()) {
            PaymentMethod chosenMethod = paymentMethods.get(userChoose - 1);
            print("Введите сумму для пополнения:");
            int amount = sc.nextInt();
            chosenMethod.addAmount(amount);
            print("Вы пополнили баланс на " + amount + " с помощью " + chosenMethod.getName());
        } else {
            print("Некорректный выбор.");
        }
    }
}
