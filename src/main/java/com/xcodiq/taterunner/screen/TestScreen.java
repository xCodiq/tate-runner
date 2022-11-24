package com.xcodiq.taterunner.screen;

import de.gurkenlabs.litiengine.graphics.TextRenderer;
import de.gurkenlabs.litiengine.gui.screens.GameScreen;
import de.gurkenlabs.litiengine.resources.Resources;

import java.awt.*;

public final class TestScreen extends GameScreen {

	public TestScreen() {
		super("Test");
	}

	@Override
	public void render(Graphics2D g) {
		super.render(g);

		g.setFont(Resources.fonts().get("font/slkscr.ttf", 40f));
		g.setColor(Color.WHITE);

		TextRenderer.render(g, "Hello", 100, 270);
	}
}
