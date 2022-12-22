package com.xcodiq.taterunner.entity.bound;

import java.awt.*;
import java.util.function.Supplier;

public abstract class BoundingBox<S extends Shape> {

	private final S shape;
	private double x, y;

	public BoundingBox(Class<S> shapeClass, Supplier<S> shapeSupplier) {
		this.shape = shapeSupplier.get();
	}

	public S getShape() {
		return shape;
	}

	public void updates(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public void update(double x, double y) {
		if (shape instanceof Rectangle rectangle) rectangle.setLocation((int) x, (int) y);
//		else if (shape instanceof Ellipse2D ellipse) ellipse.setFrame();
		else if (shape instanceof Polygon polygon) polygon.translate((int) x, (int) y);
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public boolean overlaps(BoundingBox<?> boundingBox) {
		return false;
	}
}
