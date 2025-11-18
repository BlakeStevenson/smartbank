package smartbank;

public class SavingsAccount extends BankAccount {

    private final double minimumBalance;

    public SavingsAccount(String holderName, double openingBalance, double minimumBalance) {
        super(holderName, openingBalance);
        this.minimumBalance = minimumBalance;
    }

    @Override
    public void withdraw(double amount) throws InsufficientBalanceException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal must be positive");
        }
        double newBalance = getBalance() - amount;
        if (newBalance < minimumBalance) {
            throw new InsufficientBalanceException(
                    "Cannot go below minimum balance of " + minimumBalance
            );
        }
        super.balance = newBalance;
    }
}
