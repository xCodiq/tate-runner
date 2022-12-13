package com.xcodiq.taterunner.screen.button.implementation;

import com.xcodiq.taterunner.screen.button.Button;
import com.xcodiq.taterunner.util.image.ImageUtil;

public final class OptionsButton extends Button {

	public OptionsButton(double x, double y) {
		super(ImageUtil.loadImage("textures/button/options/options2-unfocussed.png"),
				ImageUtil.loadImage("textures/button/options/options2-focussed.png"),
				x, y);

		this.setClickEvent(mouseEvent -> {
			System.exit(0);
		});
	}
}
