import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {
    private BankAccount account;

    @BeforeEach
    void setUp() {
        account = new BankAccount("a@b.com", 200.0);
    }

    // ----------------------------
    // Constructor + getters
    // ----------------------------

    @Test
    void validEmailAndPositiveBalance() {
        assertEquals("a@b.com", account.getEmail());
        assertEquals(200.0, account.getBalance());
    }

    @Test
    void zeroInitialBalanceAllowed() {
        BankAccount zeroBalanceAccount = new BankAccount("a@b.com", 0.0);
        assertEquals(0.0, zeroBalanceAccount.getBalance());
    }

    @Test
    void twoDecimalInitialBalanceAllowed() {
        BankAccount twoDecimalAccount = new BankAccount("user@example.com", 500.50);
        assertEquals(500.50, twoDecimalAccount.getBalance());
    }

    @Test
    void emptyEmailRejected() {
        assertThrows(IllegalArgumentException.class, () -> new BankAccount("", 100.0));
    }

    @Test
    void nullEmailRejected() {
        assertThrows(IllegalArgumentException.class, () -> new BankAccount(null, 100.0));
    }

    @Test
    void missingAtSymbolRejected() {
        assertThrows(IllegalArgumentException.class, () -> new BankAccount("abcdef.mail.com", 100.0));
    }

    @Test
    void negativeInitialBalanceRejected() {
        assertThrows(IllegalArgumentException.class, () -> new BankAccount("a@b.com", -1.0));
    }

    @Test
    void moreThanTwoDecimalsInitialBalanceRejected() {
        assertThrows(IllegalArgumentException.class, () -> new BankAccount("a@b.com", 10.999));
    }

    @Test
    void nanInitialBalanceRejected() {
        assertThrows(IllegalArgumentException.class, () -> new BankAccount("a@b.com", Double.NaN));
    }

    @Test
    void infinityInitialBalanceRejected() {
        assertThrows(IllegalArgumentException.class, () -> new BankAccount("a@b.com", Double.POSITIVE_INFINITY));
    }

    @Test
    void returnsInitialBalance() {
        assertEquals(200.0, account.getBalance());
    }

    @Test
    void returnsZeroBalance() {
        BankAccount zeroBalanceAccount = new BankAccount("a@b.com", 0.0);
        assertEquals(0.0, zeroBalanceAccount.getBalance());
    }

    // ----------------------------
    // Withdraw
    // ----------------------------

    @Test
    void updatesAfterSuccessfulWithdraw() {
        account.withdraw(25.0);
        assertEquals(175.0, account.getBalance());
    }

    @Test
    void unchangedAfterInsufficientFundsWithdraw() {
        assertThrows(InsufficientFundsException.class, () -> account.withdraw(1000.0));
        assertEquals(200.0, account.getBalance());
    }

    @Test
    void handlesSmallWithdraw() {
        account.withdraw(0.01);
        assertEquals(199.99, account.getBalance());
    }

    @Test
    void validMiddleWithdraw() {
        account.withdraw(100.0);
        assertEquals(100.0, account.getBalance());
    }

    @Test
    void withdrawEqualsBalance() {
        account.withdraw(200.0);
        assertEquals(0.0, account.getBalance());
    }

    @Test
    void insufficientFundsThrows() {
        assertThrows(InsufficientFundsException.class, () -> account.withdraw(200.01));
        assertEquals(200.0, account.getBalance());
    }

    @Test
    void negativeWithdrawThrows() {
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(-0.01));
        assertEquals(200.0, account.getBalance());
    }

    @Test
    void zeroWithdrawThrows() {
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(0.0));
        assertEquals(200.0, account.getBalance());
    }

    @Test
    void moreThanTwoDecimalsWithdrawThrows() {
        assertThrows(IllegalArgumentException.class, () -> account.withdraw(10.999));
        assertEquals(200.0, account.getBalance());
    }

    @Test
    void multipleWithdrawalsAccumulate() {
        account.withdraw(25.0);
        account.withdraw(50.0);
        assertEquals(125.0, account.getBalance());
    }

    @Test
    void failedWithdrawDoesNotChangeBalance() {
        assertThrows(InsufficientFundsException.class, () -> account.withdraw(1000.0));
        assertEquals(200.0, account.getBalance());
    }

    @Test
    void withdrawFromZeroBalanceThrows() {
        BankAccount zeroBalanceAccount = new BankAccount("a@b.com", 0.0);
        assertThrows(InsufficientFundsException.class, () -> zeroBalanceAccount.withdraw(0.01));
        assertEquals(0.0, zeroBalanceAccount.getBalance());
    }

    // ----------------------------
    // Deposit
    // ----------------------------

    @Test
    void updatesAfterDeposit() {
        account.deposit(50.0);
        assertEquals(250.0, account.getBalance());
    }

    @Test
    void unchangedAfterInvalidDeposit() {
        assertThrows(IllegalArgumentException.class, () -> account.deposit(10.999));
        assertEquals(200.0, account.getBalance());
    }

    @Test
    void validMiddleDeposit() {
        account.deposit(50.0);
        assertEquals(250.0, account.getBalance());
    }

    @Test
    void twoDecimalDeposit() {
        account.deposit(0.01);
        assertEquals(200.01, account.getBalance());
    }

    @Test
    void oneDecimalDeposit() {
        account.deposit(0.1);
        assertEquals(200.1, account.getBalance());
    }

    @Test
    void zeroDepositThrows() {
        assertThrows(IllegalArgumentException.class, () -> account.deposit(0.0));
        assertEquals(200.0, account.getBalance());
    }

    @Test
    void negativeDepositThrows() {
        assertThrows(IllegalArgumentException.class, () -> account.deposit(-0.01));
        assertEquals(200.0, account.getBalance());
    }

    @Test
    void moreThanTwoDecimalsDepositThrows() {
        assertThrows(IllegalArgumentException.class, () -> account.deposit(10.999));
        assertEquals(200.0, account.getBalance());
    }

    @Test
    void multipleDepositsAccumulate() {
        account.deposit(25.5);
        account.deposit(10.99);
        assertEquals(236.49, account.getBalance());
    }

    @Test
    void largeDeposit() {
        account.deposit(999999999.99);
        assertEquals(1000000199.99, account.getBalance());
    }

    @Test
    void depositIntoZeroBalanceAccount() {
        BankAccount zeroBalanceAccount = new BankAccount("a@b.com", 0.0);
        zeroBalanceAccount.deposit(1.0);
        assertEquals(1.0, zeroBalanceAccount.getBalance());
    }

    @Test
    void invalidDepositLeavesBalanceUnchanged() {
        assertThrows(IllegalArgumentException.class, () -> account.deposit(10.999));
        assertEquals(200.0, account.getBalance());
    }

    // ----------------------------
    // Transfer
    // ----------------------------

    @Test
    void reflectsTransferOut() {
        BankAccount toAccount = new BankAccount("c@d.com", 50.0);
        account.transfer(toAccount, 100.0);
        assertEquals(100.0, account.getBalance());
    }

    @Test
    void reflectsTransferIn() {
        BankAccount toAccount = new BankAccount("c@d.com", 50.0);
        account.transfer(toAccount, 100.0);
        assertEquals(150.0, toAccount.getBalance());
    }

    @Test
    void validMiddleTransfer() {
        BankAccount toAccount = new BankAccount("c@d.com", 50.0);
        account.transfer(toAccount, 100.0);
        assertEquals(100.0, account.getBalance());
        assertEquals(150.0, toAccount.getBalance());
    }

    @Test
    void twoDecimalSmallTransfer() {
        BankAccount toAccount = new BankAccount("c@d.com", 50.0);
        account.transfer(toAccount, 0.01);
        assertEquals(199.99, account.getBalance());
        assertEquals(50.01, toAccount.getBalance());
    }

    @Test
    void transferEqualsBalance() {
        BankAccount toAccount = new BankAccount("c@d.com", 50.0);
        account.transfer(toAccount, 200.0);
        assertEquals(0.0, account.getBalance());
        assertEquals(250.0, toAccount.getBalance());
    }

    @Test
    void nullTargetThrows() {
        assertThrows(IllegalArgumentException.class, () -> account.transfer(null, 10.0));
        assertEquals(200.0, account.getBalance());
    }

    @Test
    void transferToSelfThrows() {
        assertThrows(IllegalArgumentException.class, () -> account.transfer(account, 10.0));
        assertEquals(200.0, account.getBalance());
    }

    @Test
    void zeroAmountThrows() {
        BankAccount toAccount = new BankAccount("c@d.com", 50.0);
        assertThrows(IllegalArgumentException.class, () -> account.transfer(toAccount, 0.0));
    }

    // ----------------------------
    // Email immutability + format allowances
    // ----------------------------

    @Test
    void returnsExactEmail() {
        assertEquals("a@b.com", account.getEmail());
    }

    @Test
    void preservesCapitalization() {
        BankAccount capitalizedEmailAccount = new BankAccount("User@Example.com", 10.0);
        assertEquals("User@Example.com", capitalizedEmailAccount.getEmail());
    }

    @Test
    void supportsPlusSignInEmail() {
        BankAccount plusSignAccount = new BankAccount("user+tag@example.com", 10.0);
        assertEquals("user+tag@example.com", plusSignAccount.getEmail());
    }

    @Test
    void supportsDotsInLocalPart() {
        BankAccount dotAccount = new BankAccount("user.name@example.com", 10.0);
        assertEquals("user.name@example.com", dotAccount.getEmail());
    }

    @Test
    void supportsHyphenInLocalPart() {
        BankAccount hyphenAccount = new BankAccount("a-b@mail.com", 10.0);
        assertEquals("a-b@mail.com", hyphenAccount.getEmail());
    }

    @Test
    void unchangedAfterDepositEmail() {
        account.deposit(1.0);
        assertEquals("a@b.com", account.getEmail());
    }

    @Test
    void unchangedAfterWithdrawEmail() {
        account.withdraw(1.0);
        assertEquals("a@b.com", account.getEmail());
    }

    @Test
    void unchangedAfterTransferEmail() {
        BankAccount toAccount = new BankAccount("c@d.com", 0.0);
        account.transfer(toAccount, 1.0);
        assertEquals("a@b.com", account.getEmail());
    }

    @Test
    void returnsUnderscoreEmailExactly() {
        BankAccount underscoreAccount = new BankAccount("abc_def@mail.com", 10.0);
        assertEquals("abc_def@mail.com", underscoreAccount.getEmail());
    }

    @Test
    void returnsComplexEmailExactly() {
        BankAccount complexEmailAccount = new BankAccount("test.user+dev@sub.example.org", 10.0);
        assertEquals("test.user+dev@sub.example.org", complexEmailAccount.getEmail());
    }

    // ----------------------------
    // Multi-operation scenario
    // ----------------------------

    @Test
    void handlesMultipleTransactions() {
        account.deposit(100.0);
        account.withdraw(50.0);
        account.deposit(25.0);
        assertEquals(275.0, account.getBalance());
    }
}
