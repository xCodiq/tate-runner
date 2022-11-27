package com.xcodiq.taterunner.util.editor;

import com.xcodiq.taterunner.util.text.TextUtil;

import java.awt.*;

public interface TextEditor {

	void drawText(int x, int y, Font font, Color color, float size, String text);

	default void drawText(int x, int y, Color color, float size, String text) {
		drawText(x, y, TextUtil.DEFAULT_FONT, color, size, text);
	}

	void drawCenteredText(int xOffset, int yOffset, Font font, Color color, float size, String text);

	default void drawCenteredText(Font font, Color color, float size, String text) {
		this.drawCenteredText(0, 0, font, color, size, text);
	}

	default void drawCenteredText(int xOffset, int yOffset, Color color, float size, String text) {
		drawCenteredText(xOffset, yOffset, TextUtil.DEFAULT_FONT, color, size, text);
	}

	default void drawCenteredText(Color color, float size, String text) {
		this.drawCenteredText(0, 0, color, size, text);
	}

	void drawAnimatedText(int x, int y, int interval, Font font, Color color, float size, String... frames);

	default void drawAnimatedText(int x, int y, int interval, Color color, float size, String... frames) {
		drawAnimatedText(x, y, interval, TextUtil.DEFAULT_FONT, color, size, frames);
	}

	void drawCenteredAnimatedText(int xOffset, int yOffset, int interval, Font font, Color color, float size, String... frames);

	default void drawCenteredAnimatedText(int interval, Font font, Color color, float size, String... frames) {
		this.drawCenteredAnimatedText(0, 0, interval, font, color, size, frames);
	}

	default void drawCenteredAnimatedText(int xOffset, int yOffset, int interval, Color color, float size, String... frames) {
		this.drawCenteredAnimatedText(xOffset, yOffset, interval, TextUtil.DEFAULT_FONT, color, size, frames);
	}

	default void drawCenteredAnimatedText(int interval, Color color, float size, String... frames) {
		this.drawCenteredAnimatedText(0, 0, interval, color, size, frames);
	}
}
