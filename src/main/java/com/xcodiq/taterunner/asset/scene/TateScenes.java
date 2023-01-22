package com.xcodiq.taterunner.asset.scene;

import com.xcodiq.taterunner.TateRunnerGame;
import com.xcodiq.taterunner.asset.image.TateImage;
import com.xcodiq.taterunner.asset.image.TateImages;
import de.gurkenlabs.litiengine.util.Imaging;

import java.awt.image.BufferedImage;
import java.util.Locale;

public enum TateScenes implements TateScene {

	DARK_FOREST(TateImages.DARK_FOREST_BACKGROUND, 777, 100),
	LIGHT_FOREST(TateImages.LIGHT_FOREST_BACKGROUND, 833, 500),
	GRAVEYARD(TateImages.GRAVEYARD_BACKGROUND, 0, 1000);

	private final TateImage backgroundImage;
	private final int floorCoordinate;
	private final int price;

	private transient BufferedImage icon;

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
	public BufferedImage getIcon() {
		if (icon != null) return icon;

		final int iconSize = (int) (1000 * TateRunnerGame.IMAGE_SCALE);
		final BufferedImage image = this.toImage();

		return icon = Imaging.scale(image.getSubimage(
				(int) ((image.getWidth() - iconSize) * TateRunnerGame.IMAGE_SCALE), 0,
				iconSize, iconSize), 220, 220);
	}

	@Override
	public BufferedImage toImage(int width, int height) {
		return Imaging.scale(this.backgroundImage.toImage(), width, height);
	}

	@Override
	public BufferedImage toImage() {
		return this.backgroundImage.toImage();
	}

	public String toString() {
		return this.name().toUpperCase(Locale.ROOT).replace("_", " ");
	}
}
