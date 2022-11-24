package com.xcodiq.taterunner.screen;

import com.xcodiq.taterunner.util.text.TextEditor;
import com.xcodiq.taterunner.util.text.TextUtil;
import de.gurkenlabs.litiengine.gui.screens.GameScreen;

import java.awt.*;

public abstract class TateGameScreen extends GameScreen implements TextEditor {

	protected Graphics2D graphics;

	public TateGameScreen(String name) {
		super(name);
	}

	@Override
	public void render(Graphics2D graphics) {
		super.render(graphics);
		this.graphics = graphics;

		this.render();
	}

	public abstract void render();

	public Graphics2D getGraphics() {
		return graphics;
	}

	@Override
	public void drawText(int x, int y, Color color, float size, String text) {
		TextUtil.drawText(this.graphics, x, y, color, size, text);
	}

	@Override
	public void drawCenteredText(int xOffset, int yOffset, Color color, float size, String text) {
		TextUtil.drawCenteredText(this.graphics, (int) this.getWidth(), (int) this.getHeight(), xOffset, yOffset, color, size, text);
	}

	@Override
	public void drawAnimatedText(int x, int y, int interval, Color color, float size, String... frames) {
		TextUtil.drawAnimatedText(this.graphics, x, y, interval, color, size, frames);
	}

	@Override
	public void drawCenteredAnimatedText(int xOffset, int yOffset, int interval, Color color, float size, String... frames) {
		TextUtil.drawCenteredAnimatedText(this.graphics, (int) this.getWidth(), (int) this.getHeight(), xOffset, yOffset, interval, color, size, frames);
	}
}
