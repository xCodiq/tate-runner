package com.xcodiq.taterunner.asset.font;

import de.gurkenlabs.litiengine.resources.Resources;

import java.awt.*;

public enum TateFonts implements TateFont {

	PRIMARY_TITLE("font/primary-title.ttf", 120f),
	SECONDARY_SUBTITLE("font/secondary-subtitle.ttf", 25f),
	;

	private final String path;
	private final float defaultFontSize;

	TateFonts(String path, float defaultFontSize) {
		this.path = path;
		this.defaultFontSize = defaultFontSize;
	}

	@Override
	public String getPath() {
		return path;
	}

	@Override
	public float getDefaultFontSize() {
		return defaultFontSize;
	}

	@Override
	public Font toFont() {
		return Resources.fonts().get(this.path, this.defaultFontSize);
	}
}
