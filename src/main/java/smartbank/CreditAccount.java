package smartbank;

public class CreditAccount extends BankAccount {

    private double creditLimit; // how far below zero balance can go

    public CreditAccount(String holderName, double openingBalance, double creditLimit) {
        super(holderName, openingBalance);
        this.creditLimit = creditLimit;
    }

    @Override
    public boolean withdraw(double amount) throws InsufficientBalanceException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal must be positive");
        }
        double newBalance = getBalance() - amount;
        if (newBalance < -creditLimit) {
            throw new InsufficientBalanceException(
                    "Over credit limit. Max allowed overdraft is " + creditLimit
            );
        }
        super.balance = newBalance;
        return true;
    }
}
