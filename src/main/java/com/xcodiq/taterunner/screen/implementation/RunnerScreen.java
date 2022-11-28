package com.xcodiq.taterunner.screen.implementation;

import com.xcodiq.taterunner.TateRunnerGame;
import com.xcodiq.taterunner.keystroke.Keystroke;
import com.xcodiq.taterunner.manager.implementation.ScreenManager;
import com.xcodiq.taterunner.player.Player;
import com.xcodiq.taterunner.screen.TateGameScreen;
import com.xcodiq.taterunner.screen.render.BackgroundRender;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.graphics.ImageRenderer;
import de.gurkenlabs.litiengine.resources.Resources;

import java.awt.*;
import java.awt.event.KeyEvent;

public final class RunnerScreen extends TateGameScreen {

	private final Player player;

	private final Image backgroundImage;
	private final BackgroundRender backgroundRender;
	private final Font gameFont;

	private int distanceWalked = 0;
	private double backgroundSpeed = -3.00;

	public RunnerScreen(TateRunnerGame tateRunner) {
		super(tateRunner, "Runner");

		// Initialize a new player
		this.player = new Player();
		this.player.setStartingPosition(280, 578);

		// Load the background image
		this.backgroundImage = Resources.images().get("textures/background/tatetunner-dev-background.png");

		// Set up font
		this.gameFont = Resources.fonts().get("font/TarrgetLaserRegular-4OE9.otf");

		// Initialize the background render coordinates
		this.backgroundRender = new BackgroundRender(0, TateRunnerGame.WIDTH);
	}

	@Override
	public void render() {
		// Render the background
		this.renderBackground();

		// Display the distance walked text
		this.drawCenteredText(0, -420, this.gameFont, Color.ORANGE, 75f, String.valueOf(this.distanceWalked));

		// Render the player
		this.player.render(this);

		// Up the distance walked
		if (Game.time().now() % (int) (80 * (1 + (this.backgroundSpeed / 100.0))) == 0) {
			this.distanceWalked++;
		}

		// Up the backgroundSpeed
		if (Game.time().now() % 100 == 0) {
			this.backgroundSpeed -= 0.02;
		}
	}

	@Keystroke
	public void keystroke(KeyEvent keyEvent) {
		switch (keyEvent.getKeyCode()) {
			case KeyEvent.VK_ESCAPE -> {
				final ScreenManager screenManager = this.tateRunner.getManager(ScreenManager.class);
				screenManager.showScreen(SplashScreen.class);
			}
			case KeyEvent.VK_UP -> this.player.addY(-10);
			case KeyEvent.VK_DOWN -> this.player.addY(10);
			case KeyEvent.VK_RIGHT -> this.player.addX(10);
			case KeyEvent.VK_LEFT -> this.player.addX(-10);
			case KeyEvent.VK_BACK_SPACE -> this.distanceWalked = 0;
			case KeyEvent.VK_ENTER -> Game.loop().setTimeScale(Game.loop().getTimeScale() == 1 ? 0 : 1);
		}
	}

	private void renderBackground() {
		// Determine the background render coordinates have passed the screen and need a reset
		if (backgroundRender.getBackgroundX() <= -backgroundRender.getOriginalNextX()) {
			backgroundRender.reset();
		}

		// Render the actual background image
		this.drawBackgroundAt(backgroundRender.getBackgroundX());
		this.drawBackgroundAt(backgroundRender.getNextBackgroundX());

		// Adjust the background coordinates
		backgroundRender.adjust(backgroundSpeed);
	}

	private void drawBackgroundAt(double backgroundX) {
		ImageRenderer.render(this.graphics, this.backgroundImage, backgroundX, 0);
	}
}
