package qx.leizige.mq.executor;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class LocalTransactionFactory {


	public static LocalTransactionExecutor<Void, Void> execute(Runnable runnable) {
		return new DefaultLocalTransactionExecutor<>(runnable);
	}

	public static <T> LocalTransactionExecutor<T, Void> execute(Supplier<T> supplier) {
		return new DefaultLocalTransactionExecutor<>(supplier);
	}

	public static <T, P> LocalTransactionExecutor<T, P> execute(Consumer<P> consumer) {
		return new DefaultLocalTransactionExecutor<>(consumer);
	}
}
