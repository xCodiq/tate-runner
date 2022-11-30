package com.xcodiq.taterunner.entity.sprite;

import com.xcodiq.taterunner.TateRunnerGame;
import com.xcodiq.taterunner.entity.Entity;
import com.xcodiq.taterunner.screen.TateGameScreen;
import de.gurkenlabs.litiengine.graphics.ShapeRenderer;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SpriteEntity extends Entity {

	protected final BufferedImage spriteImage;

	public SpriteEntity(BufferedImage spriteImage, double startingX, double startingY) {
		super(startingX, startingY, spriteImage.getWidth(), spriteImage.getHeight());

		this.spriteImage = spriteImage;
	}

	public BufferedImage getSpriteImage() {
		return spriteImage;
	}

	@Override
	public void render(TateGameScreen tateGameScreen) {
		// Draw the bounding box if debug mode is enabled
		if (TateRunnerGame.DEBUG_MODE) {
			tateGameScreen.getGraphics().setColor(Color.RED);
			ShapeRenderer.render(tateGameScreen.getGraphics(), this.boundingBox);
		}

		// Draw the sprite image
		tateGameScreen.drawImage(this.x, this.y, this.spriteImage);
	}
}
