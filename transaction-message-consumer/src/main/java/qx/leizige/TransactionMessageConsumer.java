package qx.leizige;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author leizige
 * created 2021/08/24
 */
@SpringBootApplication
public class TransactionMessageConsumer {
    public static void main(String[] args) {
        SpringApplication.run(TransactionMessageConsumer.class, args);
    }
}
