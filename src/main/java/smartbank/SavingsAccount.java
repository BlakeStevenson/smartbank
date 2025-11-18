package smartbank;

public class SavingsAccount extends BankAccount {

    private double minimumBalance;

    public SavingsAccount(String holderName, double openingBalance, double minimumBalance) {
        super(holderName, openingBalance);
        this.minimumBalance = minimumBalance;
    }

    @Override
    public boolean withdraw(double amount) throws InsufficientBalanceException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal must be positive");
        }
        double newBalance = getBalance() - amount;
        if (newBalance < minimumBalance) {
            throw new InsufficientBalanceException(
                    "Cannot go below minimum balance of " + minimumBalance
            );
        }
        // simple way to update balance: call parent deposit/withdraw logic or adjust directly
        // Using direct adjustment (balance is protected in parent):
        super.balance = newBalance;
        return true;
    }
}
