package com.xcodiq.taterunner.util.text;

import java.awt.*;

public final class ScreenText {

	private final Color color;
	private final String text;

	public ScreenText(Color color, String text) {
		this.color = color;
		this.text = text;
	}

	public Color getColor() {
		return color;
	}

	public String getText() {
		return text;
	}
}
