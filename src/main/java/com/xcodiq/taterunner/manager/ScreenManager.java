package com.xcodiq.taterunner.manager;

import com.xcodiq.taterunner.TateRunnerGame;
import com.xcodiq.taterunner.logger.Logger;
import com.xcodiq.taterunner.screen.SplashScreen;
import com.xcodiq.taterunner.screen.TateGameScreen;
import com.xcodiq.taterunner.util.text.TextUtil;
import de.gurkenlabs.litiengine.Game;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public final class ScreenManager extends Manager {

	private final Map<Class<? extends TateGameScreen>, TateGameScreen> registeredScreens = new HashMap<>();

	public ScreenManager(TateRunnerGame tateRunnerGame) {
		super(tateRunnerGame);
	}

	@Override
	public void enable() {
		// Make a stream of all the screens to register
		Stream.of(
				new SplashScreen()
		).forEach(tateGameScreen -> {
			// Add the screen to the game
			this.tateRunnerGame.addScreen(tateGameScreen);

			// Add the screen to the registered screens map
			this.registeredScreens.put(tateGameScreen.getClass(), tateGameScreen);
			Logger.debug("Registered screen: " + tateGameScreen.getName());
		});
	}

	@Override
	public void disable() {
		// Clear the caches of the text utility class
		TextUtil.clearCache();
	}

	public <T extends TateGameScreen> void showScreen(Class<T> screenClass) {
		// Get the game screen from the cache
		final TateGameScreen gameScreen = this.registeredScreens.get(screenClass);
		if (gameScreen == null) throw new IllegalStateException("TateGameScreen doesn't exist!");

		// Display the game screen
		Game.screens().display(gameScreen.getName());
	}
}
