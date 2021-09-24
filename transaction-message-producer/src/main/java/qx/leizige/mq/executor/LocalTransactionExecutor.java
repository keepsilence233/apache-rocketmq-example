package qx.leizige.mq.executor;

public interface LocalTransactionExecutor<R, P> {


	void executor();

	void executor(P transactionId);

	R getResult() throws Exception;

}
