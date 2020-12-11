package Transactions;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class BankTest {

    @Test
    public void testTransfer45000() {
        Bank bank = new Bank();
        var accounts = bank.getAccounts();

        Account fromAccount = new Account("00001");
        fromAccount.setMoney(100000);
        accounts.put("00001",fromAccount);

        Account toAccount = new Account("00002");
        //toAccount.setBlocked(true);
        accounts.put("00002",toAccount);

        bank.transfer("00001","00002",45000L);
        assertThat(bank.getBalance("00001"), is(55000L));
        assertThat(bank.getBalance("00002"), is(45000L));
    }

    @Test
    public void testTransfer55000() {
        Bank bank = new Bank();
        var accounts = bank.getAccounts();

        Account fromAccount = new Account("00001");
        fromAccount.setMoney(100000);
        accounts.put("00001",fromAccount);

        Account toAccount = new Account("00002");
        accounts.put("00002",toAccount);

        bank.transfer("00001","00002",55000L);
        if (fromAccount.isBlocked() && toAccount.isBlocked()) {
            assertThat(bank.getBalance("00001"), is(100000L));
            assertThat(bank.getBalance("00002"), is(0L));
        } else {
            assertThat(bank.getBalance("00001"), is(45000L));
            assertThat(bank.getBalance("00002"), is(55000L));
        }
    }

}