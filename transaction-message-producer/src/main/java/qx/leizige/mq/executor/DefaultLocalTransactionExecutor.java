package qx.leizige.mq.executor;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class DefaultLocalTransactionExecutor<R, P> implements LocalTransactionExecutor<R, P> {

	private R result;
	private Exception exception;
	private Runnable runnable;
	private Supplier<R> supplier;
	private Consumer<P> consumer;

	private DefaultLocalTransactionExecutor() {
	}

	public DefaultLocalTransactionExecutor(Runnable runnable) {
		this.runnable = runnable;
	}

	public DefaultLocalTransactionExecutor(Supplier<R> supplier) {
		this.supplier = supplier;
	}

	public DefaultLocalTransactionExecutor(Consumer<P> consumer) {
		this.consumer = consumer;
	}

	@Override
	public void executor() {
		try {
			if (Objects.nonNull(runnable)) {
				runnable.run();
			}
			if (Objects.nonNull(supplier)) {
				result = supplier.get();
			}
		}
		catch (Exception e) {
			this.exception = e;
		}
	}

	@Override
	public void executor(P transactionId) {
		try {
			consumer.accept(transactionId);
		}
		catch (Exception e) {
			this.exception = e;
		}
	}

	@Override
	public R getResult() throws Exception {
		if (exception != null) {
			throw new Exception(exception.getMessage());
		}
		return result;
	}

}
