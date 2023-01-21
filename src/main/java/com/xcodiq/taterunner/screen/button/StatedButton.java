package com.xcodiq.taterunner.screen.button;

import com.xcodiq.taterunner.TateRunnerGame;
import com.xcodiq.taterunner.screen.TateGameScreen;
import com.xcodiq.taterunner.screen.button.model.Button;
import com.xcodiq.taterunner.screen.button.model.ButtonState;
import com.xcodiq.taterunner.util.multiple.Pair;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class StatedButton implements com.xcodiq.taterunner.screen.button.model.Button {

	private final List<ButtonState> buttonStateList = new ArrayList<>();
	private final Rectangle rectangle;

	private final double x, y;
	private final int width, height;

	private Runnable clickAction;
	private Predicate<Void> clickCondition = (v) -> true;
	private ButtonState currentState;

	private boolean visible = true, focused = false;
	private Function<Void, ButtonState> buttonRenderCondition = (v) -> buttonStateList.get(0);

	public StatedButton(ButtonState[] buttonStates, double x, double y, int width, int height) {
		this.buttonStateList.addAll(List.of(buttonStates));
		this.currentState = buttonStates[0];

		this.width = width;
		this.height = height;

		this.x = x * TateRunnerGame.IMAGE_SCALE;
		this.y = y * TateRunnerGame.IMAGE_SCALE;

		this.rectangle = new Rectangle((int) this.x, (int) this.y, this.width, this.height);
	}

	@Override
	public void render(TateGameScreen tateGameScreen, boolean shouldRender) {
		if (!this.visible || !shouldRender || this.currentState == null) return;

		final Pair<BufferedImage, BufferedImage> imagesPair = this.currentState.getImages();
		tateGameScreen.drawStaticImage(this.x, this.y, this.focused
				? imagesPair.getSecond() : imagesPair.getFirst());

		this.currentState = this.buttonRenderCondition.apply(null);
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
		return this.rectangle;
	}

	@Override
	public ButtonState getCurrentState() {
		return currentState;
	}

	@Override
	public void setCurrentState(ButtonState currentState) {
		this.currentState = currentState;
	}

	@Override
	public boolean isCurrentState(ButtonState buttonState) {
		return this.currentState.getName().equals(buttonState.getName());
	}

	public void setButtonRenderCondition(Function<Void, ButtonState> buttonRenderCondition) {
		this.buttonRenderCondition = buttonRenderCondition;
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
}
