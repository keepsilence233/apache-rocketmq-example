package qx.leizige.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import qx.leizige.TransactionMessageProducer;
import qx.leizige.mp.service.AccountService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TransactionMessageProducer.class)
public class AccountTest {


    @Autowired
    private AccountService accountService;

    @Test
    public void list() {
        accountService.list().forEach(System.out::println);
    }

}
