package com.xcodiq.taterunner.keystroke;

import com.xcodiq.taterunner.TateRunnerGame;

import java.awt.event.KeyEvent;
import java.util.function.Consumer;

public final class Keystroke {

	private final KeyEvent keyEvent;
	private final Consumer<TateRunnerGame> clickConsumer;

	public Keystroke(KeyEvent keyEvent, Consumer<TateRunnerGame> clickConsumer) {
		this.keyEvent = keyEvent;
		this.clickConsumer = clickConsumer;
	}

	public KeyEvent getKeyEvent() {
		return keyEvent;
	}

	public Consumer<TateRunnerGame> getClickConsumer() {
		return clickConsumer;
	}
}
