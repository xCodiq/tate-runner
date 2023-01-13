package com.xcodiq.taterunner.screen.button;

import com.xcodiq.taterunner.TateRunnerGame;
import com.xcodiq.taterunner.screen.TateGameScreen;
import de.gurkenlabs.litiengine.graphics.ImageRenderer;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.function.Consumer;

public class Button {

	private final BufferedImage unfocusedImage, focusedImage;
	private final Rectangle rectangle;

	private final double x, y;
	private final int width, height;

	private boolean visible = true, focused = false;
	private Consumer<MouseEvent> clickConsumer;

	public Button(BufferedImage unfocusedImage, BufferedImage focusedImage, double x, double y) {
		this.unfocusedImage = unfocusedImage;
		this.focusedImage = focusedImage;

		this.width = unfocusedImage.getWidth();
		this.height = unfocusedImage.getHeight();

		this.x = x * TateRunnerGame.IMAGE_SCALE;
		this.y = y * TateRunnerGame.IMAGE_SCALE;

		this.rectangle = new Rectangle((int) this.x, (int) this.y, this.width, this.height);
	}

	public void setClickEvent(Consumer<MouseEvent> clickConsumer) {
		this.clickConsumer = clickConsumer;
	}

	public Consumer<MouseEvent> getClickConsumer() {
		return this.clickConsumer;
	}

	public void render(TateGameScreen tateGameScreen, boolean shouldRender) {
		if (!this.visible || !shouldRender) return;

		ImageRenderer.render(tateGameScreen.getGraphics(), this.focused ? this.focusedImage : this.unfocusedImage,
				this.x, this.y);
	}

	public void render(TateGameScreen tateGameScreen) {
		this.render(tateGameScreen, true);
	}

	public Image getUnfocusedImage() {
		return unfocusedImage;
	}

	public Image getFocusedImage() {
		return focusedImage;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean isFocused() {
		return focused;
	}

	public void setFocused(boolean focused) {
		this.focused = focused;
	}

	public Rectangle getRectangle() {
		return rectangle;
	}
}
