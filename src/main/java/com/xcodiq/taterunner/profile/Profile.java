package com.xcodiq.taterunner.profile;

import com.xcodiq.taterunner.asset.scene.TateScene;
import com.xcodiq.taterunner.asset.scene.TateScenes;
import com.xcodiq.taterunner.asset.sprite.TateSprites;

import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

public class Profile {

	private final String name;
	private final Set<TateSprites> unlockedTateSprites = new HashSet<>() {{
		this.add(TateSprites.KAKASHI);
	}};

	private TateScenes currentTateScene = TateScenes.DARK_FOREST;
	private TateSprites equippedTateSprite = TateSprites.KAKASHI;

	private int coins = 0;
	private int lastScore = 0, highScore = 0;

	public Profile(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public TateScene getCurrentTateScene() {
		return currentTateScene;
	}

	public void setCurrentTateScene(TateScenes currentTateScene) {
		this.currentTateScene = currentTateScene;
	}

	public BufferedImage getCurrentSceneImage() {
		return this.currentTateScene.toImage();
	}

	public TateSprites getEquippedTateSprite() {
		return equippedTateSprite;
	}

	public void setEquippedTateSprite(TateSprites equippedTateSprite) {
		this.equippedTateSprite = equippedTateSprite;
	}

	public Set<TateSprites> getUnlockedTateSprites() {
		return unlockedTateSprites;
	}

	public boolean ownsTateSprite(TateSprites tateSprite) {
		return this.equippedTateSprite == tateSprite || this.unlockedTateSprites.contains(tateSprite);
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

	public int getLastScore() {
		return lastScore;
	}

	public void setLastScore(int lastScore) {
		this.lastScore = lastScore;
	}

	public int getHighScore() {
		return highScore;
	}

	public void setHighScore(int highScore) {
		this.highScore = highScore;
	}
}
