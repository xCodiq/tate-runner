package com.xcodiq.taterunner;

import com.xcodiq.taterunner.logger.Logger;
import com.xcodiq.taterunner.manager.Manager;
import com.xcodiq.taterunner.manager.implementation.*;
import com.xcodiq.taterunner.screen.TateGameScreen;
import com.xcodiq.taterunner.screen.implementation.SplashScreen;
import com.xcodiq.taterunner.state.State;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.configuration.DisplayMode;
import de.gurkenlabs.litiengine.resources.Resources;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class TateRunnerGame implements IGame {
	private static final String THREAD_NAME = "TATE-THREAD-%d";
	private static final AtomicInteger THREAD_COUNTER = new AtomicInteger(0);

	public static float IMAGE_SCALE;
	public static int GAME_WIDTH, GAME_HEIGHT, WIDTH, HEIGHT;
	public static double GRAVITY;

	public static boolean DEBUG_MODE;
	private static TateRunnerGame INSTANCE;

	static {
		GAME_WIDTH = 1920;
		GAME_HEIGHT = 1080;

		final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		WIDTH = screenSize.width;
		HEIGHT = screenSize.height;
		IMAGE_SCALE = WIDTH / (float) GAME_WIDTH;

		GRAVITY = 2.3;
		DEBUG_MODE = false; // set to true to enable debug mode
		INSTANCE = null;
	}

	private final Map<Class<? extends Manager>, Manager> registeredManagers = new HashMap<>();

	public TateRunnerGame() {
		if (INSTANCE != null) throw new IllegalStateException("Only one instance can run at the time");
		INSTANCE = this;
	}

	public static TateRunnerGame getInstance() {
		if (INSTANCE == null) throw new IllegalStateException("Cannot access instance; instance might be null");
		return INSTANCE;
	}

	public static ScheduledExecutorService getThreadPoolExecutor() {
		return Executors.newScheduledThreadPool(0, r ->
				new Thread(r, String.format(THREAD_NAME, THREAD_COUNTER.getAndIncrement())));
	}

	@Override
	public void startGame(String... args) {
		// Set up the game information
		Game.info().setName("Tate Runner");
		Game.info().setSubTitle("");
		Game.info().setDevelopers("Elmar", "Tomas", "Nino");
		Game.info().setVersion("v0.0.1");

		// Set up the game configuration
		Game.config().graphics().setDisplayMode(DisplayMode.BORDERLESS);

		// Initialize the Game
		Game.init(args);

		// Set the default window resolution and window icon
		Game.window().setIcon(Resources.images().get("icon/andrew_tate_icon.png"));

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

		Logger.log("Successfully started the game");
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
				new ProfileManager(this),
				new EnemyManager(this),
				new StateManager(this),
				new ScreenManager(this),
				new KeystrokeManager(this),
				new MouseManager(this)
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
