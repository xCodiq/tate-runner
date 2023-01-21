package com.xcodiq.taterunner.screen;

import com.xcodiq.taterunner.TateRunnerGame;
import com.xcodiq.taterunner.asset.font.TateFont;
import com.xcodiq.taterunner.screen.button.model.Button;
import com.xcodiq.taterunner.screen.keystroke.Keystroke;
import com.xcodiq.taterunner.util.editor.ImageEditor;
import com.xcodiq.taterunner.util.editor.TextEditor;
import com.xcodiq.taterunner.util.text.TextUtil;
import de.gurkenlabs.litiengine.graphics.ImageRenderer;
import de.gurkenlabs.litiengine.graphics.ShapeRenderer;
import de.gurkenlabs.litiengine.gui.screens.GameScreen;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.lang.reflect.Method;
import java.util.*;

public abstract class TateGameScreen extends GameScreen implements TextEditor, ImageEditor {

	protected final TateRunnerGame tateRunner;

	private final Map<Class<? extends Button>, Collection<Button>> buttonsMap = new HashMap<>();
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

	public void preShow() {
	}

	public Graphics2D getGraphics() {
		return graphics;
	}

	public Method getKeystrokeMethod() {
		return keystrokeMethod;
	}

	@Override
	public void drawText(int x, int y, Font font, Color color, float size, String text) {
		TextUtil.drawText(this.graphics,
				(int) (x * TateRunnerGame.IMAGE_SCALE), (int) (y * TateRunnerGame.IMAGE_SCALE),
				font, color, size * TateRunnerGame.IMAGE_SCALE, text);
	}

	@Override
	public void drawCenteredText(int xOffset, int yOffset, Font font, Color color, float size, String text) {
		TextUtil.drawCenteredText(this.graphics, TateRunnerGame.WIDTH, TateRunnerGame.HEIGHT,
				(int) (xOffset * TateRunnerGame.IMAGE_SCALE), (int) (yOffset * TateRunnerGame.IMAGE_SCALE),
				font, color, size * TateRunnerGame.IMAGE_SCALE, text);
	}

	@Override
	public void drawAnimatedText(int x, int y, int interval, Font font, Color color, float size, String... frames) {
		TextUtil.drawAnimatedText(this.graphics,
				(int) (x * TateRunnerGame.IMAGE_SCALE), (int) (y * TateRunnerGame.IMAGE_SCALE),
				interval, font, color, size * TateRunnerGame.IMAGE_SCALE, frames);
	}

	@Override
	public void drawCenteredAnimatedText(int xOffset, int yOffset, int interval, Font font, Color color, float size, String... frames) {
		TextUtil.drawCenteredAnimatedText(this.graphics, TateRunnerGame.WIDTH, TateRunnerGame.HEIGHT,
				xOffset, yOffset, interval, font, color, size * TateRunnerGame.IMAGE_SCALE, frames);
	}

	@Override
	public void drawImage(double x, double y, BufferedImage image) {
		ImageRenderer.render(this.graphics, image,
				x * TateRunnerGame.IMAGE_SCALE - image.getWidth(),
				y * TateRunnerGame.IMAGE_SCALE - image.getHeight());
	}

	public void drawStaticImage(double x, double y, BufferedImage image) {
		ImageRenderer.render(this.graphics, image, x, y);
	}

	public void drawRectangle(double x, double y, Rectangle rectangle, Color color) {
		this.graphics.setColor(color);
		ShapeRenderer.render(this.graphics,
				new Rectangle(
						(int) (rectangle.getWidth() * TateRunnerGame.IMAGE_SCALE),
						(int) (rectangle.getHeight() * TateRunnerGame.IMAGE_SCALE)),
				(int) (x * TateRunnerGame.IMAGE_SCALE),
				(int) (y * TateRunnerGame.IMAGE_SCALE));
	}

	public void renderButton(Class<? extends Button> buttonClass) {
		final Collection<Button> buttons = this.buttonsMap.get(buttonClass);
		buttons.stream().filter(Objects::nonNull).forEach(button -> button.render(this));
	}

	public void renderAllButtons() {
		this.getButtons().forEach(button -> button.render(this));
	}

	public void drawBackgroundImage(BufferedImage image, double x) {
		ImageRenderer.render(this.graphics, image, x, 0);
	}

	public void drawBackgroundImage(BufferedImage image) {
		this.drawBackgroundImage(image, 0);
	}

	public void drawFullscreenCover(Color color) {
		this.graphics.setColor(color);
		ShapeRenderer.render(this.graphics, new Rectangle(1920, 1080), 0, 0);
	}

	public Collection<Button> getButtons() {
		return buttonsMap.values().stream().flatMap(Collection::stream).toList();

	}

	public void addButton(Button button) {
		final Collection<Button> buttons = this.getButtonCollection(button.getClass());
		buttons.add(button);

		this.buttonsMap.put(button.getClass(), buttons);
	}

	public int getStringWidth(String text, TateFont tateFont, float size) {
		return TextUtil.getStringWidth(this.graphics, tateFont, size, text);
	}

	private <T extends Button> Collection<Button> getButtonCollection(Class<T> buttonClass) {
		return this.buttonsMap.computeIfAbsent(buttonClass, key -> new ArrayList<>());
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