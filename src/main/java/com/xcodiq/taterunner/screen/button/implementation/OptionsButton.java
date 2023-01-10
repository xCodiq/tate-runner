package com.xcodiq.taterunner.screen.button.implementation;

import com.xcodiq.taterunner.screen.button.Button;
import com.xcodiq.taterunner.util.image.ImageUtil;

public final class OptionsButton extends Button {

	public OptionsButton(double x, double y) {
		super(
				ImageUtil.loadImage("textures/button/options/options-unfocussed.png", 115, 45),
				ImageUtil.loadImage("textures/button/options/options-focussed.png", 115, 45),
				x, y);

		this.setClickEvent(mouseEvent -> {
		});
	}
}
