package com.xcodiq.taterunner.entity.bound;

import java.awt.*;
import java.util.function.Supplier;

public abstract class BoundingBox<S extends Shape> {
	private static final float POLYGON_TRANSLATION_MARGIN = -0.25f;

	private final S shape;
	private int x, y, lastX = x, lastY = y;

	public BoundingBox(Class<S> shapeClass, Supplier<S> shapeSupplier) {
		this.shape = shapeSupplier.get();
	}

	public S getShape() {
		return shape;
	}

	public void update(int x, int y) {
		this.x = x;
		this.y = y;

		// Check if the shape is a rectangle
		if (shape instanceof Rectangle rectangle)
			// Set the rectangle's location to x and y
			rectangle.setLocation(x, y);

		// Check if the shape is a polygon
		else if (shape instanceof Polygon polygon) {
			// Translate the polygon by deltaX and deltaY (current - last)
			int deltaX = x - lastX;
			int deltaY = y - lastY;

			polygon.translate(deltaX, deltaY);
		}

		// Update the last fields of the bounding box
		this.lastX = x;
		this.lastY = y;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public boolean overlaps(BoundingBox<?> boundingBox) {
		return this.shape.intersects(boundingBox.getShape().getBounds2D());
	}
}
