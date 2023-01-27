package com.xcodiq.taterunner.util.collection;

import com.xcodiq.taterunner.TateRunnerGame;

import java.time.Instant;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;

public class ExpiringList<E> extends HashSet<E> {

	private final HashMap<E, ScheduledFuture<?>> refreshFutures = new HashMap<>();
	private final HashMap<E, Instant> addedAt = new HashMap<>();
	private Function<E, Boolean> purgeFunction;

	private double delay = 5;
	private TimeUnit timeUnit = TimeUnit.SECONDS;

	public ExpiringList() {
	}

	public ExpiringList(double delay, TimeUnit timeUnit) {
		this.delay = delay;
		this.timeUnit = timeUnit;
	}

	public ExpiringList(Consumer<E> purgeFunction) {
		this.purgeFunction = (entry) -> {
			purgeFunction.accept(entry);
			return true;
		};
	}

	public ExpiringList(Function<E, Boolean> purgeFunction) {
		this.purgeFunction = purgeFunction;
	}

	public ExpiringList<E> setPurgeFunction(Function<E, Boolean> purgeFunction) {
		this.purgeFunction = purgeFunction;
		return this;
	}

	@Override
	public boolean add(E entry) {
		return add(entry, this.delay, this.timeUnit);
	}

	public boolean add(E entry, double delay, TimeUnit timeUnit) {
		this.addedAt.put(entry, Instant.now());

		this.queue(entry, delay, timeUnit);

		return super.add(entry);
	}

	public void update(E entry) {
		this.queue(entry);
	}

	public void addOrUpdate(E entry) {
		if (this.contains(entry)) this.update(entry);
		else this.add(entry);
	}

	@Override
	public boolean remove(Object object) {
		//Remove from addedAt
		this.addedAt.remove(object);

		ScheduledFuture<?> scheduledFuture = this.getScheduledFuture((E) object);
		if (scheduledFuture != null) scheduledFuture.cancel(true);
		this.refreshFutures.remove(object);

		return super.remove(object);
	}

	private void queue(E entry) {
		queue(entry, this.delay, this.timeUnit);
	}

	private void queue(E entry, double delay, TimeUnit timeUnit) {
		// Make sure to remove previous values
		if (refreshFutures.containsKey(entry)) {
			refreshFutures.get(entry).cancel(true);
			refreshFutures.remove(entry);
		}

		ScheduledFuture<?> scheduledFuture = TateRunnerGame.getThreadPoolExecutor().schedule(() -> {
			this.remove(entry);
			purgeFunction.apply(entry);
		}, (long) delay, timeUnit);

		refreshFutures.put(entry, scheduledFuture);
	}

	public Instant isAddedAt(E entry) {
		return this.addedAt.computeIfAbsent(entry, e -> Instant.now());
	}

	public Instant expiresAt(E entry) {
		ScheduledFuture<?> scheduledFuture = this.getScheduledFuture(entry);
		return Instant.now().plusMillis(scheduledFuture.getDelay(TimeUnit.MILLISECONDS));
	}

	public long getTimeLeft(E key, TimeUnit timeUnit) {
		final Instant expiresAt = this.expiresAt(key);
		return timeUnit.convert(expiresAt.minusMillis(System.currentTimeMillis()).toEpochMilli(), TimeUnit.MILLISECONDS);
	}

	public double getDelay() {
		return delay;
	}

	public ExpiringList<E> setDelay(double delay) {
		this.delay = delay;
		return this;
	}

	public TimeUnit getTimeUnit() {
		return timeUnit;
	}

	public ExpiringList<E> setTimeUnit(TimeUnit timeUnit) {
		this.timeUnit = timeUnit;
		return this;
	}

	public ScheduledFuture<?> getScheduledFuture(E entry) {
		return refreshFutures.get(entry);
	}

}