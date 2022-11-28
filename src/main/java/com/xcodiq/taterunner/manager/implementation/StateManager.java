package com.xcodiq.taterunner.manager.implementation;

import com.xcodiq.taterunner.TateRunnerGame;
import com.xcodiq.taterunner.logger.Logger;
import com.xcodiq.taterunner.manager.Manager;
import com.xcodiq.taterunner.state.State;

public final class StateManager extends Manager {

	private State currentState;

	public StateManager(TateRunnerGame tateRunnerGame) {
		super(tateRunnerGame);
	}

	@Override
	public void enable() {
		// unused
	}

	@Override
	public void disable() {
		// unused
	}

	public State getCurrentState() {
		return currentState;
	}

	public void setCurrentState(State currentState) {
		this.currentState = currentState;
		Logger.debug("[StateManager] Switched to " + currentState + "");
	}
}
