package smartbank;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Optional;

public class SmartBankApp extends Application {

    private final ArrayList<BankAccount> accounts = new ArrayList<>();
    private final ArrayList<Transaction> transactions = new ArrayList<>();

    private TextArea outputArea;
    private TextField txtHolderName;
    private TextField txtOpeningBalance;
    private TextField txtAmount;
    private TextField txtAccountNumber;
    private ChoiceBox<String> accountTypeChoice;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("SmartBank");

        // ---------- Top: Open Account ----------
        txtHolderName = new TextField();
        txtHolderName.setPromptText("Holder Name");

        txtOpeningBalance = new TextField();
        txtOpeningBalance.setPromptText("Opening Balance");

        accountTypeChoice = new ChoiceBox<>();
        accountTypeChoice.getItems().addAll("Savings", "Credit");
        accountTypeChoice.setValue("Savings");

        Button btnOpenAccount = new Button("Open Account");
        btnOpenAccount.setOnAction(e -> openAccount());

        HBox openBox = new HBox(10, txtHolderName, txtOpeningBalance, accountTypeChoice, btnOpenAccount);
        openBox.setPadding(new Insets(10));

        // ---------- Middle: Deposit / Withdraw ----------
        txtAccountNumber = new TextField();
        txtAccountNumber.setPromptText("Account #");

        txtAmount = new TextField();
        txtAmount.setPromptText("Amount");

        Button btnDeposit = new Button("Deposit");
        btnDeposit.setOnAction(e -> deposit());

        Button btnWithdraw = new Button("Withdraw");
        btnWithdraw.setOnAction(e -> withdraw());

        Button btnShowDetails = new Button("Show Account Details");
        btnShowDetails.setOnAction(e -> showAccountDetails());

        Button btnShowTransactions = new Button("Show All Transactions");
        btnShowTransactions.setOnAction(e -> showTransactions());

        HBox txnBox = new HBox(10, txtAccountNumber, txtAmount, btnDeposit, btnWithdraw, btnShowDetails, btnShowTransactions);
        txnBox.setPadding(new Insets(10));

        // ---------- Bottom: Output ----------
        outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setPrefRowCount(15);

        VBox root = new VBox(10, openBox, txnBox, outputArea);
        root.setPadding(new Insets(10));

        Scene scene = new Scene(root, 900, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // ---------- Helper methods ----------

    private void openAccount() {
        try {
            String name = txtHolderName.getText().trim();
            if (name.isEmpty()) {
                showError("Holder name is required.");
                return;
            }

            double openingBalance = Double.parseDouble(txtOpeningBalance.getText().trim());
            if (openingBalance < 0) {
                showError("Opening balance cannot be negative.");
                return;
            }

            String type = accountTypeChoice.getValue();
            BankAccount acc;

            if ("Savings".equals(type)) {
                // Example: min balance is 100
                acc = new SavingsAccount(name, openingBalance, 100.0);
            } else {
                // Credit account, example credit limit 1000
                acc = new CreditAccount(name, openingBalance, 1000.0);
            }

            accounts.add(acc);
            outputArea.appendText("Opened " + type + " account: " +
                    acc.displayAccountDetails() + "\n");

            txtHolderName.clear();
            txtOpeningBalance.clear();

        } catch (NumberFormatException ex) {
            showError("Opening balance must be a number.");
        } catch (Exception ex) {
            showError("Error opening account: " + ex.getMessage());
        }
    }

    private void deposit() {
        Optional<BankAccount> opt = findAccountFromInput();
        if (opt.isEmpty()) return;

        BankAccount acc = opt.get();

        try {
            double amount = Double.parseDouble(txtAmount.getText().trim());
            acc.deposit(amount);
            transactions.add(new Transaction(acc.accountNumber, "DEPOSIT", amount));

            outputArea.appendText("Deposit successful.\n");
            outputArea.appendText(acc.displayAccountDetails() + "\n");
        } catch (NumberFormatException ex) {
            showError("Amount must be a number.");
        } catch (IllegalArgumentException ex) {
            showError(ex.getMessage());
        }
    }

    private void withdraw() {
        Optional<BankAccount> opt = findAccountFromInput();
        if (opt.isEmpty()) return;

        BankAccount acc = opt.get();

        try {
            double amount = Double.parseDouble(txtAmount.getText().trim());
            acc.withdraw(amount);
            transactions.add(new Transaction(acc.accountNumber, "WITHDRAW", amount));

            outputArea.appendText("Withdrawal successful.\n");
            outputArea.appendText(acc.displayAccountDetails() + "\n");
        } catch (NumberFormatException ex) {
            showError("Amount must be a number.");
        } catch (InsufficientBalanceException ex) {
            showError("Withdrawal failed: " + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            showError(ex.getMessage());
        }
    }

    private void showAccountDetails() {
        Optional<BankAccount> opt = findAccountFromInput();
        if (opt.isEmpty()) return;

        BankAccount acc = opt.get();
        outputArea.appendText("Account Details:\n" + acc.displayAccountDetails() + "\n");
    }

    private void showTransactions() {
        outputArea.appendText("All Transactions:\n");
        for (Transaction t : transactions) {
            outputArea.appendText(t.toString() + "\n");
        }
    }

    private Optional<BankAccount> findAccountFromInput() {
        try {
            int accNo = Integer.parseInt(txtAccountNumber.getText().trim());
            for (BankAccount a : accounts) {
                if (a.accountNumber == accNo) {
                    return Optional.of(a);
                }
            }
            showError("Account not found: " + accNo);
        } catch (NumberFormatException ex) {
            showError("Account number must be an integer.");
        }
        return Optional.empty();
    }

    private void showError(String message) {
        outputArea.appendText("ERROR: " + message + "\n");
    }
}
