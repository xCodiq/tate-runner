package com.xcodiq.taterunner.util.image;

import com.xcodiq.taterunner.TateRunnerGame;
import de.gurkenlabs.litiengine.resources.Resources;
import de.gurkenlabs.litiengine.util.Imaging;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public final class ImageUtil {

	private static final Map<String, BufferedImage> RUNTIME_IMAGE_CACHE = new HashMap<>();

	private ImageUtil() {
		// unused
	}

	public static BufferedImage loadImage(String image) {
		return RUNTIME_IMAGE_CACHE.computeIfAbsent(image, key ->
				Imaging.scale(Resources.images().get(image), TateRunnerGame.IMAGE_SCALE, true));
	}

	public static BufferedImage loadImage(String image, int width, int height) {
		return Imaging.scale(loadImage(image), width, height,true);
	}
}

