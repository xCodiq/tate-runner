package com.xcodiq.taterunner.entity;

import com.xcodiq.taterunner.TateRunnerGame;
import com.xcodiq.taterunner.entity.bound.BoundingBox;
import com.xcodiq.taterunner.entity.bound.annotation.BoundContext;
import com.xcodiq.taterunner.entity.bound.point.BoundingPoint;
import com.xcodiq.taterunner.entity.bound.type.CircleBoundingBox;
import com.xcodiq.taterunner.entity.bound.type.PolygonBoundingBox;
import com.xcodiq.taterunner.entity.bound.type.RectangleBoundingBox;
import com.xcodiq.taterunner.screen.TateGameScreen;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public abstract class Entity {

	protected final BoundingBox<?> boundingBox;

	protected final double startingX, startingY;
	protected final int width, height;

	protected final BoundContext boundContext;
	protected double x, y;

	public Entity(double startingX, double startingY, int width, int height) {
		// Set the entity width and height
		this.width = width;
		this.height = height;

		// Save the starting position (scale it for the current resolution)
		this.startingX = startingX * TateRunnerGame.IMAGE_SCALE - width;
		this.startingY = startingY * TateRunnerGame.IMAGE_SCALE - height;

		// Set the position of the entity
		this.x = this.startingX;
		this.y = this.startingY;

		// Get the entity context
		this.boundContext = this.getClass().getAnnotation(BoundContext.class);
		if (this.boundContext == null) throw new RuntimeException("BoundContext annotation not found on class!");

		// Determine the bounding box type
		this.boundingBox = switch (this.boundContext.boundType()) {
			case BOX -> new RectangleBoundingBox(width - 20, height - 20);
			case CIRCLE -> new CircleBoundingBox(width - 20, height - 20);
			case POLYGON -> {
				final String boundPath = this.boundContext.boundPath();
				if (boundPath.isEmpty()) throw new RuntimeException("Bound path not in BoundContext annotation!");

				try {
					final URI boundLocation = ClassLoader.getSystemResource(boundPath).toURI();

					final List<BoundingPoint> boundingPoints = Files.readAllLines(Path.of(boundLocation)).stream().map(line -> {
						final String[] split = line.split(",");
						return new BoundingPoint(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
					}).toList();

					yield new PolygonBoundingBox(boundingPoints);
				} catch (IOException | URISyntaxException e) {
					throw new RuntimeException(e);
				}
			}
		};

		// Update the bounding box for the first time to position it correctly
		this.updateBoundingBox();
	}

	public BoundingBox<?> getBoundingBox() {
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

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public boolean collidesWith(Entity entity) {
		return this.boundingBox.overlaps(entity.getBoundingBox());
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
		this.boundingBox.update(this.x + 10, this.y + 10);
	}

	public abstract void render(TateGameScreen tateGameScreen);
}
