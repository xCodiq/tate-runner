package com.xcodiq.taterunner.screen.button.implementation;

import com.xcodiq.taterunner.screen.button.StaticButton;
import com.xcodiq.taterunner.util.image.ImageUtil;

public final class OptionsButton extends StaticButton {

	public OptionsButton(double x, double y) {
		super(
				ImageUtil.loadImage("textures/button/options/options-unfocussed.png", 138, 54),
				ImageUtil.loadImage("textures/button/options/options-focussed.png", 138, 54),
				x, y);
	}
}
