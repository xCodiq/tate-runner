package com.xcodiq.taterunner.asset.image;

import com.xcodiq.taterunner.asset.Asset;

import java.awt.image.BufferedImage;

public interface TateImage extends Asset {

	BufferedImage toImage(int width, int height);
	BufferedImage toImage();
}
