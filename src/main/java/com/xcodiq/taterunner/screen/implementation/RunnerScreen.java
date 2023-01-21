package com.xcodiq.taterunner.screen.implementation;

import com.xcodiq.taterunner.TateRunnerGame;
import com.xcodiq.taterunner.asset.color.TateColors;
import com.xcodiq.taterunner.asset.font.TateFonts;
import com.xcodiq.taterunner.asset.image.TateImages;
import com.xcodiq.taterunner.asset.scene.TateScene;
import com.xcodiq.taterunner.entity.implementation.Player;
import com.xcodiq.taterunner.entity.implementation.Rock;
import com.xcodiq.taterunner.manager.implementation.ProfileManager;
import com.xcodiq.taterunner.manager.implementation.StateManager;
import com.xcodiq.taterunner.profile.Profile;
import com.xcodiq.taterunner.screen.TateGameScreen;
import com.xcodiq.taterunner.screen.button.implementation.cosmetic.CosmeticShopButton;
import com.xcodiq.taterunner.screen.keystroke.Keystroke;
import com.xcodiq.taterunner.screen.render.BackgroundRender;
import com.xcodiq.taterunner.state.State;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.graphics.ShapeRenderer;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.concurrent.ThreadLocalRandom;

public final class RunnerScreen extends TateGameScreen {

	private final StateManager stateManager;
	private final Profile profile;

	private final Player player;

	private final double floorYCoordinate;
	private final BufferedImage backgroundImage;
	private final BackgroundRender backgroundRender;

	private final Font gameFont, gameTextFont;
	boolean isHurt = false;
	private Rock rock;
	private int distanceWalked;
	private double gameSpeed;

	public RunnerScreen(TateRunnerGame tateRunner) {
		super(tateRunner, "Runner");
		this.stateManager = tateRunner.getManager(StateManager.class);
		this.profile = tateRunner.getManager(ProfileManager.class).getProfile();

		// Get the currently selected game background from the profile
		final TateScene tateScene = this.profile.getCurrentTateScene();
		this.floorYCoordinate = tateScene.getFloorCoordinate();

		// Initialize a new player
		this.player = new Player(this.profile, 580, this.floorYCoordinate);
		this.player.setPauseAnimationCondition(() -> this.stateManager.getCurrentState() != State.RUNNING);

		// Load the background image
		this.backgroundImage = tateScene.toImage();

		// Set up font
		this.gameFont = TateFonts.PRIMARY_TITLE.toFont();
		this.gameTextFont = TateFonts.SECONDARY_SUBTITLE.toFont();

		// Initialize the background render coordinates
		this.backgroundRender = new BackgroundRender(0, TateRunnerGame.WIDTH);

		// Create all the buttons for this screen
		this.addButton(new CosmeticShopButton(tateRunner, 20, 20));

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
				if (!isHurt && this.player.collidesWith(this.rock)) {
					if (this.player.getLives() == 1) {
						this.stateManager.setCurrentState(State.DIED);
					} else {
						this.player.setLives(this.player.getLives() - 1);
					}
					this.isHurt = true;
					return;
				}
				this.isHurt = this.player.collidesWith(this.rock);

				// Update the rock
				this.updateRock();

				// Attempt to change the player's position while jumping
				this.player.jump();

				// Adjust the background coordinates
				backgroundRender.adjust(this.gameSpeed);

				// Render the scoreboard
				this.renderScoreboard();

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
				this.renderAllButtons();
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
		this.drawBackgroundImage(this.backgroundImage, backgroundRender.getBackgroundX());
		this.drawBackgroundImage(this.backgroundImage, backgroundRender.getNextBackgroundX());
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

	private void renderScoreboard() {
		// Draw the rectangle
		this.drawRectangle(10, 10, new Rectangle(300, 220),
				new Color(44, 41, 41, TateColors.MEDIUM_BACKGROUND_ALPHA));
		this.drawRectangle(10, 10, new Rectangle(300, 40),
				new Color(44, 41, 41, TateColors.LOW_BACKGROUND_ALPHA));

		// Draw the title of the scoreboard
		this.drawText(112 - 30, 43, Color.CYAN, 30f, "TATE RUNNER");

		// Draw the coins indicator
		this.drawText(25, 80, TateFonts.SECONDARY_SUBTITLE.toFont(), Color.orange, 23f,
				"Coins: " + this.profile.getCoins());
		// Draw the distance indicator
		this.drawText(25, 130, TateFonts.SECONDARY_SUBTITLE.toFont(), Color.white, 23f,
				"Score: " + this.distanceWalked + "m");
		this.drawText(25, 150, TateFonts.SECONDARY_SUBTITLE.toFont(), Color.gray, 23f,
				"Highscore: " + "45m");

		// Draw the hearts indicator
		BufferedImage heartsImage = switch (this.player.getLives()) {
			case Player.STARTING_LIVES -> TateImages.HEART_FULL.toImage(186, 40);
			case 2 -> TateImages.HEART_2LEFT.toImage(186, 40);
			case 1 -> TateImages.HEART_1LEFT.toImage(186, 40);
			default -> null;
		};
		this.drawStaticImage(20, 135, heartsImage);
	}
}
