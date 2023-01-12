package com.xcodiq.taterunner.screen.button.implementation;

import com.xcodiq.taterunner.screen.button.Button;
import com.xcodiq.taterunner.util.image.ImageUtil;

import java.awt.event.MouseEvent;
import java.util.function.Consumer;

public final class ReturnButton extends Button {

	public ReturnButton(double x, double y, Consumer<MouseEvent> clickConsumer) {
		super(
				ImageUtil.loadImage("textures/button/return/return-unfocussed.png", 138, 54),
				ImageUtil.loadImage("textures/button/return/return-focussed.png", 138, 54),
				x, y);

		this.setClickEvent(clickConsumer);
	}
}
