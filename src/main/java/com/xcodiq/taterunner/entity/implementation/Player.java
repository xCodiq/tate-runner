package com.xcodiq.taterunner.entity.implementation;

import com.xcodiq.taterunner.TateRunnerGame;
import com.xcodiq.taterunner.entity.sprite.AnimatedSpriteEntity;
import com.xcodiq.taterunner.screen.TateGameScreen;
import com.xcodiq.taterunner.util.animation.ImageAnimation;


public final class Player extends AnimatedSpriteEntity {

	private double jumpVelocity = 15;
	private boolean jumping = false;

	public Player(double startingX, double startingY) {
		super(new ImageAnimation("kakashi", 24, 100, 200, 200),
				startingX, startingY);
	}

	@Override
	public void render(TateGameScreen tateGameScreen) {
		if (!this.jumping) super.render(tateGameScreen);
		else super.renderLastFrame(tateGameScreen);
	}

	@Override
	public void reset() {
		super.reset();

		this.jumpVelocity = 15;
		this.jumping = false;
	}

	public void jump() {
		if (!this.jumping) return;

		this.y -= this.jumpVelocity * TateRunnerGame.GRAVITY;
		this.jumpVelocity -= 1;
		if (jumpVelocity < -15) {
			this.jumping = false;
			this.jumpVelocity = 15;
		}

		this.updateBoundingBox();
	}

	public boolean isJumping() {
		return jumping;
	}

	public void setJumping(boolean jumping) {
		this.jumping = jumping;
	}
}
