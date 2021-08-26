package qx.leizige.mq.executor;

import java.util.function.Supplier;

public class DefaultLocalTransactionExecutor<R> implements LocalTransactionExecutor<R> {

    private R result;
    private Exception exception;
    private Runnable runnable;
    private Supplier<R> supplier;

    private DefaultLocalTransactionExecutor() {
    }

    public DefaultLocalTransactionExecutor(Runnable runnable) {
        this.runnable = runnable;
    }

    public DefaultLocalTransactionExecutor(Supplier<R> supplier) {
        this.supplier = supplier;
    }

    @Override
    public void executor() {
        try {
            if (runnable != null) {
                runnable.run();
            } else {
                result = supplier.get();
            }
        } catch (Exception e) {
            this.exception = e;
        }
    }



    @Override
    public R getResult() throws Exception {
        if (exception != null) {
            throw new Exception();
        }
        return result;
    }

}
