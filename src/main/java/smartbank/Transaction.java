package smartbank;

import java.time.LocalDateTime;

public class Transaction {

    private int accountNumber;
    private String type;  // "DEPOSIT" or "WITHDRAW"
    private double amount;
    private LocalDateTime dateTime;

    public Transaction(int accountNumber, String type, double amount) {
        this.accountNumber = accountNumber;
        this.type = type;
        this.amount = amount;
        this.dateTime = LocalDateTime.now();
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public String toString() {
        return "[" + dateTime + "] " +
                "Acc #" + accountNumber +
                " | " + type +
                " | $" + String.format("%.2f", amount);
    }
}
