package smartbank;

import java.time.LocalDateTime;

public class Transaction {

    private final int accountNumber;
    private final String type;  // "DEPOSIT" or "WITHDRAW"
    private final double amount;
    private final LocalDateTime dateTime;

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
