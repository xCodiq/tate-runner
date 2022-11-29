package com.xcodiq.taterunner.entity.implementation;

import com.xcodiq.taterunner.entity.sprite.SpriteEntity;
import de.gurkenlabs.litiengine.resources.Resources;
import de.gurkenlabs.litiengine.util.Imaging;

public final class Rock extends SpriteEntity {

	public Rock(double startingX, double startingY) {
		super(Imaging.scale(Resources.images().get("sprites/rock-brown.png"), 60, 60, true),
				startingX, startingY);
	}
}
