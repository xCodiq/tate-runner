package com.xcodiq.taterunner.screen.button.implementation;

import com.xcodiq.taterunner.TateRunnerGame;
import com.xcodiq.taterunner.manager.implementation.ScreenManager;
import com.xcodiq.taterunner.manager.implementation.StateManager;
import com.xcodiq.taterunner.screen.button.Button;
import com.xcodiq.taterunner.screen.implementation.RunnerScreen;
import com.xcodiq.taterunner.state.State;
import de.gurkenlabs.litiengine.resources.Resources;

public final class StoreButton extends Button {

	public StoreButton(TateRunnerGame tateRunner) {
		super(Resources.images().get("textures/button/button-unfocussed.png"),
				Resources.images().get("textures/button/button-focussed.png"),
				1694, 30, 196, 63);

		this.setClickEvent(mouseEvent -> {
			// Switch to the Runner screen
			tateRunner.getManager(ScreenManager.class).showScreen(RunnerScreen.class);
		});
	}
}
