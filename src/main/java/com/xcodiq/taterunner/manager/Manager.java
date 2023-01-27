package com.xcodiq.taterunner.manager;

import com.xcodiq.taterunner.TateRunnerGame;

public abstract class Manager {

	protected final TateRunnerGame tateRunnerGame;

	public Manager(TateRunnerGame tateRunnerGame) {
		this.tateRunnerGame = tateRunnerGame;
	}

	public abstract void enable();

	public abstract void disable();
}
