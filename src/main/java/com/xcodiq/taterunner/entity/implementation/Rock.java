package com.xcodiq.taterunner.entity.implementation;

import com.xcodiq.taterunner.asset.image.TateImages;
import com.xcodiq.taterunner.entity.bound.annotation.BoundContext;
import com.xcodiq.taterunner.entity.bound.type.BoundType;
import com.xcodiq.taterunner.entity.sprite.SpriteEntity;

@BoundContext(
		boundType = BoundType.POLYGON,
		boundPath = "sprites/rock/rock.bounds")
public final class Rock extends SpriteEntity {

	public Rock(double startingX, double startingY, int width, int height) {
		super(TateImages.ROCK.toImage(width, height), startingX, startingY);
	}
}
