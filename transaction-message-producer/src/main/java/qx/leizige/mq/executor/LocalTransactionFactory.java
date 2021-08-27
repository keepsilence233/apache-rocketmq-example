package qx.leizige.mq.executor;

import java.util.function.Supplier;

public class LocalTransactionFactory {


    public static LocalTransactionExecutor<Void> execute(Runnable runnable) {
        return new DefaultLocalTransactionExecutor<>(runnable);
    }

    public static <T> LocalTransactionExecutor<T> execute(Supplier<T> supplier) {
        return new DefaultLocalTransactionExecutor<>(supplier);
    }
}
