package com.xcodiq.taterunner.entity.implementation;

import com.xcodiq.taterunner.entity.sprite.SpriteEntity;
import com.xcodiq.taterunner.util.image.ImageUtil;

public final class Rock extends SpriteEntity {

	public Rock(double startingX, double startingY) {
		super(ImageUtil.loadImage("sprites/rock-brown.png", 60, 60), startingX, startingY);
	}
}
