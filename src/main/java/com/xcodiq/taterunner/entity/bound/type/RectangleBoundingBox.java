package com.xcodiq.taterunner.entity.bound.type;

import com.xcodiq.taterunner.entity.bound.BoundingBox;

import java.awt.*;

public class RectangleBoundingBox extends BoundingBox<Rectangle> {
	public RectangleBoundingBox(int width, int height) {
		super(Rectangle.class, () -> new Rectangle(width, height));
	}
}
