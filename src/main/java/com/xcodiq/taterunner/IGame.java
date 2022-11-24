package com.xcodiq.taterunner;

import com.xcodiq.taterunner.manager.Manager;
import de.gurkenlabs.litiengine.gui.screens.GameScreen;

public interface IGame {

	void startGame(String... args);

	void stopGame();

	void registerManagers();

	<T extends Manager> T getManager(Class<T> typeClass);

	void addScreen(GameScreen gameScreen);
}
