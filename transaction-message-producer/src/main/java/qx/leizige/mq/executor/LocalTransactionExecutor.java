package qx.leizige.mq.executor;

public interface LocalTransactionExecutor<R> {


    void executor();


//    void executor(String transactionId);


    R getResult() throws Exception;


}
