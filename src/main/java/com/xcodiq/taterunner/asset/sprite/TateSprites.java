package com.xcodiq.taterunner.asset.sprite;

import com.xcodiq.taterunner.util.animation.ImageAnimation;
import com.xcodiq.taterunner.util.image.ImageUtil;

import java.awt.image.BufferedImage;
import java.util.Locale;

public enum TateSprites implements TateSprite {
	KAKASHI("", new TateSpriteRender("sprites/kakashi/kakashi.bounds",
			new ImageAnimation("kakashi", 24, 100, 200, 200)), 100),
	OLIVER("", new TateSpriteRender("sprites/oliver/oliver.bounds",
			new ImageAnimation("oliver", 8, 75, 195, 167)), 500),
	ROSCOE("", new TateSpriteRender("sprites/roscoe/roscoe.bounds",
			new ImageAnimation("roscoe", 10, 75, 182, 173)), 1000),
	;

	private final String path;
	private final TateSpriteRender render;
	private final int price;

	TateSprites(String path, TateSpriteRender render, int price) {
		this.path = path;
		this.render = render;
		this.price = price;
	}

	@Override
	@Deprecated
	public String getPath() {
		throw new RuntimeException("This method is deprecated!");
	}

	@Override
	public BufferedImage toImage(int width, int height) {
		return ImageUtil.loadImage(this.path, width, height);
	}

	@Override
	public BufferedImage toImage() {
		return ImageUtil.loadImage(this.path);
	}

	@Override
	public TateSpriteRender getRender() {
		return render;
	}

	@Override
	public int getPrice() {
		return price;
	}

	@Override
	public String toString() {
		return this.name().toUpperCase(Locale.ROOT);
	}
}
