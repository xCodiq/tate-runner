package com.xcodiq.taterunner.manager.implementation;

import com.xcodiq.taterunner.TateRunnerGame;
import com.xcodiq.taterunner.logger.Logger;
import com.xcodiq.taterunner.manager.Manager;
import com.xcodiq.taterunner.screen.TateGameScreen;
import com.xcodiq.taterunner.screen.implementation.CosmeticShopScreen;
import com.xcodiq.taterunner.screen.implementation.RunnerScreen;
import com.xcodiq.taterunner.screen.implementation.SplashScreen;
import com.xcodiq.taterunner.util.text.TextUtil;
import de.gurkenlabs.litiengine.Game;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

public final class ScreenManager extends Manager {

	private final Map<Class<? extends TateGameScreen>, TateGameScreen> registeredScreens = new HashMap<>();
	private TateGameScreen currentScreen;

	public ScreenManager(TateRunnerGame tateRunnerGame) {
		super(tateRunnerGame);
	}

	@Override
	public void enable() {
		// Make a stream of all the screens to register
		Stream.of(
				new SplashScreen(this.tateRunnerGame),
				new RunnerScreen(this.tateRunnerGame),
				new CosmeticShopScreen(this.tateRunnerGame)
		).forEach(tateGameScreen -> {
			// Add the screen to the game
			this.tateRunnerGame.addScreen(tateGameScreen);

			// Add the screen to the registered screens map
			this.registeredScreens.put(tateGameScreen.getClass(), tateGameScreen);
			Logger.debug("[ScreenManager] Registered screen: " + tateGameScreen.getName());
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
		gameScreen.preShow();

		// Set the game screen as the current screen
		this.currentScreen = gameScreen;
		Logger.debug("[ScreenManager] Showing screen: " + gameScreen.getName());
	}

	public Map<Class<? extends TateGameScreen>, TateGameScreen> getRegisteredScreens() {
		return registeredScreens;
	}

	public TateGameScreen getCurrentScreen() {
		return currentScreen;
	}

	public <T extends TateGameScreen> void setCurrentScreen(Class<T> screenClass) {
		this.currentScreen = Objects.requireNonNull(this.registeredScreens.get(screenClass),
				"TateGameScreen doesn't exist!");
	}
}
