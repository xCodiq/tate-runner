package com.xcodiq.taterunner.screen.button.implementation;

import com.xcodiq.taterunner.screen.button.StaticButton;
import com.xcodiq.taterunner.util.image.ImageUtil;

public final class ExitButton extends StaticButton {

	public ExitButton(double x, double y) {
		super(
				ImageUtil.loadImage("textures/button/exit/exit-unfocussed.png", 138, 54),
				ImageUtil.loadImage("textures/button/exit/exit-focussed.png", 138, 54),
				x, y);

		this.setClickAction(button -> System.exit(0));
	}
}
