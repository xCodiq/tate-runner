package com.xcodiq.taterunner.asset.sound;

public enum TateSounds implements TateSound {

	MAIN_THEME("audio/main_theme.wav");

	private final String path;

	TateSounds(String path) {
		this.path = path;
	}

	@Override
	public String getPath() {
		return path;
	}
}
