package qx.leizige.mq.executor;

public interface LocalTransactionExecutor<R> {


    void executor();

    R getResult() throws Exception;


}
