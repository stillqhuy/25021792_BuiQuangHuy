import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Tài khoản tiết kiệm. */
public class SavingsAccount extends Account {
    private static final Logger logger = LoggerFactory.getLogger(SavingsAccount.class);
    
    public static final double MAX_WITHDRAW = 1000.0;
    public static final double MIN_BALANCE = 5000.0;

    public SavingsAccount(long accountNumber, double balance) {
        super(accountNumber, balance);
    }

    @Override
    public void deposit(double amount) {
        logger.info("Bắt đầu xử lý nạp tiền {} vào tài khoản {}", amount, getAccountNumber());
        double initialBalance = getBalance();
        try {
            doDepositing(amount);
            double finalBalance = getBalance();
            Transaction t = new Transaction(
                    Transaction.TYPE_DEPOSIT_SAVINGS, amount, initialBalance, finalBalance);
            addTransaction(t);
            logger.info("Nạp tiền thành công: +{}", amount);
        } catch (BankException e) {
            logger.error("Nạp tiền thất bại do lỗi nghiệp vụ.", e);
        }
    }

    @Override
    public void withdraw(double amount) {
        double initialBalance = getBalance();
        try {
            if (amount > MAX_WITHDRAW) {
                throw new InvalidFundingAmountException(amount);
            }
            if (initialBalance - amount < MIN_BALANCE) {
                throw new InsufficientFundsException(amount);
            }
            
            doWithdrawing(amount);
            double finalBalance = getBalance();
            Transaction t = new Transaction(
                    Transaction.TYPE_WITHDRAW_SAVINGS, amount, initialBalance, finalBalance);
            addTransaction(t);
            logger.info("[SAVINGS] Rút {} thành công. Số dư còn: {}", amount, finalBalance);
        } catch (BankException e) {
            logger.error("Rút tiền thất bại đối với tài khoản {}", getAccountNumber(), e);
        }
    }
}