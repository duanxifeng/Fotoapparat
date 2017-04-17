package io.fotoapparat.result.extender.rxjava2;

import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import io.reactivex.observers.TestObserver;

public class ObservableExtenderTest {

	private TestObserver<Object> observer = new TestObserver<>();

	@Test
	public void completed() throws Exception {
		// Given
		Future<String> future = new CallableFuture<>(new Callable<String>() {
			@Override
			public String call() throws Exception {
				return "Hello";
			}
		});

		// When
		ObservableExtender.<String>observableExtender()
				.extend(future)
				.subscribe(observer);

		// Then
		observer.assertValue("Hello");
		observer.assertNoErrors();
	}

	@Test
	public void error() throws Exception {
		// Given
		Future<String> future = new CallableFuture<>(new Callable<String>() {
			@Override
			public String call() throws Exception {
				throw new RuntimeException("What a failure");
			}
		});

		// When
		ObservableExtender.<String>observableExtender()
				.extend(future)
				.subscribe(observer);

		// Then
		observer.assertNoValues();
		observer.assertError(ExecutionException.class);
	}

}