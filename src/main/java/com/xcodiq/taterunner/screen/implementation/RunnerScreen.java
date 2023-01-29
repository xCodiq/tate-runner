package com.xcodiq.taterunner.screen.implementation;

import com.xcodiq.taterunner.TateRunnerGame;
import com.xcodiq.taterunner.asset.color.TateColors;
import com.xcodiq.taterunner.asset.font.TateFonts;
import com.xcodiq.taterunner.asset.image.TateImages;
import com.xcodiq.taterunner.asset.scene.TateScene;
import com.xcodiq.taterunner.asset.sound.TateSounds;
import com.xcodiq.taterunner.entity.implementation.Bat;
import com.xcodiq.taterunner.entity.implementation.Coin;
import com.xcodiq.taterunner.entity.implementation.Player;
import com.xcodiq.taterunner.entity.implementation.Rock;
import com.xcodiq.taterunner.manager.implementation.EnemyManager;
import com.xcodiq.taterunner.manager.implementation.ProfileManager;
import com.xcodiq.taterunner.manager.implementation.ScreenManager;
import com.xcodiq.taterunner.manager.implementation.StateManager;
import com.xcodiq.taterunner.profile.Profile;
import com.xcodiq.taterunner.screen.TateGameScreen;
import com.xcodiq.taterunner.screen.button.implementation.ExitButton;
import com.xcodiq.taterunner.screen.keystroke.Keystroke;
import com.xcodiq.taterunner.screen.render.BackgroundRender;
import com.xcodiq.taterunner.state.State;
import de.gurkenlabs.litiengine.Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public final class RunnerScreen extends TateGameScreen {

	private final EnemyManager enemyManager;
	private final StateManager stateManager;
	private final Profile profile;

	private final BackgroundRender backgroundRender;
	private final Font primaryFont, secondaryFont;

	private final List<Coin> coins = new ArrayList<>();
	private int coinsCollected;

	private Player player;
	private BufferedImage backgroundImage;
	private double floorYCoordinate;
	private boolean highScore = false;

	private int distanceWalked;
	private double gameSpeed;

	public RunnerScreen(TateRunnerGame tateRunner) {
		super(tateRunner, "Runner");

		// Initialize the managers
		this.enemyManager = tateRunner.getManager(EnemyManager.class);
		this.stateManager = tateRunner.getManager(StateManager.class);
		this.profile = tateRunner.getManager(ProfileManager.class).getProfile();

		// Set up font
		this.primaryFont = TateFonts.PRIMARY_TITLE.toFont();
		this.secondaryFont = TateFonts.SECONDARY_SUBTITLE.toFont();

		// Initialize the background render coordinates
		this.backgroundRender = new BackgroundRender(0, TateRunnerGame.WIDTH);

		// Create all the buttons for this screen
		this.addButton(new ExitButton(20, 20, button ->
				this.tateRunner.getManager(ScreenManager.class).showScreen(SplashScreen.class)));

		// Restart the game
		this.restart();
	}

	@Override
	public void render() {
		// Play the main theme, looped
		TateSounds.MAIN_THEME.playOnBackground();

		// Check the current state and see if the game is focussed or not
		final State currentState = this.stateManager.getCurrentState();
		if (!Game.window().isFocusOwner() && currentState != State.PAUSED && currentState != State.DIED)
			this.stateManager.setCurrentState(State.PAUSED);

		// Render the background
		this.renderBackground();

		// Use the enemy manager to render all the enemies on the screen
		this.enemyManager.renderAll(this);

		// Render the player
		this.player.render(this);

		// Render the coins
		for (Coin coin : this.coins) coin.render(this);

		// Check for the current state to see what to do this cycle
		switch (currentState) {
			// Only run game progression things when current state is set to running
			case RUNNING -> {
				// Check if the player is hitting coins
				for (Coin coin : this.coins) {
					if (player.collidesWith(coin)) {
						coin.reset(); // Clear the coin from the screen

						// Calculate the amount of coins to add to the player
						final int coins = ThreadLocalRandom.current().nextInt(1, 4);
						this.coinsCollected += coins;

						// Add the coins to the player
						this.profile.addCoins(coins);
						TateSounds.PICKUP_COIN.play();
					}
				}

				// Check if the player is hitting any enemies
				if (this.enemyManager.hitEnemy(this.player)) {
					// Play the hurt sound
					TateSounds.PLAYER_HURT.play();

					// Check if they have lives left, if not, set the state to died
					if (this.player.getLives() == 1) {
						// Check if they got a new highscore
						if (this.distanceWalked > this.profile.getHighScore()) this.highScore = true;

						// Set the state to died
						this.stateManager.setCurrentState(State.DIED);

						// Give coins to the profile
						final int coins = (int) Math.pow(this.distanceWalked, 0.8);
						this.coinsCollected += coins;
						this.profile.addCoins(coins);

						// Play the highscore sound if they got a new highscore, otherwise play the death sound
						if (this.highScore) TateSounds.HIGHSCORE.play();
						else TateSounds.YOU_DIED.play();
					} else {
						// Lower the lives by 1, and play the hurt sound
						this.player.setLives(this.player.getLives() - 1);
						TateSounds.HURT.play();
					}

					// Set the player to be hurt
					this.enemyManager.setHurting(true);
					return;
				}

				// Check if the player is still colliding with an enemy
				this.enemyManager.setHurting(this.enemyManager.isColliding(this.player));

				// Update all the enemies
				this.enemyManager.updateAll(this.floorYCoordinate, this.gameSpeed);

				// Update the coins
				for (Coin coin : this.coins) coin.update(this.gameSpeed);

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
					this.gameSpeed -= 0.210;
					this.player.getSpriteAnimation().multiplyInterval(0.9899);
				}

				// Spawn random coins over the map
				if (Game.time().now() % 185 == 0) {//185
					this.spawnRandomCoin();
				}
			}
			case DIED -> {
				// Draw a fullscreen rectangle on with an alpha
				this.drawFullscreenCover(new Color(0, 0, 0, TateColors.HIGH_BACKGROUND_ALPHA));

				// Draw the game paused text on the screen
				this.drawCenteredText(0, 5, this.primaryFont, TateColors.RED_SHADOW, 160f, "YOU DIED!");
				this.drawCenteredText(this.primaryFont, TateColors.RED_TEXT, 160f, "YOU DIED!");
				this.drawCenteredAnimatedText(0, 35, 550, this.secondaryFont, Color.WHITE, 30f,
						"Press > SPACE < to start over",
						"Press > SPACE < to start over",
						"Press  >SPACE<  to start over");

				// Save the current score
				this.profile.setLastScore(this.distanceWalked);

				// Render the game summary, score and current high score
				if (this.highScore) {
					this.profile.setHighScore(this.distanceWalked);
					this.drawCenteredText(0, -280, this.secondaryFont, TateColors.HIGHSCORE_TEXT,
							80f, "! A new High Score !");
				}

				this.drawCenteredText(0, -200, this.secondaryFont, Color.WHITE,
						80f, this.distanceWalked + "M");

				this.drawCenteredText(0, -170, this.secondaryFont, Color.ORANGE,
						30f, "! You earned " + this.coinsCollected + " coins !");

				// Render desired buttons
				this.renderButton(ExitButton.class);
			}
			case PAUSED -> {
				// Draw a fullscreen rectangle on with an alpha
				this.drawFullscreenCover(new Color(0, 0, 0, TateColors.HIGH_BACKGROUND_ALPHA));

				// Draw the game paused text on the screen
				this.drawCenteredText(0, 5, this.primaryFont, TateColors.CYAN_SHADOW, 160f, "GAME PAUSED");
				this.drawCenteredText(this.primaryFont, TateColors.CYAN_TEXT, 160f, "GAME PAUSED");
				this.drawCenteredAnimatedText(0, 35, 550, this.secondaryFont, Color.WHITE, 30f,
						"Press > ESC < to resume the game",
						"Press > ESC < to resume the game",
						"Press  >ESC<  to resume the game");

				// Render all the buttons
				this.renderAllButtons();
			}
		}
	}

	@Keystroke
	public void keystroke(KeyEvent keyEvent) {
		final State currentState = this.stateManager.getCurrentState();
		switch (currentState) {
			case RUNNING, PAUSED -> {
				// Check if the escape key is pressed to toggle the pause menu
				if (keyEvent.getKeyCode() == KeyEvent.VK_ESCAPE) {
					this.stateManager.setCurrentState(this.stateManager.getCurrentState() == State.RUNNING
							? State.PAUSED : State.RUNNING);
					return;
				}

				// Check if the game is in the running state and the space key is pressed
				if (currentState == State.RUNNING && keyEvent.getKeyCode() == KeyEvent.VK_SPACE) {
					if (!this.player.isJumping()) {
						// Set the player jumping
						this.player.setJumping(true);

						// Play the jump sound
						TateSounds.JUMP.play();
					}
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

	private void renderScoreboard() {
		// Draw the rectangle
		this.drawRectangle(10, 10, new Rectangle(300, 220),
				new Color(44, 41, 41, TateColors.MEDIUM_BACKGROUND_ALPHA));
		this.drawRectangle(10, 10, new Rectangle(300, 40),
				new Color(44, 41, 41, TateColors.LOW_BACKGROUND_ALPHA));

		// Draw the title of the scoreboard
		this.drawText(82, 43, Color.CYAN, 30f, "TATE RUNNER");

		// Draw the coins indicator
		this.drawText(25, 80, this.secondaryFont, Color.ORANGE,
				23f, "Coins: " + this.profile.getCoins());
		// Draw the distance indicator
		this.drawText(25, 130, this.secondaryFont, Color.WHITE,
				23f, "Score: " + this.distanceWalked + "m");
		this.drawText(25, 150, this.secondaryFont, Color.GRAY,
				23f, "Highscore: " + this.profile.getHighScore() + "m");

		// Draw the hearts indicator
		BufferedImage heartsImage = switch (this.player.getLives()) {
			case Player.STARTING_LIVES -> TateImages.HEART_FULL.toImage(186, 40);
			case 2 -> TateImages.HEART_2LEFT.toImage(186, 40);
			case 1 -> TateImages.HEART_1LEFT.toImage(186, 40);
			default -> null;
		};
		this.drawStaticImage(20, 165 * TateRunnerGame.IMAGE_SCALE, heartsImage);
	}

	private void spawnRandomCoin() {
		// Spawn a random coin at a random distance
		int randomDistance = ThreadLocalRandom.current().nextInt(100, 351);

		// Calculate the y coordinate of the coin
		int yMod = ThreadLocalRandom.current().nextInt(1, 4);
		int yDiff = 0;
		if (yMod == 1) yDiff = ThreadLocalRandom.current().nextInt(0, 51);
		else if (yMod > 1) yDiff = ThreadLocalRandom.current().nextInt(100, 251);

		// Add the coin to the list
		this.coins.add(new Coin(TateRunnerGame.GAME_WIDTH + randomDistance, this.floorYCoordinate - yDiff));
	}

	private void restart() {
		// Get the currently selected game background from the profile
		final TateScene tateScene = this.profile.getCurrentTateScene();
		this.floorYCoordinate = tateScene.getFloorCoordinate();
		this.backgroundImage = tateScene.toImage();

		// Initialize a new player
		this.player = new Player(this.profile, 580, this.floorYCoordinate);
		this.player.setPauseAnimationCondition(() -> this.stateManager.getCurrentState() != State.RUNNING);
		this.player.reset();

		// Reset the enemy manager
		this.enemyManager.reset();

		// Add new enemies to the enemy list
		int randomSize = ThreadLocalRandom.current().nextInt(75, 125);
		this.enemyManager.add(new Rock(TateRunnerGame.GAME_WIDTH + 500, this.floorYCoordinate, randomSize, randomSize));
		randomSize = ThreadLocalRandom.current().nextInt(75, 125);
		this.enemyManager.add(new Bat(TateRunnerGame.GAME_WIDTH + 2000, this.floorYCoordinate - (randomSize * 3), randomSize, randomSize));

		// Reset the background and values
		this.backgroundRender.reset();
		this.distanceWalked = 0;
		this.gameSpeed = -6.00;
		this.highScore = false;
		this.coinsCollected = 0;

		// Reset all sounds from previous screens
		TateSounds.resetSounds();

		// Set the state to running again
		this.stateManager.setCurrentState(State.RUNNING);
	}
}
