package com.xcodiq.taterunner.screen.button.model;

import com.xcodiq.taterunner.util.multiple.Pair;

import java.awt.image.BufferedImage;

public interface ButtonState {

	String getName();

	// first=unfocussed, second=focussed
	Pair<BufferedImage, BufferedImage> getImages();

	default BufferedImage getUnfocusedImage() {
		return this.getImages().getFirst();
	}

	default BufferedImage getFocusedImage() {
		return this.getImages().getSecond();
	}
}
