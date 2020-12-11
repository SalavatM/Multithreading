package Transactions;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Bank
{
    private HashMap<String, Account> accounts = new HashMap<>();
    private final Random random = new Random();
    private long totalBalance;

    public HashMap<String, Account> getAccounts() {
        return accounts;
    }

    public static void main(String[] args) {
        Bank bank = new Bank();

        Account fromAccount = new Account("00001");
        fromAccount.setMoney(100000);
        bank.accounts.put("00001", fromAccount);

        Account toAccount = new Account("00002");
        //toAccount.setBlocked(true);
        bank.accounts.put("00002", toAccount);

        bank.transfer("00001", "00002", 50000);
        bank.transfer("00001", "00002", 55000);
    }

    public synchronized boolean isFraud(String fromAccountNum, String toAccountNum, long amount)
            throws InterruptedException
    {
        Thread.sleep(1000);
        return random.nextBoolean();
    }

    /**
     * TODO: реализовать метод. Метод переводит деньги между счетами.
     * Если сумма транзакции > 50000, то после совершения транзакции,
     * она отправляется на проверку Службе Безопасности – вызывается
     * метод isFraud. Если возвращается true, то делается блокировка
     * счетов (как – на ваше усмотрение)
     */
    public void transfer(String fromAccountNum, String toAccountNum, long amount)
    {
        boolean transferFrom = false;
        boolean transferTo = false;

        totalBalance = getTotalBalance();
        System.out.println("Total balance before transfer: " + totalBalance);

        //Security verification
        if (amount > 50000) {
            boolean verified = false;

            try {
                Thread thread = new Thread(String.valueOf(isFraud(fromAccountNum, toAccountNum, amount)));
                thread.start();
                String tname = thread.getName();
                verified = (tname == "true" ? true : false);
                System.out.println("Transfer of amount " + amount + " verified: " + verified);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (!verified) {
                boolean fromAccountBlocked = false;
                boolean toAccountBlocked = false;
                for (Map.Entry<String, Account> entry : accounts.entrySet()) {
                    if (fromAccountBlocked & toAccountBlocked) {
                        break;
                    }
                    if (entry.getKey().equals(fromAccountNum)) {
                        Account fromAccount = entry.getValue();
                        fromAccount.setBlocked(true);
                        fromAccountBlocked = true;
                        System.out.println("Source account " + fromAccountNum + " is blocked");
                     }
                    if (entry.getKey().equals(toAccountNum)) {
                        Account toAccount = entry.getValue();
                        toAccount.setBlocked(true);
                        toAccountBlocked = true;
                        System.out.println("Destination account " + toAccountNum + " is blocked");
                    }
                }
                return;
            }
        }

        //Money transfer
        long currentBalance = getBalance(fromAccountNum);
        if (amount <= currentBalance) {
            for (Map.Entry<String, Account> entry : accounts.entrySet()) {
                if (entry.getKey().equals(fromAccountNum)) {
                    Account fromAccount = entry.getValue();
                    if (fromAccount.isBlocked()) {
                        System.out.println("Source account " + fromAccountNum + " is blocked");
                        break;
                    }
                    fromAccount.setMoney(fromAccount.getMoney() - amount);
                    transferFrom = true;
                    break;
                }
            }
            for (Map.Entry<String, Account> entry : accounts.entrySet()) {
                if (entry.getKey().equals(toAccountNum)) {
                    Account toAccount = entry.getValue();
                    if (toAccount.isBlocked()) {
                        System.out.println("Destination account " + toAccountNum + " is blocked");
                        break;
                    }
                    toAccount.setMoney(toAccount.getMoney() + amount);
                    transferTo = true;
                    break;
                }
            }
        } else {
            System.out.println("Not enough money");
        }

        if (transferFrom && transferTo) {
            System.out.println("Transfer of amount " + amount + " successful");
        } else {
            System.out.println("Transfer of amount " + amount + " failed");
        }

        long totalBalanceAfterTransfer = getTotalBalance();
        if (totalBalance == totalBalanceAfterTransfer) {
            System.out.println("Total balance after transfer: " + totalBalance);
        } else {
            System.out.println("Total balance has changed from " + totalBalance + " to " + totalBalanceAfterTransfer);
        }
    }

    /**
     * TODO: реализовать метод. Возвращает остаток на счёте.
     */
    public long getBalance(String accountNum)
    {
        for (Map.Entry<String, Account> entry : accounts.entrySet()) {
            if (entry.getKey() == accountNum) {
                return entry.getValue().getMoney();
            }
        }
        return 0;
    }

    /**
     * TODO: реализовать метод. Возвращает остаток на всех счетах.
     */
    public long getTotalBalance()
    {
        long Sum = 0;
        for (Map.Entry<String, Account> entry : accounts.entrySet()) {
            Sum += entry.getValue().getMoney();
        }
        return Sum;
    }
}

