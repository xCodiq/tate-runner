package com.xcodiq.taterunner.util.text;

import com.xcodiq.taterunner.asset.font.TateFont;
import com.xcodiq.taterunner.asset.font.TateFonts;
import com.xcodiq.taterunner.util.multiple.Pair;
import de.gurkenlabs.litiengine.graphics.TextRenderer;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public final class TextUtil {

	public static final Font DEFAULT_FONT;

	private static final Map<Integer, Pair<AnimatedText, Integer>> currentAnimatedTextIndexes = new HashMap<>();
	private static final Map<Integer, Long> currentTimings = new HashMap<>();

	static {
		DEFAULT_FONT = TateFonts.PRIMARY_TITLE.toFont().deriveFont(20f);
	}

	private TextUtil() {
		// unused
	}

	public static void drawText(Graphics2D graphics, int x, int y, Font font, Color color, float size, String text) {
		graphics.setFont(font.deriveFont(size));

		graphics.setColor(color);
		TextRenderer.render(graphics, text, x, y);
	}

	public static void drawText(Graphics2D graphics, int x, int y, Color color, float size, String text) {
		drawText(graphics, x, y, DEFAULT_FONT, color, size, text);
	}

	public static void drawCenteredText(Graphics2D graphics, int width, int height, int xOffset, int yOffset, Font font, Color color, float size, String text) {
		graphics.setFont(font.deriveFont(size));
		graphics.setColor(color);

		final FontMetrics metrics = graphics.getFontMetrics(graphics.getFont());
		final int textWidth = metrics.stringWidth(text);

		int x = (width / 2) - (textWidth / 2);
		int y = height / 2;

		TextRenderer.render(graphics, text, x + xOffset, y + yOffset);
	}

	public static void drawCenteredText(Graphics2D graphics, int width, int height, Color color, float size, String text) {
		drawCenteredText(graphics, width, height, 0, 0, DEFAULT_FONT.deriveFont(size), color, size, text);
	}

	public static void drawCenteredAnimatedText(Graphics2D graphics, int width, int height, int interval, Font font, Color color, float size, String... frames) {
		drawCenteredAnimatedText(graphics, width, height, interval, 0, 0, font, color, size, frames);
	}

	public static void drawCenteredAnimatedText(Graphics2D graphics, int width, int height, int xOffset, int yOffset, int interval, Font font, Color color, float size, String... frames) {
		if (graphics == null || frames.length < 1) return;

		final AnimatedText animatedText = new AnimatedText(xOffset, yOffset, frames);
		final int hash = animatedText.hashCode();

		final Pair<AnimatedText, Integer> animatedPair = currentAnimatedTextIndexes.computeIfAbsent(hash, at -> new Pair<>(animatedText, 0));

		for (int i = 0; i < frames.length; i++) {
			if (animatedPair.getSecond() == i) {
				drawCenteredText(graphics, width, height, xOffset, yOffset, font.deriveFont(size), color, size, animatedText.getFrame(i));
			}
		}

		updateAnimatedText(interval, animatedText, hash, animatedPair, frames);
	}

	public static void drawAnimatedText(Graphics2D graphics, int x, int y, int interval, Font font, Color color, float size, String... frames) {
		if (graphics == null || frames.length < 1) return;

		final AnimatedText animatedText = new AnimatedText(x, y, frames);
		final int hash = animatedText.hashCode();

		final Pair<AnimatedText, Integer> animatedPair = currentAnimatedTextIndexes.computeIfAbsent(hash, at -> new Pair<>(animatedText, 0));

		graphics.setFont(font.deriveFont(size));
		graphics.setColor(color);

		for (int i = 0; i < frames.length; i++) {
			if (animatedPair.getSecond() == i) {
				TextRenderer.render(graphics, animatedText.getFrame(i), x, y);
			}
		}

		updateAnimatedText(interval, animatedText, hash, animatedPair, frames);
	}

	private static void updateAnimatedText(int interval, AnimatedText animatedText, int hash, Pair<AnimatedText, Integer> animatedPair, String[] frames) {
		if (System.currentTimeMillis() - currentTimings.computeIfAbsent(hash, h -> System.currentTimeMillis()) > interval) {
			int count = animatedPair.getSecond();
			count++;

			if (count == frames.length) count = 0;

			currentAnimatedTextIndexes.put(hash, new Pair<>(animatedText, count));
			currentTimings.put(hash, System.currentTimeMillis());
		}
	}

	public static void clearCache() {
		currentAnimatedTextIndexes.clear();
		currentTimings.clear();
	}

	public static int getStringWidth(Graphics2D graphics, Font font, String text) {
		final FontMetrics metrics = graphics.getFontMetrics(font);
		return metrics.stringWidth(text);
	}

	public static int getStringWidth(Graphics2D graphics, TateFont tateFont, float size, String text) {
		return getStringWidth(graphics, tateFont.toFont(size), text);
	}
}
