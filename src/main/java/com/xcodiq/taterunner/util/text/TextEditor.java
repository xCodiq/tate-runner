package com.xcodiq.taterunner.util.text;

import java.awt.*;

public interface TextEditor {

	void drawText(int x, int y, Color color, float size, String text);

	void drawCenteredText(int xOffset, int yOffset, Color color, float size, String text);

	default void drawCenteredText(Color color, float size, String text) {
		this.drawCenteredText(0, 0, color, size, text);
	}

	void drawAnimatedText(int x, int y, int interval, Color color, float size, String... frames);

	void drawCenteredAnimatedText(int xOffset, int yOffset, int interval, Color color, float size, String... frames);

	default void drawCenteredAnimatedText(int interval, Color color, float size, String... frames) {
		this.drawCenteredAnimatedText(0, 0, interval, color, size, frames);
	}
}
