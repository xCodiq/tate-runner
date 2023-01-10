package com.xcodiq.taterunner.screen.button.implementation;

import com.xcodiq.taterunner.screen.button.Button;
import com.xcodiq.taterunner.util.image.ImageUtil;

public final class ExitButton extends Button {

	public ExitButton(double x, double y) {
		super(
				ImageUtil.loadImage("textures/button/exit/exit-unfocussed.png", 115, 45),
				ImageUtil.loadImage("textures/button/exit/exit-focussed.png", 115, 45),
				x, y);

		this.setClickEvent(mouseEvent -> System.exit(0));
	}
}
