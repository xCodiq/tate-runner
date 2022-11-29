package com.xcodiq.taterunner.entity;

import com.xcodiq.taterunner.screen.TateGameScreen;

import java.awt.*;

public abstract class Entity {

	protected final Rectangle boundingBox;
	protected final double startingX, startingY;

	protected double x, y;

	public Entity(double startingX, double startingY, int width, int height) {
		this.startingX = startingX;
		this.startingY = startingY;

		this.x = startingX;
		this.y = startingY;

		this.boundingBox = new Rectangle((int) this.x + 10, (int) this.x + 10,
				width - 20, height - 20);
	}

	public Rectangle getBoundingBox() {
		return boundingBox;
	}

	public double getStartingX() {
		return startingX;
	}

	public double getStartingY() {
		return startingY;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public boolean collidesWith(Entity entity) {
		return this.boundingBox.intersects(entity.getBoundingBox());
	}

	public void update(double x, double y) {
		this.x += x;
		if (y != 0.0) this.y += y;

		this.updateBoundingBox();
	}

	public void update(double x) {
		this.update(x, 0.0);
	}

	public void reset() {
		this.x = this.startingX;
		this.y = this.startingY;

		this.updateBoundingBox();
	}

	public void updateBoundingBox() {
		this.boundingBox.setLocation((int) this.x + 10, (int) this.y + 10);
	}

	public abstract void render(TateGameScreen tateGameScreen);
}
