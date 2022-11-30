package com.xcodiq.taterunner.entity.implementation;

import com.xcodiq.taterunner.entity.sprite.AnimatedSpriteEntity;
import com.xcodiq.taterunner.screen.TateGameScreen;
import com.xcodiq.taterunner.util.animation.ImageAnimation;


public final class Player extends AnimatedSpriteEntity {
	private static final float JUMP_HEIGHT = 1f; // short jump

	private static final float GRAVITY = 4.2f;
	private static final float INITIAL_JUMP_VELOCITY = 3.5f;

	public double jumpVelocity = INITIAL_JUMP_VELOCITY;
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

		this.jumpVelocity = INITIAL_JUMP_VELOCITY;
		this.jumping = false;
	}

	@Override
	public void update(double x) {
		super.update(x);
	}

	public boolean isOnGround() {
		return this.y == this.startingY;
	}

	public void jump() {
		// Ignore if they aren't jumping
		if (!this.jumping) return;

		this.y -= this.jumpVelocity * GRAVITY;
		this.jumpVelocity -= 0.125f;

		if (jumpVelocity < -INITIAL_JUMP_VELOCITY) {
			this.jumping = false;
			this.jumpVelocity = INITIAL_JUMP_VELOCITY;
		}

		// Update the bounding box of the player
		this.updateBoundingBox();
	}

	public boolean isJumping() {
		return jumping;
	}

	public void setJumping(boolean jumping) {
		this.jumping = jumping;
	}
}
