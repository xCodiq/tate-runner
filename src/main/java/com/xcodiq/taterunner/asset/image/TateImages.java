package com.xcodiq.taterunner.asset.image;

import com.xcodiq.taterunner.util.image.ImageUtil;

import java.awt.image.BufferedImage;

public enum TateImages implements TateImage {

	DARK_FOREST_BACKGROUND("textures/background/dark-forest.png"),
	LIGHT_FOREST_BACKGROUND("textures/background/light-forest.png"),
	GRAVEYARD_BACKGROUND("textures/background/graveyard.png"),
	MARKET_BACKGROUND("textures/background/market.png");

	private final String path;

	TateImages(String path) {
		this.path = path;
	}

	@Override
	public String getPath() {
		return path;
	}

	@Override
	public BufferedImage toImage(int width, int height) {
		return ImageUtil.loadImage(this.path, width, height);
	}

	@Override
	public BufferedImage toImage() {
		return ImageUtil.loadImage(this.path);
	}
}
