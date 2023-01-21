package com.xcodiq.taterunner.asset.scene;

import com.xcodiq.taterunner.asset.image.TateImage;
import com.xcodiq.taterunner.asset.image.TateImages;
import de.gurkenlabs.litiengine.util.Imaging;

import java.awt.image.BufferedImage;

public enum TateScenes implements TateScene {

	DARK_FOREST(TateImages.DARK_FOREST_BACKGROUND, 777, 100),
	LIGHT_FOREST(TateImages.LIGHT_FOREST_BACKGROUND, 0, 500),
	GRAVEYARD(TateImages.GRAVEYARD_BACKGROUND, 0, 1000);

	private final TateImage backgroundImage;
	private final int floorCoordinate;
	private final int price;

	TateScenes(TateImage backgroundImage, int floorCoordinate, int price) {
		this.backgroundImage = backgroundImage;
		this.floorCoordinate = floorCoordinate;
		this.price = price;
	}

	@Override
	public String getPath() {
		return this.backgroundImage.getPath();
	}

	@Override
	public int getFloorCoordinate() {
		return this.floorCoordinate;
	}

	@Override
	public int getPrice() {
		return price;
	}

	@Override
	public BufferedImage toImage(int width, int height) {
		return Imaging.scale(this.backgroundImage.toImage(), width, height);
	}

	@Override
	public BufferedImage toImage() {
		return this.backgroundImage.toImage();
	}
}
