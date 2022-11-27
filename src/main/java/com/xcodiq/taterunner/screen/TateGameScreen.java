package com.xcodiq.taterunner.screen;

import com.xcodiq.taterunner.TateRunnerGame;
import com.xcodiq.taterunner.keystroke.Keystroke;
import com.xcodiq.taterunner.util.editor.ImageEditor;
import com.xcodiq.taterunner.util.editor.TextEditor;
import com.xcodiq.taterunner.util.text.TextUtil;
import de.gurkenlabs.litiengine.graphics.ImageRenderer;
import de.gurkenlabs.litiengine.gui.screens.GameScreen;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

public abstract class TateGameScreen extends GameScreen implements TextEditor, ImageEditor {

	protected final TateRunnerGame tateRunner;

	private final Method keystrokeMethod;
	protected Graphics2D graphics;

	public TateGameScreen(TateRunnerGame tateRunner, String name) {
		super(name);
		this.tateRunner = tateRunner;

		// Look for a keystroke method if present
		this.keystrokeMethod = Arrays.stream(this.getClass().getMethods())
				.filter(method -> method.isAnnotationPresent(Keystroke.class))
				.findFirst().orElse(null);

		// Check if the keystroke method exists, and if it has a KeyEvent parameter
		if (this.keystrokeMethod != null && this.keystrokeMethod.getParameterCount() != 1 && this.keystrokeMethod.getParameterTypes()[0] != KeyEvent.class)
			throw new IllegalStateException("Keystroke method must have a KeyEvent parameter!");
	}

	@Override
	public void render(Graphics2D graphics) {
		// Set the graphics
		super.render(graphics);
		this.graphics = graphics;

		// Call the abstract render method
		this.render();
	}

	public abstract void render();

	public Graphics2D getGraphics() {
		return graphics;
	}

	public Method getKeystrokeMethod() {
		return keystrokeMethod;
	}

	@Override
	public void drawText(int x, int y, Font font, Color color, float size, String text) {
		TextUtil.drawText(this.graphics, x, y, font, color, size, text);
	}

	@Override
	public void drawCenteredText(int xOffset, int yOffset, Font font, Color color, float size, String text) {
		TextUtil.drawCenteredText(this.graphics, (int) this.getWidth(), (int) this.getHeight(),
				xOffset, yOffset, font, color, size, text);
	}

	@Override
	public void drawAnimatedText(int x, int y, int interval, Font font, Color color, float size, String... frames) {
		TextUtil.drawAnimatedText(this.graphics, x, y, interval, font, color, size, frames);
	}

	@Override
	public void drawCenteredAnimatedText(int xOffset, int yOffset, int interval, Font font, Color color, float size, String... frames) {
		TextUtil.drawCenteredAnimatedText(this.graphics, (int) this.getWidth(), (int) this.getHeight(),
				xOffset, yOffset, interval, font, color, size, frames);
	}

	@Override
	public void drawImage(int x, int y, Image image) {
		ImageRenderer.render(this.graphics, image, x, y);
	}

	public void drawBackgroundImage(Image image) {
		this.drawImage(0, 0, image);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		TateGameScreen that = (TateGameScreen) o;
		return Objects.equals(that.hashCode(), that.hashCode());
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.getClass());
	}
}
