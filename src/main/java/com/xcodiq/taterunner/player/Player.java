package com.xcodiq.taterunner.player;

import com.xcodiq.taterunner.screen.TateGameScreen;
import de.gurkenlabs.litiengine.graphics.ImageRenderer;
import de.gurkenlabs.litiengine.resources.Resources;
import de.gurkenlabs.litiengine.util.Imaging;

import java.awt.*;

public final class Player {

	private final Image spriteImage;

	private int x, y;

	public Player() {
		// Load the sprite image
		this.spriteImage = Imaging.scale(Resources.images().get("sprites/dev-sprite.png"), 200, 200);
	}

	public void render(TateGameScreen tateGameScreen) {
		// Draw the sprite image
		ImageRenderer.render(tateGameScreen.getGraphics(), this.spriteImage, this.x, this.y);
	}

	public void setStartingPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Image getSpriteImage() {
		return spriteImage;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void addX(int addX) {
		this.x += addX;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void addY(int addY) {
		this.y += addY;
	}
}
