package com.xcodiq.taterunner.screen.background;

import com.xcodiq.taterunner.asset.image.TateImage;
import com.xcodiq.taterunner.asset.image.TateImages;

public enum GameBackgrounds implements GameBackground {

	DARK_FOREST(TateImages.DARK_FOREST_BACKGROUND, 777),
	LIGHT_FOREST(TateImages.LIGHT_FOREST_BACKGROUND, 0),
	GRAVEYARD(TateImages.GRAVEYARD_BACKGROUND, 0);

	private final TateImage backgroundImage;
	private final int floorCoordinate;

	GameBackgrounds(TateImage backgroundImage, int floorCoordinate) {
		this.backgroundImage = backgroundImage;
		this.floorCoordinate = floorCoordinate;
	}

	@Override
	public TateImage getBackgroundImage() {
		return this.backgroundImage;
	}

	@Override
	public int getFloorCoordinate() {
		return this.floorCoordinate;
	}
}
