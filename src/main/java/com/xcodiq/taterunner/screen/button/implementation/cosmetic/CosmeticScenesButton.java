package com.xcodiq.taterunner.screen.button.implementation.cosmetic;

import com.xcodiq.taterunner.screen.button.Button;
import com.xcodiq.taterunner.util.image.ImageUtil;

import java.awt.event.MouseEvent;
import java.util.function.Consumer;
import java.util.function.Predicate;

public final class CosmeticScenesButton extends Button {

	public CosmeticScenesButton(double x, double y, Consumer<MouseEvent> clickConsumer, Predicate<Void> clickCondition) {
		super(ImageUtil.loadImage("textures/button/cosmeticshop/scenes/scenes-cosmetic-unfocussed.png"),
				ImageUtil.loadImage("textures/button/cosmeticshop/scenes/scenes-cosmetic-focussed.png"),
				x, y);

		this.setClickEvent(clickConsumer);
		this.setClickCondition(clickCondition);
	}
}
