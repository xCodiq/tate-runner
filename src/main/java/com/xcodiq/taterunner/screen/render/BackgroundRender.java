package com.xcodiq.taterunner.screen.render;

public class BackgroundRender implements Render {

	private final int originalX, originalNextX;
	private double backgroundX, nextBackgroundX;

	public BackgroundRender(int backgroundX, int nextBackgroundX) {
		this.backgroundX = backgroundX;
		this.nextBackgroundX = nextBackgroundX;

		this.originalX = backgroundX;
		this.originalNextX = nextBackgroundX;
	}

	public double getBackgroundX() {
		return backgroundX;
	}

	public void setBackgroundX(double backgroundX) {
		this.backgroundX = backgroundX;
	}

	public void addBackgroundX(double backgroundX) {
		this.backgroundX += backgroundX;
	}

	public double getNextBackgroundX() {
		return nextBackgroundX;
	}

	public void setNextBackgroundX(double nextBackgroundX) {
		this.nextBackgroundX = nextBackgroundX;
	}

	public void addNextBackgroundX(double nextBackgroundX) {
		this.nextBackgroundX += nextBackgroundX;
	}

	public int getOriginalX() {
		return originalX;
	}

	public int getOriginalNextX() {
		return originalNextX;
	}

	public void reset() {
		this.backgroundX = this.originalX;
		this.nextBackgroundX = this.originalNextX;
	}

	public void adjust(double newX) {
		this.addBackgroundX(newX);
		this.addNextBackgroundX(newX);
	}
}
