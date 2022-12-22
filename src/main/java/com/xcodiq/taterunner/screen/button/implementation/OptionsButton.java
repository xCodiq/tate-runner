package com.xcodiq.taterunner.screen.button.implementation;

import com.xcodiq.taterunner.screen.button.Button;
import com.xcodiq.taterunner.util.image.ImageUtil;

public final class OptionsButton extends Button {

	public OptionsButton(double x, double y) {
		super(
				ImageUtil.loadImage("textures/button/options/options-gear-unfocussed.png", 50, 50),
				ImageUtil.loadImage("textures/button/options/options-gear-focussed.png", 50, 50),
				x, y);

		this.setClickEvent(mouseEvent -> {
		});
	}
}
