package com.xcodiq.taterunner.util.image;

import com.xcodiq.taterunner.TateRunnerGame;
import de.gurkenlabs.litiengine.resources.Resources;
import de.gurkenlabs.litiengine.util.Imaging;

import java.awt.image.BufferedImage;

public final class ImageUtil {

	private ImageUtil() {
		// unused
	}

	public static BufferedImage loadImage(String image) {
		return Imaging.scale(Resources.images().get(image), TateRunnerGame.IMAGE_SCALE, true);
	}

	public static BufferedImage loadImage(String image, int width, int height) {
		return Imaging.scale(loadImage(image), width, height);
	}
}

