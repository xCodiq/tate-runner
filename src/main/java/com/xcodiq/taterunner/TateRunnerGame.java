package com.xcodiq.taterunner;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.GameInfo;
import de.gurkenlabs.litiengine.gui.screens.Resolution;
import de.gurkenlabs.litiengine.resources.Resources;

public class TateRunnerGame implements IGame {

	private static TateRunnerGame instance = null;

	public TateRunnerGame() {
		if (instance != null) throw new IllegalStateException("Only one instance can run at the time");
		instance = this;
	}

	public static TateRunnerGame getInstance() {
		if (instance == null) throw new IllegalStateException("Cannot access instance; instance might be null");
		return instance;
	}

	@Override
	public void startGame(String... args) {
		// Set up the game information
		final GameInfo info = Game.info();
		info.setName("Tate Runner");
		info.setSubTitle("");
		info.setVersion("v0.0.1");

		// Initialize the Game
		Game.init(args);

		// Set the default window resolution and window icon
		Game.window().setResolution(Resolution.custom(960, 540, "default"));
		Game.window().setIcon(Resources.images().get("icon/andrew_tate_icon.jpg"));

		// Start the actual game
		Game.start();
	}

	@Override
	public void stopGame() {

	}
}
