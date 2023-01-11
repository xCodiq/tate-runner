package com.xcodiq.taterunner.entity.implementation;

import com.xcodiq.taterunner.entity.bound.annotation.BoundContext;
import com.xcodiq.taterunner.entity.bound.type.BoundType;
import com.xcodiq.taterunner.entity.sprite.AnimatedSpriteEntity;
import com.xcodiq.taterunner.screen.TateGameScreen;
import com.xcodiq.taterunner.util.animation.ImageAnimation;

@BoundContext(boundType = BoundType.BOX)
public final class Player extends AnimatedSpriteEntity {
	private static final float JUMP_HEIGHT = 13f;

	private static final float GRAVITY = 0.75f;
	private static final float INITIAL_JUMP_VELOCITY = JUMP_HEIGHT * 2;

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
		this.jumpVelocity -= 1f;

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
