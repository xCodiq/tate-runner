package com.xcodiq.taterunner.profile;

import com.xcodiq.taterunner.screen.background.GameBackgrounds;

public class Profile {

	private final String name;
	private GameBackgrounds currentGameBackground = GameBackgrounds.DARK_FOREST;

	public Profile(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public GameBackgrounds getCurrentGameBackground() {
		return currentGameBackground;
	}

	public void setCurrentGameBackground(GameBackgrounds currentGameBackground) {
		this.currentGameBackground = currentGameBackground;
	}
}
