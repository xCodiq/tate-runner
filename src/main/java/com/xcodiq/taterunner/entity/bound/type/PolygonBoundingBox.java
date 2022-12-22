package com.xcodiq.taterunner.entity.bound.type;

import com.xcodiq.taterunner.entity.bound.BoundingBox;
import com.xcodiq.taterunner.entity.bound.point.BoundingPoint;

import java.awt.*;
import java.util.List;

public class PolygonBoundingBox extends BoundingBox<Polygon> {
	public PolygonBoundingBox(List<BoundingPoint> boundingPoints) {
		super(Polygon.class, () -> {
			final Polygon polygon = new Polygon();
			boundingPoints.forEach(point -> polygon.addPoint(point.getX(), point.getY()));
			return polygon;
		});
	}
}
