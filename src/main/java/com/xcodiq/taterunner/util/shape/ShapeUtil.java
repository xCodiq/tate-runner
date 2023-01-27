package com.xcodiq.taterunner.util.shape;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;

public class ShapeUtil {

	private ShapeUtil() {
		// unused
	}

	public static Shape imageToShape(BufferedImage image) {
		Area area = new Area();
		Rectangle rectangle = new Rectangle();
		int y1, y2;
		for (int x = 0; x < image.getWidth(); x++) {
			y1 = Integer.MAX_VALUE;
			y2 = -1;
			for (int y = 0; y < image.getHeight(); y++) {
				int rgb = image.getRGB(x, y);
				rgb = rgb >>> 24;
				if (rgb > 0) {
					if (y1 == Integer.MAX_VALUE) {
						y1 = y;
						y2 = y;
					}
					if (y > (y2 + 1)) {
						rectangle.setBounds(x, y1, 1, y2 - y1 + 1);
						area.add(new Area(rectangle));
						y1 = y;
					}
					y2 = y;
				}
			}
			if ((y2 - y1) >= 0) {
				rectangle.setBounds(x, y1, 1, y2 - y1 + 1);
				area.add(new Area(rectangle));
			}
		}
		return area;
	}
}
