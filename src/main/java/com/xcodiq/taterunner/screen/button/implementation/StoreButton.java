package com.xcodiq.taterunner.screen.button.implementation;

import com.xcodiq.taterunner.TateRunnerGame;
import com.xcodiq.taterunner.manager.implementation.ScreenManager;
import com.xcodiq.taterunner.manager.implementation.StateManager;
import com.xcodiq.taterunner.screen.button.Button;
import com.xcodiq.taterunner.screen.implementation.RunnerScreen;
import com.xcodiq.taterunner.state.State;
import com.xcodiq.taterunner.util.image.ImageUtil;
import de.gurkenlabs.litiengine.resources.Resources;

public final class StoreButton extends Button {

	public StoreButton(TateRunnerGame tateRunner) {
		super(ImageUtil.loadImage("textures/button/button-unfocussed.png"),
				ImageUtil.loadImage("textures/button/button-focussed.png"),
				1890, 30);
//				30, 1050);

		this.setClickEvent(mouseEvent -> {
			// Switch to the Runner screen
			tateRunner.getManager(ScreenManager.class).showScreen(RunnerScreen.class);
		});
	}
}
