package smartbank;

public class BankAccount {

    public int accountNumber;          // as required
    protected double balance;          // as required
    private String holderName;         // as required

    private static int nextAccountNumber = 1000; // static auto-increment

    public BankAccount(String holderName, double openingBalance) {
        this.accountNumber = generateAccountNumber();
        this.holderName = holderName;
        this.balance = openingBalance;
    }

    public static int generateAccountNumber() {
        return nextAccountNumber++;
    }

    public String getHolderName() {
        return holderName;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit must be positive");
        }
        balance += amount;
    }

    // Will be overridden in subclasses
    public boolean withdraw(double amount) throws InsufficientBalanceException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal must be positive");
        }
        if (amount > balance) {
            throw new InsufficientBalanceException("Not enough balance");
        }
        balance -= amount;
        return true;
    }

    public String displayAccountDetails() {
        return "Account #" + accountNumber +
                " | Holder: " + holderName +
                " | Balance: $" + String.format("%.2f", balance);
    }
}

