package com.xcodiq.taterunner;

import com.xcodiq.taterunner.manager.Manager;
import com.xcodiq.taterunner.manager.ScreenManager;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.GameInfo;
import de.gurkenlabs.litiengine.configuration.DisplayMode;
import de.gurkenlabs.litiengine.gui.screens.GameScreen;
import de.gurkenlabs.litiengine.gui.screens.Resolution;
import de.gurkenlabs.litiengine.resources.Resources;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class TateRunnerGame implements IGame {

	// set to true to enable debug mode
	public static boolean DEBUG_MODE = false;
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
		final GameInfo info = Game.info();
		info.setName("Tate Runner");
		info.setSubTitle("");
		info.setDevelopers("Elmar", "Tomas", "Nino");
		info.setVersion("v0.0.1");

		// Initialize the Game
		Game.init(args);

		// Register the managers
		this.registerManagers();

		// Set the default window resolution and window icon, and other default window/display settings
		Game.window().setResolution(Resolution.Ratio16x9.RES_1920x1080);
		Game.window().setIcon(Resources.images().get("icon/andrew_tate_icon.jpg"));

		// Start the actual game
		Game.start();

		// Show the default splash screen
//		final ScreenManager screenManager = this.getManager(ScreenManager.class);
//		screenManager.showScreen(SplashScreen.class);

	}

	@Override
	public void stopGame() {

	}

	@Override
	public void registerManagers() {
		// Make a stream of all the managers to register
		Stream.of(
				new ScreenManager(this)
		).forEach(manager -> {
			// Enable the manager
			manager.enable();

			// Put the manager instance in the map
			this.registeredManagers.put(manager.getClass(), manager);
		});
	}

	@Override
	public <T extends Manager> T getManager(Class<T> typeClass) {
		final T manager = (T) this.registeredManagers.get(typeClass);
		if (manager == null) throw new IllegalStateException("Manager has not been registered yet!");

		return manager;
	}

	@Override
	public void addScreen(GameScreen gameScreen) {
		try {
			Game.screens().add(gameScreen);
		} catch (NullPointerException exception) {
			System.err.println("Game#init() hasn't been called yet!");
		}
	}
}
