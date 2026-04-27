import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Hệ thống quản lý ngân hàng. */
public class Bank {
    private static final Logger logger = LoggerFactory.getLogger(Bank.class);
    private List<Customer> customerList;

    public Bank() {
        this.customerList = new ArrayList<>();
    }

    public List<Customer> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(List<Customer> customerList) {
        if (customerList == null) {
            this.customerList = new ArrayList<>();
        } else {
            this.customerList = customerList;
        }
    }

    public void readCustomerList(InputStream inputStream) {
        logger.info("Bắt đầu đọc dữ liệu khách hàng từ InputStream...");
        if (inputStream == null) return;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            Customer currentCustomer = null;
            
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                int lastSpaceIndex = line.lastIndexOf(' ');
                if (lastSpaceIndex <= 0) continue;

                String token = line.substring(lastSpaceIndex + 1).trim();
                
                if (token.matches("\\d{9}")) {
                    String name = line.substring(0, lastSpaceIndex).trim();
                    currentCustomer = new Customer(Long.parseLong(token), name);
                    customerList.add(currentCustomer);
                    logger.debug("Đã thêm khách hàng mới: {}", name);
                } else if (currentCustomer != null) {
                    String[] parts = line.split("\\s+");
                    if (parts.length >= 3) {
                        long num = Long.parseLong(parts[0]);
                        String type = parts[1];
                        double bal = Double.parseDouble(parts[2]);
                        
                        if (Account.CHECKING_TYPE.equals(type)) {
                            currentCustomer.addAccount(new CheckingAccount(num, bal));
                        } else if (Account.SAVINGS_TYPE.equals(type)) {
                            currentCustomer.addAccount(new SavingsAccount(num, bal));
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Lỗi khi đọc file cấu hình khách hàng.", e);
        }
    }

    public String getCustomersInfoByIdOrder() {
        customerList.sort(Comparator.comparingLong(Customer::getIdNumber));
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < customerList.size(); i++) {
            sb.append(customerList.get(i).getCustomerInfo());
            if (i < customerList.size() - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    public String getCustomersInfoByNameOrder() {
        List<Customer> copyList = new ArrayList<>(customerList);
        copyList.sort(Comparator.comparing(Customer::getFullName)
                .thenComparingLong(Customer::getIdNumber));
        
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < copyList.size(); i++) {
            sb.append(copyList.get(i).getCustomerInfo());
            if (i < copyList.size() - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}