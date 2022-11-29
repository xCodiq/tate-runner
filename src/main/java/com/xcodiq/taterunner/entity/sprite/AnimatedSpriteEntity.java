package com.xcodiq.taterunner.entity.sprite;

import com.xcodiq.taterunner.TateRunnerGame;
import com.xcodiq.taterunner.entity.Entity;
import com.xcodiq.taterunner.screen.TateGameScreen;
import com.xcodiq.taterunner.util.animation.ImageAnimation;
import de.gurkenlabs.litiengine.graphics.ShapeRenderer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.function.Supplier;

public class AnimatedSpriteEntity extends Entity {

	protected final ImageAnimation spriteAnimation;
	protected BufferedImage lastSpriteAnimationFrame;

	protected Supplier<Boolean> pauseAnimationCondition;

	public AnimatedSpriteEntity(ImageAnimation spriteAnimation, double startingX, double startingY) {
		super(startingX, startingY, spriteAnimation.getWidth(), spriteAnimation.getHeight());
		this.spriteAnimation = spriteAnimation;
	}

	@Override
	public void render(TateGameScreen tateGameScreen) {
		// Draw the bounding box if debug mode is enabled
		if (TateRunnerGame.DEBUG_MODE) {
			tateGameScreen.getGraphics().setColor(Color.RED);
			ShapeRenderer.render(tateGameScreen.getGraphics(), this.boundingBox);
		}

		// Check if the animation should be paused
		if (this.pauseAnimationCondition != null && !this.pauseAnimationCondition.get()) {
			tateGameScreen.drawImage(this.x, this.y, this.lastSpriteAnimationFrame);
			return;
		}

		// Draw the sprite image
		final BufferedImage currentFrame = this.spriteAnimation.getCurrentFrame();
		tateGameScreen.drawImage(this.x, this.y, currentFrame);

		// Save the last frame in case we need to render it again or use it somewhere else
		this.lastSpriteAnimationFrame = currentFrame;
	}

	public void renderLastFrame(TateGameScreen tateGameScreen) {
		// Draw the bounding box if debug mode is enabled
		if (TateRunnerGame.DEBUG_MODE) {
			tateGameScreen.getGraphics().setColor(Color.RED);
			ShapeRenderer.render(tateGameScreen.getGraphics(), this.boundingBox);
		}

		tateGameScreen.drawImage(this.x, this.y, this.lastSpriteAnimationFrame);
	}

	public ImageAnimation getSpriteAnimation() {
		return spriteAnimation;
	}

	public void setPauseAnimationCondition(Supplier<Boolean> pauseAnimationCondition) {
		this.pauseAnimationCondition = pauseAnimationCondition;
	}
}
