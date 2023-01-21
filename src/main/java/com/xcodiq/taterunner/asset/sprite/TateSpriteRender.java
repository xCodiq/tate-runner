package com.xcodiq.taterunner.asset.sprite;

import com.xcodiq.taterunner.util.animation.ImageAnimation;

import java.awt.image.BufferedImage;

public class TateSpriteRender {

	private final BufferedImage image;
	private ImageAnimation imageAnimation;

	public TateSpriteRender(BufferedImage image) {
		this.image = image;
	}

	public TateSpriteRender(ImageAnimation imageAnimation) {
		this.imageAnimation = imageAnimation;
		this.image = null;
	}

	public BufferedImage getImage() {
		if (imageAnimation != null) return imageAnimation.getCurrentFrame();
		if (image == null) throw new IllegalStateException("Sprite image is null!");
		return image;
	}

	public ImageAnimation getImageAnimation() {
		return imageAnimation;
	}

	public int getWidth() {
		return this.getImage().getWidth();
	}

	public int getHeight() {
		return this.getImage().getHeight();
	}
}
