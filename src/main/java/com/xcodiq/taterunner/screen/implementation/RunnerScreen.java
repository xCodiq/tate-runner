package com.xcodiq.taterunner.screen.implementation;

import com.xcodiq.taterunner.TateRunnerGame;
import com.xcodiq.taterunner.entity.implementation.Player;
import com.xcodiq.taterunner.entity.implementation.Rock;
import com.xcodiq.taterunner.manager.implementation.StateManager;
import com.xcodiq.taterunner.screen.TateGameScreen;
import com.xcodiq.taterunner.screen.button.implementation.StoreButton;
import com.xcodiq.taterunner.screen.keystroke.Keystroke;
import com.xcodiq.taterunner.screen.render.BackgroundRender;
import com.xcodiq.taterunner.state.State;
import com.xcodiq.taterunner.util.image.ImageUtil;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.graphics.ShapeRenderer;
import de.gurkenlabs.litiengine.resources.Resources;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.concurrent.ThreadLocalRandom;

public final class RunnerScreen extends TateGameScreen {

	private final StateManager stateManager;

	private final Player player;
	private final double floorYCoordinate = 777;
	private final BufferedImage backgroundImage;
	private final BackgroundRender backgroundRender;
	private final Font gameFont, gameTextFont;
	private Rock rock;
	private int distanceWalked;
	private double gameSpeed;

	public RunnerScreen(TateRunnerGame tateRunner) {
		super(tateRunner, "Runner");
		this.stateManager = tateRunner.getManager(StateManager.class);

		// Initialize a new player
		this.player = new Player(580, floorYCoordinate);
		this.player.setPauseAnimationCondition(() -> this.stateManager.getCurrentState() == State.RUNNING);

		// Load the background image
		this.backgroundImage = ImageUtil.loadImage("textures/background/cpastegame-3-graveyard.png");
//		this.backgroundImage = ImageUtil.loadImage("textures/background/tree-background.png");
//		this.backgroundImage = ImageUtil.loadImage("textures/background/tatetunner-dev-background.png");

		// Set up font
		this.gameFont = Resources.fonts().get("font/ElecstromRegular-w1y4P.ttf");
		this.gameTextFont = Resources.fonts().get("font/slkscr.ttf");

		// Initialize the background render coordinates
		this.backgroundRender = new BackgroundRender(0, TateRunnerGame.WIDTH);

		// Create all the buttons for this screen
		this.addButton(new StoreButton(tateRunner));

		// Restart the game
		this.restart();
	}

	@Override
	public void render() {
		// Check the current state and see if the game is focussed or not
		final State currentState = this.stateManager.getCurrentState();
		if (!Game.window().isFocusOwner() && currentState != State.PAUSED)
			this.stateManager.setCurrentState(State.PAUSED);

		// Render the background
		this.renderBackground();

		// Render the rock
		this.rock.render(this);

		// Render the player
		this.player.render(this);

		// Check for the current state to see what to do this cycle
		switch (currentState) {
			// Only run game progression things when current state is set to running
			case RUNNING -> {
				if (this.player.collidesWith(this.rock)) {
					this.stateManager.setCurrentState(State.DIED);
					return;
				}
//				if (this.player.collidesWith(this.rock)) {
//					if (this.player.isOnGround()) {
//						this.player.update(gameSpeed);
//					} else {
//						this.stateManager.setCurrentState(State.DIED);
//						return;
//					}
//				}

				// Update the rock
				this.updateRock();

				// Display the distance walked text
				this.drawCenteredText(0, 400, this.gameFont, Color.ORANGE,
						75f, String.valueOf(this.distanceWalked));

				// Attempt to change the player's position while jumping
				this.player.jump();

				// Adjust the background coordinates
				backgroundRender.adjust(this.gameSpeed);

				// Up the distance walked
				if (Game.time().now() % (int) (80 * (1 + (this.gameSpeed / 100.0))) == 0) {
					this.distanceWalked++;
				}

				// Up the gameSpeed and playerAnimationSpeed
				if (Game.time().now() % 100 == 0) {
					this.gameSpeed -= 0.02;
					this.player.getSpriteAnimation().multiplyInterval(0.9995);
				}
			}
			case DIED -> {
				// Draw a fullscreen rectangle on with an alpha
				this.graphics.setColor(new Color(0, 0, 0, (int) (0.85 * 255)));
				ShapeRenderer.render(this.graphics, new Rectangle(1920, 1080), 0, 0);

				// Draw the game paused text on the screen
				this.drawCenteredText(0, 5, this.gameFont, new Color(96, 0, 0), 125f, "YOU DIED!");
				this.drawCenteredText(this.gameFont, Color.RED, 125f, "YOU DIED!");
				this.drawCenteredAnimatedText(0, 50, 550, this.gameTextFont, new Color(213, 213, 213), 25f,
						"Press > SPACE < to start over",
						"Press > SPACE < to start over",
						"Press  >SPACE<  to start over");
			}
			case PAUSED -> {
				// Draw a fullscreen rectangle on with an alpha
				this.graphics.setColor(new Color(0, 0, 0, (int) (0.85 * 255)));
				ShapeRenderer.render(this.graphics, new Rectangle(1920, 1080), 0, 0);

				// Draw the game paused text on the screen
				this.drawCenteredText(0, 5, this.gameFont, new Color(2, 96, 96), 125f, "GAME PAUSED");
				this.drawCenteredText(this.gameFont, Color.CYAN, 125f, "GAME PAUSED");
				this.drawCenteredAnimatedText(0, 50, 550, this.gameTextFont, new Color(213, 213, 213), 25f,
						"Press > ESC < to resume the game",
						"Press > ESC < to resume the game",
						"Press  >ESC<  to resume the game");

				// Set the pause menu buttons to visible
				this.getButtons().forEach(button -> button.render(this));
			}
		}
	}

	@Keystroke
	public void keystroke(KeyEvent keyEvent) {
		final State currentState = this.stateManager.getCurrentState();
		switch (currentState) {
			case RUNNING, PAUSED -> {
				if (keyEvent.getKeyCode() == KeyEvent.VK_ESCAPE) {
					this.stateManager.setCurrentState(this.stateManager.getCurrentState() == State.RUNNING
							? State.PAUSED : State.RUNNING);
				}

				if (currentState == State.RUNNING && keyEvent.getKeyCode() == KeyEvent.VK_SPACE) {
					if (!this.player.isJumping()) this.player.setJumping(true);
				}

				if (currentState == State.RUNNING && keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
					this.stateManager.setCurrentState(State.DIED);
				}
			}
			case DIED -> {
				if (keyEvent.getKeyCode() == KeyEvent.VK_SPACE) {
					this.restart();
				}
			}
		}
	}

	@Override
	public void preShow() {
		this.restart();
	}

	private void renderBackground() {
		// Determine the background render coordinates have passed the screen and need a reset
		if (backgroundRender.getBackgroundX() <= -backgroundRender.getOriginalNextX()) {
			backgroundRender.reset();
		}

		// Draw the actual background image
		this.drawImage(backgroundRender.getBackgroundX(), 0, this.backgroundImage);
		this.drawImage(backgroundRender.getNextBackgroundX(), 0, this.backgroundImage);
	}

	private void updateRock() {
		this.rock.update(this.gameSpeed);

		if (this.rock.getX() < -this.rock.getWidth()) {
			final int randomSize = ThreadLocalRandom.current().nextInt(75, 125);
			final int randomDistance = ThreadLocalRandom.current().nextInt(600, 1750);
			this.rock = new Rock(TateRunnerGame.GAME_WIDTH + randomDistance, this.floorYCoordinate,
					randomSize, randomSize);
		}
	}

	private void restart() {
		// Reset the entities
		this.player.reset();
		final int randomSize = ThreadLocalRandom.current().nextInt(75, 125);
		this.rock = new Rock(TateRunnerGame.GAME_WIDTH + 500, this.floorYCoordinate, randomSize, randomSize);

		// Reset the background and values
		this.backgroundRender.reset();
		this.distanceWalked = 0;
		this.gameSpeed = -6.00;

		// Set the state to running again
		this.stateManager.setCurrentState(State.RUNNING);
	}
}
