package com.xcodiq.taterunner.asset.sound;

import com.xcodiq.taterunner.asset.Asset;
import de.gurkenlabs.litiengine.sound.Sound;

public interface TateSound extends Asset {

	Sound getSound();

	void playOnBackground();

	void play(boolean looped);

	default void play() {
		this.play(false);
	}
}
