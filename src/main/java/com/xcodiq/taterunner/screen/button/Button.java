package com.xcodiq.taterunner.screen.button;

import com.xcodiq.taterunner.screen.TateGameScreen;
import de.gurkenlabs.litiengine.graphics.ImageRenderer;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;

public class Button {

	private final Image unfocusedImage, focusedImage;
	private final int x, y, width, height;
	private final Rectangle rectangle;

	private boolean visible = true, focused = false;
	private Consumer<MouseEvent> clickConsumer;

	public Button(Image unfocusedImage, Image focusedImage, int x, int y, int width, int height) {
		this.unfocusedImage = unfocusedImage;
		this.focusedImage = focusedImage;

		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;

		this.rectangle = new Rectangle(x, y, width, height);
	}

	public void toggleVisibility(TateGameScreen tateGameScreen) {

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

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
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
