import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Lớp trừu tượng đại diện cho một tài khoản ngân hàng. */
public abstract class Account {
    private static final Logger logger = LoggerFactory.getLogger(Account.class);

    public static final String CHECKING_TYPE = "CHECKING";
    public static final String SAVINGS_TYPE = "SAVINGS";

    private long accountNumber;
    private double balance;
    protected List<Transaction> transactionList;

    public Account(long accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.transactionList = new ArrayList<>();
    }

    public long getAccountNumber() { return accountNumber; }
    public void setAccountNumber(long accountNumber) { this.accountNumber = accountNumber; }
    public double getBalance() { return balance; }
    protected void setBalance(double balance) { this.balance = balance; }
    public List<Transaction> getTransactionList() { return transactionList; }

    public void setTransactionList(List<Transaction> transactionList) {
        if (transactionList == null) {
            this.transactionList = new ArrayList<>();
        } else {
            this.transactionList = transactionList;
        }
    }

    public abstract void deposit(double amount);
    public abstract void withdraw(double amount);

    protected void doDepositing(double amount) throws InvalidFundingAmountException {
        if (amount <= 0) {
            logger.error("Lỗi nạp tiền: Số tiền {} không hợp lệ.", amount);
            throw new InvalidFundingAmountException(amount);
        }
        balance += amount;
    }

    protected void doWithdrawing(double amount) throws BankException {
        if (amount <= 0) {
            logger.error("Lỗi rút tiền: Số tiền {} không hợp lệ.", amount);
            throw new InvalidFundingAmountException(amount);
        }
        if (amount > balance) {
            logger.error("Lỗi rút tiền: Số dư hiện tại ({}) không đủ để rút {}.", balance, amount);
            throw new InsufficientFundsException(amount);
        }
        balance -= amount;
    }

    public void addTransaction(Transaction transaction) {
        if (transaction != null) {
            transactionList.add(transaction);
        }
    }

    public String getTransactionHistory() {
        StringBuilder sb = new StringBuilder();
        sb.append("Lịch sử giao dịch của tài khoản ").append(accountNumber).append(":\n");
        for (int i = 0; i < transactionList.size(); i++) {
            sb.append(transactionList.get(i).getTransactionSummary());
            if (i < transactionList.size() - 1) {
                sb.append("\n");
            }
        }
        logger.debug("Đã lấy lịch sử cho tài khoản: {}", accountNumber);
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Account)) return false;
        Account other = (Account) obj;
        return this.accountNumber == other.accountNumber;
    }

    @Override
    public int hashCode() {
        return (int) (accountNumber ^ (accountNumber >>> 32));
    }
}