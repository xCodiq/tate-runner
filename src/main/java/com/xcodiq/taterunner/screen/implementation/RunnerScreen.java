package com.xcodiq.taterunner.screen.implementation;

import com.xcodiq.taterunner.TateRunnerGame;
import com.xcodiq.taterunner.asset.font.TateFonts;
import com.xcodiq.taterunner.entity.implementation.Player;
import com.xcodiq.taterunner.entity.implementation.Rock;
import com.xcodiq.taterunner.entity.implementation.Bat;
import com.xcodiq.taterunner.entity.implementation.Coins;
import com.xcodiq.taterunner.manager.implementation.ProfileManager;
import com.xcodiq.taterunner.manager.implementation.StateManager;
import com.xcodiq.taterunner.profile.Profile;
import com.xcodiq.taterunner.screen.TateGameScreen;
import com.xcodiq.taterunner.screen.background.GameBackgrounds;
import com.xcodiq.taterunner.screen.button.implementation.cosmetic.CosmeticShopButton;
import com.xcodiq.taterunner.screen.keystroke.Keystroke;
import com.xcodiq.taterunner.screen.render.BackgroundRender;
import com.xcodiq.taterunner.state.State;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.graphics.ShapeRenderer;

import java.util.Arrays;
import java.util.Random;

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
	private Rock rock;

	private Coins coins;
	private Bat bat;
	private int distanceWalked;
	private double gameSpeed;

	public final Random random = new Random();

	public RunnerScreen(TateRunnerGame tateRunner) {
		super(tateRunner, "Runner");
		this.stateManager = tateRunner.getManager(StateManager.class);
		this.profile = tateRunner.getManager(ProfileManager.class).getProfile();

		// Get the currently selected game background from the profile
		final GameBackgrounds gameBackground = this.profile.getCurrentGameBackground();
		this.floorYCoordinate = gameBackground.getFloorCoordinate();

		// Initialize a new player
		this.player = new Player(580, this.floorYCoordinate);
		this.player.setPauseAnimationCondition(() -> this.stateManager.getCurrentState() == State.RUNNING);

		// Load the background image
		this.backgroundImage = gameBackground.getBackgroundImage().toImage();

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

		//render the bat
		this.bat.render(this);

		//render the coin
		this.coins.render(this);

		// Render the player
		this.player.render(this);

		// Check for the current state to see what to do this cycle
		switch (currentState) {
			// Only run game progression things when current state is set to running
			case RUNNING -> {
				if (this.player.collidesWith(this.rock)) {
					this.stateManager.setCurrentState(State.DIED);
					return;
				} else if (this.player.collidesWith(this.bat)) {
					this.stateManager.setCurrentState(State.DIED);
					return;
				} else if (this.player.collidesWith(this.coins)) {
					int coins = 0;
					coins++;
					this.coins.reset();
					return;
				}

				// Update the rock
				this.updateRock();

				//update the bat
				this.updateBat();

				//update coins
				this.updateCoins();

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
			this.rock = new Rock(TateRunnerGame.GAME_WIDTH , this.floorYCoordinate,
					randomSize, randomSize);
		}
	}

	private void updateBat() {
		this.bat.update(this.gameSpeed);

		if (this.bat.getX() < -this.bat.getWidth()) {
			final int randomSize = ThreadLocalRandom.current().nextInt(75, 125);
			this.bat = new Bat(TateRunnerGame.GAME_WIDTH , this.floorYCoordinate -250,
					randomSize, randomSize);
		}
	}

	private void updateCoins() {
		this.coins.update(this.gameSpeed);

		if (this.coins.getX() < -this.coins.getWidth()) {
			final int randomSize = ThreadLocalRandom.current().nextInt(75, 125);
			this.coins = new Coins(TateRunnerGame.GAME_WIDTH , this.floorYCoordinate,
					randomSize, randomSize);
		}
	}

	private void restart() {

		// Reset the entities
		this.player.reset();
		final int randomSize = ThreadLocalRandom.current().nextInt(75, 125);

		this.rock = new Rock(TateRunnerGame.GAME_WIDTH + 500, this.floorYCoordinate, randomSize, randomSize);
		this.bat = new Bat(TateRunnerGame.GAME_WIDTH +1000,this.floorYCoordinate -250, randomSize, randomSize);
		this.coins = new Coins(TateRunnerGame.GAME_WIDTH +1000,this.floorYCoordinate, randomSize, randomSize);

		// Reset the background and values
		this.backgroundRender.reset();
		this.distanceWalked = 0;
		this.gameSpeed = -6.00;

		// Set the state to running again
		this.stateManager.setCurrentState(State.RUNNING);
	}
}
