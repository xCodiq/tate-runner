package com.xcodiq.taterunner.screen.button;

import com.xcodiq.taterunner.TateRunnerGame;
import com.xcodiq.taterunner.screen.TateGameScreen;
import com.xcodiq.taterunner.screen.button.model.Button;
import com.xcodiq.taterunner.screen.button.model.ButtonState;
import com.xcodiq.taterunner.util.multiple.Pair;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class StaticButton implements Button {

	private final Rectangle rectangle;
	private final ButtonState buttonState;

	private final double x, y;
	private final int width, height;

	private Runnable clickAction;
	private Predicate<Void> clickCondition = (v) -> true;

	private boolean visible = true, focused = false;

	public StaticButton(BufferedImage unfocusedImage, BufferedImage focusedImage, double x, double y) {
		this.buttonState = new State(unfocusedImage, focusedImage);

		this.width = unfocusedImage.getWidth();
		this.height = unfocusedImage.getHeight();

		this.x = x * TateRunnerGame.IMAGE_SCALE;
		this.y = y * TateRunnerGame.IMAGE_SCALE;

		this.rectangle = new Rectangle((int) this.x, (int) this.y, this.width, this.height);
	}

	@Override
	public void render(TateGameScreen tateGameScreen, boolean shouldRender) {
		if (!this.visible || !shouldRender || this.buttonState == null) return;

		tateGameScreen.drawStaticImage(this.x, this.y, this.focused
				? this.buttonState.getFocusedImage() : this.buttonState.getUnfocusedImage());
	}

	@Override
	public boolean isFocused() {
		return focused;
	}

	@Override
	public void setFocused(boolean focused) {
		this.focused = focused;
	}

	@Override
	public Rectangle getRectangle() {
		return rectangle;
	}

	@Override
	public ButtonState getCurrentState() {
		return this.buttonState;
	}

	@Override
	public void setCurrentState(ButtonState buttonState) {
		throw new IllegalStateException("This button does not support changing states.");
	}

	@Override
	public boolean isCurrentState(ButtonState buttonState) {
		return this.buttonState.getName().equals(buttonState.getName());
	}

	@Override
	public Runnable getClickAction() {
		return clickAction;
	}

	@Override
	public void setClickAction(Consumer<Button> clickActionConsumer) {
		this.clickAction = () -> clickActionConsumer.accept(this);
	}

	@Override
	public Predicate<Void> getClickCondition() {
		return clickCondition;
	}

	@Override
	public void setClickCondition(Predicate<Void> clickCondition) {
		this.clickCondition = clickCondition;
	}

	@Override
	public boolean canClick() {
		return this.clickCondition == null || this.clickCondition.test(null);
	}

	private static class State implements ButtonState {

		private final Pair<BufferedImage, BufferedImage> images;

		public State(BufferedImage unfocusedImage, BufferedImage focusedImage) {
			this.images = new Pair<>(unfocusedImage, focusedImage);
		}

		@Override
		public String getName() {
			return this.getClass().getSimpleName();
		}

		@Override
		public Pair<BufferedImage, BufferedImage> getImages() {
			return this.images;
		}
	}
}
