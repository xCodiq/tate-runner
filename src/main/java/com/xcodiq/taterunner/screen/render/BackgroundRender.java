package com.xcodiq.taterunner.screen.render;

public class BackgroundRender implements Render {

	private final int originalX, originalNextX;
	private int backgroundX, nextBackgroundX;

	public BackgroundRender(int backgroundX, int nextBackgroundX) {
		this.backgroundX = backgroundX;
		this.nextBackgroundX = nextBackgroundX;

		this.originalX = backgroundX;
		this.originalNextX = nextBackgroundX;
	}

	public int getBackgroundX() {
		return backgroundX;
	}

	public void setBackgroundX(int backgroundX) {
		this.backgroundX = backgroundX;
	}

	public void addBackgroundX(int backgroundX) {
		this.backgroundX += backgroundX;
	}

	public int getNextBackgroundX() {
		return nextBackgroundX;
	}

	public void setNextBackgroundX(int nextBackgroundX) {
		this.nextBackgroundX = nextBackgroundX;
	}

	public void addNextBackgroundX(int nextBackgroundX) {
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
}
