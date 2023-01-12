package com.xcodiq.taterunner.screen.implementation;

import com.xcodiq.taterunner.TateRunnerGame;
import com.xcodiq.taterunner.asset.font.TateFonts;
import com.xcodiq.taterunner.asset.image.TateImages;
import com.xcodiq.taterunner.asset.color.TateColors;
import com.xcodiq.taterunner.manager.implementation.ScreenManager;
import com.xcodiq.taterunner.screen.TateGameScreen;
import com.xcodiq.taterunner.screen.button.implementation.cosmetic.CosmeticShopButton;
import com.xcodiq.taterunner.screen.button.implementation.ExitButton;
import com.xcodiq.taterunner.screen.button.implementation.OptionsButton;
import com.xcodiq.taterunner.screen.keystroke.Keystroke;
import com.xcodiq.taterunner.util.animation.ImageAnimation;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.graphics.ShapeRenderer;

import java.awt.*;
import java.awt.event.KeyEvent;

public final class SplashScreen extends TateGameScreen {

	private final Font subtitleFont;

	private final ImageAnimation kakashiAnimation;
	private double kakashiX = -300;
	private boolean showKakashi = false;

	public SplashScreen(TateRunnerGame tateRunner) {
		super(tateRunner, "Splash");

		// Set up font
		this.subtitleFont = TateFonts.SECONDARY_SUBTITLE.toFont();

		// Create all the buttons for this screen
		this.addButton(new ExitButton(20, 20));
		this.addButton(new OptionsButton(165+30, 20));
		this.addButton(new CosmeticShopButton(tateRunner, 310 + 60, 20));

		// Initialize the player
		this.kakashiAnimation = new ImageAnimation("kakashi", 24, 100, 200, 200);
	}

	@Override
	public void render() {
		// Set background test
		this.drawBackgroundImage(TateImages.DARK_FOREST_BACKGROUND.toImage());

		// Randomly let kakashi walk on the screen
		this.randomlyLetKakashiWalk();

		// Draw a fullscreen rectangle on with an alpha
		this.graphics.setColor(new Color(0, 0, 0, TateColors.HIGH_BACKGROUND_ALPHA));
		ShapeRenderer.render(this.graphics, new Rectangle(1920, 1080), 0, 0);

		// Draw the title
		final String title = "TATE RUNNER";
		this.drawCenteredText(0, -45, Color.decode("#034954"), 120f, title); // shadow
		this.drawCenteredText(0, -50, Color.decode("#2be3ff"), 120f, title); // text

		// Draw the subtitle
		this.drawCenteredAnimatedText(0, -10, 550, this.subtitleFont, Color.WHITE, 25f,
				">  Press SPACE to enter the game  <",
				">  Press SPACE to enter the game  <",
				"> Press SPACE to enter the game <");

		// Draw the buttons
		this.getButtons().forEach(button -> button.render(this));
	}

	@Keystroke
	public void keystroke(KeyEvent keyEvent) {
		switch (keyEvent.getKeyCode()) {
			case KeyEvent.VK_SPACE ->
				// Switch to the Runner screen
					this.tateRunner.getManager(ScreenManager.class).showScreen(RunnerScreen.class);
			case KeyEvent.VK_DELETE ->
				// Exit the actual application
					System.exit(0);
		}
	}

	private void randomlyLetKakashiWalk() {
		if (!showKakashi && Game.time().now() % 400 == 0) this.showKakashi = true;
		if (!this.showKakashi) return;

		this.kakashiX += 12;
		this.drawImage(this.kakashiX, (777 * TateRunnerGame.IMAGE_SCALE) - 200, this.kakashiAnimation.getCurrentFrame());

		if (this.kakashiX > TateRunnerGame.WIDTH + 100) {
			this.showKakashi = false;
			this.kakashiX = -300;
		}
	}
}
