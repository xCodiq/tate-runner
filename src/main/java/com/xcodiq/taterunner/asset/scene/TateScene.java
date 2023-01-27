package com.xcodiq.taterunner.asset.scene;

import com.xcodiq.taterunner.asset.image.TateImage;

import java.awt.image.BufferedImage;

public interface TateScene extends TateImage {

	int getFloorCoordinate();

	int getPrice();

	BufferedImage getIcon();
}
