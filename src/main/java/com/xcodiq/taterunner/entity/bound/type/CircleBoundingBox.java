package com.xcodiq.taterunner.entity.bound.type;

import com.xcodiq.taterunner.entity.bound.BoundingBox;

import java.awt.geom.Ellipse2D;

public class CircleBoundingBox extends BoundingBox<Ellipse2D> {
	public CircleBoundingBox(int width, int height) {
		super(Ellipse2D.class, () -> new Ellipse2D.Double(0,0,width,height));
	}
}
