package com.xcodiq.taterunner.manager.implementation;

import com.xcodiq.taterunner.TateRunnerGame;
import com.xcodiq.taterunner.manager.Manager;
import com.xcodiq.taterunner.screen.TateGameScreen;
import de.gurkenlabs.litiengine.input.Input;

import java.lang.reflect.Method;

public final class KeystrokeManager extends Manager {

	private long lastKeystrokeInvoke = 0L;

	public KeystrokeManager(TateRunnerGame tateRunnerGame) {
		super(tateRunnerGame);
	}

	@Override
	public void enable() {
		final ScreenManager screenManager = tateRunnerGame.getManager(ScreenManager.class);

		Input.keyboard().onKeyPressed(keyEvent -> {
			for (TateGameScreen tateGameScreen : screenManager.getRegisteredScreens().values()) {
				final Method keystrokeMethod = tateGameScreen.getKeystrokeMethod();
				if (keystrokeMethod == null || !screenManager.getCurrentScreen().equals(tateGameScreen)) continue;

				try {
					if (System.currentTimeMillis() - this.lastKeystrokeInvoke > 100) {
						// Invoke the keystroke method
						keystrokeMethod.setAccessible(true);
						keystrokeMethod.invoke(tateGameScreen, keyEvent);

						// Update the last keystroke invoke time
						this.lastKeystrokeInvoke = System.currentTimeMillis();
					}
					return;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	public void disable() {
		// Reset
		this.lastKeystrokeInvoke = 0L;
		Input.keyboard().clearExplicitListeners();
	}
}
