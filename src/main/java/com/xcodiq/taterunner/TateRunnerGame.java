package com.xcodiq.taterunner;

import com.xcodiq.taterunner.logger.Logger;
import com.xcodiq.taterunner.manager.Manager;
import com.xcodiq.taterunner.manager.implementation.KeystrokeManager;
import com.xcodiq.taterunner.manager.implementation.ScreenManager;
import com.xcodiq.taterunner.manager.implementation.StateManager;
import com.xcodiq.taterunner.screen.TateGameScreen;
import com.xcodiq.taterunner.screen.implementation.SplashScreen;
import com.xcodiq.taterunner.state.State;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.configuration.DisplayMode;
import de.gurkenlabs.litiengine.gui.screens.Resolution;
import de.gurkenlabs.litiengine.resources.Resources;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class TateRunnerGame implements IGame {

	public static final int WIDTH = 1920, HEIGHT = 1080;

	// set to true to enable debug mode
	public static boolean DEBUG_MODE = true;
	private static TateRunnerGame INSTANCE = null;

	private final Map<Class<? extends Manager>, Manager> registeredManagers = new HashMap<>();

	public TateRunnerGame() {
		if (INSTANCE != null) throw new IllegalStateException("Only one instance can run at the time");
		INSTANCE = this;
	}

	public static TateRunnerGame getInstance() {
		if (INSTANCE == null) throw new IllegalStateException("Cannot access instance; instance might be null");
		return INSTANCE;
	}

	@Override
	public void startGame(String... args) {
		// Set up the game information
		Game.info().setName("Tate Runner");
		Game.info().setSubTitle("");
		Game.info().setDevelopers("Elmar", "Tomas", "Nino");
		Game.info().setVersion("v0.0.1");

		// Initialize the Game
		Game.init(args);

		// Set the default window resolution and window icon, and other default window/display settings
		Game.config().graphics().setDisplayMode(DisplayMode.WINDOWED);
		Game.window().setResolution(Resolution.custom(WIDTH, HEIGHT, "default"));
		Game.window().setIcon(Resources.images().get("icon/andrew_tate_icon.jpg"));

		// Register the managers
		this.registerManagers();

		// Set the current state as starting
		final StateManager stateManager = this.getManager(StateManager.class);
		stateManager.setCurrentState(State.STARTING);

		// Start the actual game
		Game.start();

		// Set the current state as started
		stateManager.setCurrentState(State.STARTED);

		// Set starting screen as the splash screen
		final ScreenManager screenManager = this.getManager(ScreenManager.class);
		screenManager.setCurrentScreen(SplashScreen.class);
	}

	@Override
	public void stopGame() {
		Logger.log("Stopping tate-runner...");

		// Set the current state as stopping
		final StateManager stateManager = this.getManager(StateManager.class);
		stateManager.setCurrentState(State.STOPPING);

		// Disable all the registered managers
		this.registeredManagers.forEach((aClass, manager) -> manager.disable());
	}

	@Override
	public void registerManagers() {
		// Make a stream of all the managers to register
		Stream.of(
				new StateManager(this),
				new ScreenManager(this),
				new KeystrokeManager(this)
		).forEach(manager -> {
			// Enable the manager
			manager.enable();

			// Put the manager instance in the map
			this.registeredManagers.put(manager.getClass(), manager);
		});
	}

	@Override
	public <T extends Manager> T getManager(Class<T> typeClass) {
		// Look for a registered manager by the given type class
		final T manager = (T) this.registeredManagers.get(typeClass);
		if (manager == null) throw new IllegalStateException("Manager has not been registered yet!");

		// Return the registered manager bound to the type class
		return manager;
	}

	@Override
	public void addScreen(TateGameScreen tateGameScreen) {
		try {
			// Use the game instance to register the screen
			Game.screens().add(tateGameScreen);
		} catch (NullPointerException exception) {
			Logger.error("Game#init() hasn't been called yet!");
		}
	}
}
