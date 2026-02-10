public class BankAccount {
    private final String email;
    private double balance;

    public BankAccount(String email, double initialBalance) {
        if (!isEmailValid(email)) {
            throw new IllegalArgumentException("Invalid email address.");
        }
        if (!(initialBalance == 0.0 || isAmountValid(initialBalance))) {
            throw new IllegalArgumentException("Invalid initial balance.");
        }
        this.email = email;
        this.balance = initialBalance;
    }

    public double getBalance() {
        return balance;
    }

    public String getEmail() {
        return email;
    }

    public void withdraw(double amount) {
        if (!isAmountValid(amount)) {
            throw new IllegalArgumentException("Invalid withdrawal amount.");
        }
        if (amount > balance) {
            throw new InsufficientFundsException("Insufficient funds.");
        }
        balance -= amount;
    }


    public void deposit(double amount) {
        if (!isAmountValid(amount)) {
            throw new IllegalArgumentException("Invalid deposit amount.");
        }
        balance += amount;
    }

    public void transfer(BankAccount toAccount, double amount) {
        if (toAccount == null) {
            throw new IllegalArgumentException("Target account cannot be null.");
        }
        if (toAccount == this) {
            throw new IllegalArgumentException("Cannot transfer to the same account.");
        }
        withdraw(amount);
        toAccount.deposit(amount);
    }

    private boolean isEmailValid(String email) {
        if (email == null || email.isEmpty() || email.chars().filter(ch -> ch == '@').count() != 1) {
            return false;
        }
        String[] parts = email.split("@");
        if (parts.length != 2 || parts[0].isEmpty() || parts[1].isEmpty() || parts[1].indexOf('.') == -1) {
            return false;
        }
        return true; // Additional checks can be added as per the specification
    }

    private boolean isAmountValid(double amount) {
        return amount > 0 && amount == Math.round(amount * 100) / 100.0;
    }
}