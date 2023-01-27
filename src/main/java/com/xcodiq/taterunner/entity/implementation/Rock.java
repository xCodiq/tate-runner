package com.xcodiq.taterunner.entity.implementation;

import com.xcodiq.taterunner.entity.bound.annotation.BoundContext;
import com.xcodiq.taterunner.entity.bound.type.BoundType;
import com.xcodiq.taterunner.entity.sprite.SpriteEntity;
import com.xcodiq.taterunner.util.image.ImageUtil;

@BoundContext(
		boundType = BoundType.POLYGON,
		boundPath = "sprites/rock/brown-rock.bounds")
public final class Rock extends SpriteEntity {

	public Rock(double startingX, double startingY, int width, int height) {
		super(ImageUtil.loadImage("sprites/rock/brown-rock.png", width, height), startingX, startingY);
	}
}
