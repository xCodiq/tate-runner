package com.xcodiq.taterunner.screen.button.model;

import com.xcodiq.taterunner.screen.TateGameScreen;

import java.awt.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

public interface Button {

	void render(TateGameScreen tateGameScreen, boolean shouldRender);

	default void render(TateGameScreen tateGameScreen) {
		this.render(tateGameScreen, true);
	}

	boolean isFocused();

	void setFocused(boolean focused);

	Rectangle getRectangle();

	ButtonState getCurrentState();

	void setCurrentState(ButtonState buttonState);

	boolean isCurrentState(ButtonState buttonState);

	Runnable getClickAction();

	void setClickAction(Consumer<Button> clickActionConsumer);

	Predicate<Void> getClickCondition();

	void setClickCondition(Predicate<Void> clickCondition);

	boolean canClick();

	boolean isVisible();

	void setVisible(Boolean visible);
}
