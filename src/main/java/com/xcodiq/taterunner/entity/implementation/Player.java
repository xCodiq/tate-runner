package com.xcodiq.taterunner.entity.implementation;

import com.xcodiq.taterunner.entity.bound.annotation.BoundContext;
import com.xcodiq.taterunner.entity.bound.type.BoundType;
import com.xcodiq.taterunner.entity.sprite.AnimatedSpriteEntity;
import com.xcodiq.taterunner.logger.Logger;
import com.xcodiq.taterunner.profile.Profile;
import com.xcodiq.taterunner.screen.TateGameScreen;

@BoundContext(boundType = BoundType.BOX)
public final class Player extends AnimatedSpriteEntity {
	public static final int STARTING_LIVES = 3;
	private static final float JUMP_HEIGHT = 13f;

	private static final float GRAVITY = 0.75f;
	private static final float INITIAL_JUMP_VELOCITY = JUMP_HEIGHT * 2;

	public double jumpVelocity = INITIAL_JUMP_VELOCITY;
	private boolean jumping = false;

	private int lives = STARTING_LIVES;

	public Player(Profile profile, double startingX, double startingY) {
		super(profile.getEquippedTateSprite().getRender().getImageAnimation(), startingX, startingY);

		try {
			// Update the bound context of the player according to the equipped sprite
			this.updateBoundContext(BoundType.POLYGON, profile.getEquippedTateSprite().getRender().getBoundPath());
		} catch (Exception ignored) {
			Logger.debug("Failed to update bound context of player! Setting to default: BOX");
		}
	}

	@Override
	public void render(TateGameScreen tateGameScreen) {
		if (!this.jumping) super.render(tateGameScreen);
		else super.renderLastFrame(tateGameScreen);
	}

	@Override
	public void reset() {
		super.reset();

		this.lives = STARTING_LIVES;

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

	public int getLives() {
		return lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}
}
