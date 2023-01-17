package com.xcodiq.taterunner.profile;

import com.xcodiq.taterunner.screen.background.GameBackgrounds;

import java.awt.image.BufferedImage;

public class Profile {

	private final String name;
	private GameBackgrounds currentGameBackground = GameBackgrounds.DARK_FOREST;

	private int coins = 0;

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

	public BufferedImage getCurrentGameBackgroundImage() {
		return this.currentGameBackground.getBackgroundImage().toImage();
	}

	public int getCoins() {
		return coins;
	}

	public void setCoins(int coins) {
		this.coins = coins;
	}

	public void addCoins(int coins) {
		this.coins += coins;
	}

	public void removeCoins(int coins) {
		this.coins -= coins;
	}

	public boolean hasCoins(int coins) {
		return this.coins >= coins;
	}
}
