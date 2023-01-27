package com.xcodiq.taterunner;

import com.xcodiq.taterunner.manager.Manager;
import com.xcodiq.taterunner.screen.TateGameScreen;

public interface IGame {

	void startGame(String... args);

	void stopGame();

	void registerManagers();

	<T extends Manager> T getManager(Class<T> typeClass);

	void addScreen(TateGameScreen tateGameScreen);
}
