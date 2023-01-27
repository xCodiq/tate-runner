package com.xcodiq.taterunner.util.animation;


import de.gurkenlabs.litiengine.resources.Resources;
import de.gurkenlabs.litiengine.util.Imaging;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public final class ImageAnimation {

	private final double originalInterval;
	private final List<BufferedImage> frames = new ArrayList<>();
	private double interval;

	private long lastFrameChange = System.currentTimeMillis();
	private int currentFrame = 0;

	public ImageAnimation(String name, int frameCount, double interval, int width, int height) {
		this.originalInterval = interval;
		this.interval = interval;

		for (int i = 1; i <= frameCount; i++) {
			// Determine the image location
			final String folder = "sprites/" + name + "/";
			final String fileName = folder + name + "_frame" + i + ".png";

			// Get the image and add it to the frame
			final BufferedImage frameImage = Resources.images().get(fileName);
			this.frames.add(Imaging.scale(frameImage, width, height));
		}
	}

	public BufferedImage getCurrentFrame() {
		// Check if the interval has passed
		if (System.currentTimeMillis() - this.lastFrameChange <= this.interval)
			return this.frames.get(this.currentFrame);

		// Get the next frame
		final BufferedImage image = this.frames.get(this.currentFrame++);
		if (this.currentFrame >= this.frames.size()) this.currentFrame = 0;

		// Update the last frame change and return the new frame
		this.lastFrameChange = System.currentTimeMillis();
		return image;
	}

	public void multiplyInterval(double multiplier) {
		this.interval *= multiplier;
	}

	public int getWidth() {
		return this.getCurrentFrame().getWidth();
	}

	public int getHeight() {
		return this.getCurrentFrame().getHeight();
	}

	public void resetInterval() {
		this.interval = this.originalInterval;
	}
}
