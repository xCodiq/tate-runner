package com.xcodiq.taterunner.manager.implementation;

import com.xcodiq.taterunner.TateRunnerGame;
import com.xcodiq.taterunner.manager.Manager;
import com.xcodiq.taterunner.screen.TateGameScreen;
import com.xcodiq.taterunner.screen.button.Button;
import de.gurkenlabs.litiengine.input.Input;

import java.awt.*;

public final class MouseManager extends Manager {

	public MouseManager(TateRunnerGame tateRunnerGame) {
		super(tateRunnerGame);
	}

	@Override
	public void enable() {
		final ScreenManager screenManager = tateRunnerGame.getManager(ScreenManager.class);

		Input.mouse().onMoved(moveEvent -> {
			for (TateGameScreen tateGameScreen : screenManager.getRegisteredScreens().values()) {
				final TateGameScreen currentScreen = screenManager.getCurrentScreen();
				if (currentScreen == null || !screenManager.getCurrentScreen().equals(tateGameScreen)) continue;

				for (Button button : tateGameScreen.getButtons()) {
					button.setFocused(button.getRectangle().contains(moveEvent.getPoint()));
				}
			}
		});

		Input.mouse().onPressed(clickEvent -> {
			for (TateGameScreen tateGameScreen : screenManager.getRegisteredScreens().values()) {
				final TateGameScreen currentScreen = screenManager.getCurrentScreen();
				if (currentScreen == null || !currentScreen.equals(tateGameScreen)) continue;

				for (Button button : tateGameScreen.getButtons()) {
					final Point point = clickEvent.getPoint();
					if (button.getRectangle().contains(point) && button.canClick()) {
						button.getClickConsumer().accept(clickEvent);
						return;
					}
				}
			}
		});
	}

	@Override
	public void disable() {

	}
}
