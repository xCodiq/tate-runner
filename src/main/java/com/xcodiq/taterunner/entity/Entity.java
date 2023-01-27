package com.xcodiq.taterunner.entity;

import com.xcodiq.taterunner.TateRunnerGame;
import com.xcodiq.taterunner.entity.bound.BoundingBox;
import com.xcodiq.taterunner.entity.bound.annotation.BoundContext;
import com.xcodiq.taterunner.entity.bound.point.BoundingPoint;
import com.xcodiq.taterunner.entity.bound.type.BoundType;
import com.xcodiq.taterunner.entity.bound.type.CircleBoundingBox;
import com.xcodiq.taterunner.entity.bound.type.PolygonBoundingBox;
import com.xcodiq.taterunner.entity.bound.type.RectangleBoundingBox;
import com.xcodiq.taterunner.logger.Logger;
import com.xcodiq.taterunner.screen.TateGameScreen;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public abstract class Entity {
	private static final int BOUNDING_BOX_MARGIN = 20;

	protected final double startingX, startingY;
	protected final int width, height;

	protected final BoundContext boundContext;
	protected double x, y;

	protected BoundingBox<?> boundingBox;

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
		this.updateBoundContext(this.boundContext.boundType(), this.boundContext.boundPath());

		// Update the bounding box for the first time to position it correctly
		this.updateBoundingBox();
	}

	protected void updateBoundContext(BoundType boundType, String boundPath) {
		this.boundingBox = switch (boundType) {
			case BOX ->
				// Create a rectangle bounding box
					new RectangleBoundingBox(width - BOUNDING_BOX_MARGIN, height - BOUNDING_BOX_MARGIN);
			case CIRCLE ->
				// Create a circle bounding box
					new CircleBoundingBox(width - BOUNDING_BOX_MARGIN, height - BOUNDING_BOX_MARGIN);
			case POLYGON -> {
				// Look for a bound file path
				if (boundPath.isEmpty()) throw new RuntimeException("Bound path not in BoundContext annotation!");

				try {
					// Get the bound file location
					final URI boundLocation = ClassLoader.getSystemResource(boundPath).toURI();

					// Read all the lines and map it to a list of bounding points
					final List<BoundingPoint> boundingPoints = Files.readAllLines(Path.of(boundLocation)).stream().map(line -> {
						// Split the line into two parts (x and y)
						final String[] split = line.split(";");

						// Map to a bounding point
						return new BoundingPoint(
								(int) (Float.parseFloat(split[0]) * this.width),
								(int) (Float.parseFloat(split[1]) * this.height));
					}).toList(); // collect to in list

					// Create a polygon bounding box
					yield new PolygonBoundingBox(boundingPoints);
				} catch (IOException | URISyntaxException e) {
					throw new RuntimeException(e);
				}
			}
		};
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
		if (this.boundingBox.getShape() instanceof Polygon) {
			this.boundingBox.update((int) Math.round(this.x), (int) Math.round(this.y));
		}
		else {
			this.boundingBox.update((int) this.x + 10, (int)this.y + 10);
		}
	}

	public abstract void render(TateGameScreen tateGameScreen);
}
