package com.xcodiq.taterunner.screen.implementation;

import com.xcodiq.taterunner.TateRunnerGame;
import com.xcodiq.taterunner.keystroke.Keystroke;
import com.xcodiq.taterunner.manager.implementation.StateManager;
import com.xcodiq.taterunner.player.Player;
import com.xcodiq.taterunner.screen.TateGameScreen;
import com.xcodiq.taterunner.screen.button.Button;
import com.xcodiq.taterunner.screen.render.BackgroundRender;
import com.xcodiq.taterunner.state.State;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.graphics.ImageRenderer;
import de.gurkenlabs.litiengine.resources.Resources;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public final class RunnerScreen extends TateGameScreen {

	private final StateManager stateManager;
	private final Player player;

	private final Image backgroundImage;
	private final BackgroundRender backgroundRender;
	private final Font gameFont;

	private final List<Button> pauseMenuButtons = new ArrayList<>();

	private int distanceWalked = 0;
	private double backgroundSpeed = -3.00;

	public RunnerScreen(TateRunnerGame tateRunner) {
		super(tateRunner, "Runner");
		this.stateManager = tateRunner.getManager(StateManager.class);

		// Initialize a new player
		this.player = new Player();
		this.player.setStartingPosition(280, 578);

		// Load the background image
		this.backgroundImage = Resources.images().get("textures/background/tatetunner-dev-background.png");

		// Set up font
		this.gameFont = Resources.fonts().get("font/TarrgetLaserRegular-4OE9.otf");

		// Initialize the background render coordinates
		this.backgroundRender = new BackgroundRender(0, TateRunnerGame.WIDTH);

		// Create all the buttons for this screen
		final Button button = new Button(null, null, 0, 0, 0, 0);
		button.setClickEvent(mouseEvent -> {

		});
	}

	@Override
	public void render() {
		// Check the current state and see if the game is focussed or not
		final State currentState = this.stateManager.getCurrentState();
		if (!Game.window().isFocusOwner() && currentState != State.PAUSED)
			this.stateManager.setCurrentState(State.PAUSED);

		// Render the background
		this.renderBackground();

		// Display the distance walked text
		this.drawCenteredText(0, -320, this.gameFont, Color.ORANGE, 75f, String.valueOf(this.distanceWalked));

		// Render the player
		this.player.render(this);

		// Check for the current state to see what to do this cycle
		switch (currentState) {
			// Only run game progression when current state is set to running
			case RUNNING -> {
				// Up the distance walked
				if (Game.time().now() % (int) (80 * (1 + (this.backgroundSpeed / 100.0))) == 0) {
					this.distanceWalked++;
				}

				// Up the backgroundSpeed
				if (Game.time().now() % 100 == 0) {
					this.backgroundSpeed -= 0.02;
				}
			}
			case PAUSED -> {
				// Draw a fullscreen rectangle on with an alpha
				this.graphics.setColor(new Color(0, 0, 0, (int) (0.75 * 255)));
				this.graphics.fill3DRect(0, 0, 1920, 1080, false);

				// Draw the game paused text on the screen
				this.drawCenteredText(this.gameFont, Color.WHITE, 75f, "GAME PAUSED");

				// Set the pause menu buttons to visible
				this.pauseMenuButtons.forEach(button -> button.toggleVisibility(this));
			}
		}
	}

	@Keystroke
	public void keystroke(KeyEvent keyEvent) {
		switch (keyEvent.getKeyCode()) {
			case KeyEvent.VK_ESCAPE -> {
//				final ScreenManager screenManager = this.tateRunner.getManager(ScreenManager.class);
//				screenManager.showScreen(SplashScreen.class);
				this.stateManager.setCurrentState(this.stateManager.getCurrentState() == State.RUNNING ? State.PAUSED : State.RUNNING);
			}
//			case KeyEvent.VK_UP -> this.player.addY(-10);
//			case KeyEvent.VK_DOWN -> this.player.addY(10);
//			case KeyEvent.VK_RIGHT -> this.player.addX(10);
//			case KeyEvent.VK_LEFT -> this.player.addX(-10);
//			case KeyEvent.VK_BACK_SPACE -> this.distanceWalked = 0;
//			case KeyEvent.VK_ENTER -> Game.loop().setTimeScale(Game.loop().getTimeScale() == 1 ? 0 : 1);
		}
	}

	private void renderBackground() {
		// Determine the background render coordinates have passed the screen and need a reset
		if (backgroundRender.getBackgroundX() <= -backgroundRender.getOriginalNextX()) {
			backgroundRender.reset();
		}

		// Draw the actual background image
		this.drawBackground();

		// Adjust the background coordinates if state is RUNNING
		if (this.stateManager.getCurrentState() == State.RUNNING)
			backgroundRender.adjust(backgroundSpeed);
	}

	private void drawBackground() {
		this.drawBackgroundAt(backgroundRender.getBackgroundX());
		this.drawBackgroundAt(backgroundRender.getNextBackgroundX());
	}

	private void drawBackgroundAt(double backgroundX) {
		ImageRenderer.render(this.graphics, this.backgroundImage, backgroundX, 0);
	}
}
