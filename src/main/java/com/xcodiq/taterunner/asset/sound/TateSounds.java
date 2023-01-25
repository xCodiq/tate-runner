package com.xcodiq.taterunner.asset.sound;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.resources.Resources;
import de.gurkenlabs.litiengine.sound.Sound;

import java.util.function.Predicate;

public enum TateSounds implements TateSound {

	MAIN_THEME("audio/main_theme.ogg"),
	JUMP("audio/jump.wav"),
	PLAYER_HURT("audio/player_hurt.wav"),
	PICKUP_COIN("audio/pickup_coin.wav"),
	BUTTON_CLICK("audio/button_click.wav"),
	EQUIP_ITEM("audio/equip_item.wav"),
	PURCHASE_ITEM("audio/purchase_item.wav"),
	INSUFFICIENT_FUNDS("audio/insufficient_funds.wav"),
	;

	private final String path;
	private transient Sound sound;

	TateSounds(String path) {
		this.path = path;
	}

	public static void resetSounds(Predicate<Void> stopCondition) {
		// Test if the condition succeeds
		if (stopCondition.test(null)) return;

		// Stop the music
		Game.audio().stopMusic();
	}

	public static void resetSounds() {
		TateSounds.resetSounds(voidValue -> false);
	}

	@Override
	public String getPath() {
		return path;
	}

	@Override
	public Sound getSound() {
		if (this.sound != null) return sound;

		this.sound = Resources.sounds().get(this.path);
		return this.sound;
	}

	@Override
	public void playOnBackground() {
		Game.audio().playMusic(this.getSound());
	}

	@Override
	public void play(boolean looped) {
		Game.audio().playSound(this.getSound(), looped, Game.audio().getMaxDistance(), 1.8F);
	}
}
