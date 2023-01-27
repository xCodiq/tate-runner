package com.xcodiq.taterunner.screen.button.implementation;

import com.xcodiq.taterunner.screen.button.StaticButton;
import com.xcodiq.taterunner.screen.button.model.Button;
import com.xcodiq.taterunner.util.image.ImageUtil;

import java.util.function.Consumer;

public final class ReturnButton extends StaticButton {

	public ReturnButton(double x, double y, Consumer<Button> clickActionConsumer) {
		super(
				ImageUtil.loadImage("textures/button/return/return-unfocussed.png", 138, 54),
				ImageUtil.loadImage("textures/button/return/return-focussed.png", 138, 54),
				x, y);

		this.setClickAction(clickActionConsumer);
	}
}
