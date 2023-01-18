package com.xcodiq.taterunner.asset.sprite;

import com.xcodiq.taterunner.util.image.ImageUtil;

import java.awt.image.BufferedImage;

public enum TateSprites implements TateSprite {
	KAKASHI(""),
	AMONGUS(""),
	OLIVER("")
	;

	private final String path;

	TateSprites(String path) {
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
