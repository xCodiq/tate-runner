package com.xcodiq.taterunner.screen.implementation;

import com.xcodiq.taterunner.TateRunnerGame;
import com.xcodiq.taterunner.asset.color.TateColors;
import com.xcodiq.taterunner.asset.font.TateFonts;
import com.xcodiq.taterunner.asset.image.TateImages;
import com.xcodiq.taterunner.asset.scene.TateScenes;
import com.xcodiq.taterunner.asset.sound.TateSounds;
import com.xcodiq.taterunner.manager.implementation.ProfileManager;
import com.xcodiq.taterunner.manager.implementation.ScreenManager;
import com.xcodiq.taterunner.screen.TateGameScreen;
import com.xcodiq.taterunner.screen.button.implementation.ExitButton;
import com.xcodiq.taterunner.screen.button.implementation.cosmetic.CosmeticShopButton;
import com.xcodiq.taterunner.screen.keystroke.Keystroke;
import com.xcodiq.taterunner.util.animation.ImageAnimation;
import de.gurkenlabs.litiengine.Game;

import java.awt.*;
import java.awt.event.KeyEvent;

public final class SplashScreen extends TateGameScreen {

	private final Font subtitleFont;

	private final ImageAnimation kakashiAnimation;
	private final int kakashiHeight = 200;
	private double kakashiX = -300;
	private boolean showKakashi = false;

	public SplashScreen(TateRunnerGame tateRunner) {
		super(tateRunner, "Splash");

		// Set up font
		this.subtitleFont = TateFonts.SECONDARY_SUBTITLE.toFont();

		// Create all the buttons for this screen
		this.addButton(new ExitButton(20, 20, button -> System.exit(0)));
		this.addButton(new CosmeticShopButton(tateRunner, 198, 20));

		// Initialize a new animation for the kakashi image that randomly appears on the screen
		this.kakashiAnimation = new ImageAnimation("kakashi", 24, 100, kakashiHeight, kakashiHeight);
	}

	@Override
	public void render() {
		// Play the main theme, looped
		TateSounds.MAIN_THEME.playOnBackground();

		// Set background test
		this.drawBackgroundImage(TateImages.DARK_FOREST_BACKGROUND.toImage());

		// Randomly let kakashi walk on the screen
		this.randomlyLetKakashiWalk();

		// Draw a fullscreen rectangle on with an alpha
		this.drawFullscreenCover(new Color(0, 0, 0, TateColors.HIGH_BACKGROUND_ALPHA));

		// Draw the title
		final String title = "TATE RUNNER";
		this.drawCenteredText(0, -45, Color.decode("#034954"), 160f, title); // shadow
		this.drawCenteredText(0, -50, Color.decode("#2be3ff"), 160f, title); // text

		// Draw the subtitle
		this.drawCenteredAnimatedText(0, -10, 550, this.subtitleFont, Color.WHITE, 30f,
				">  Press SPACE to enter the game  <",
				">  Press SPACE to enter the game  <",
				"> Press SPACE to enter the game <");

		// Render all the buttons
		this.renderAllButtons();
	}

	@Override
	public void preShow() {
	}

	@Keystroke
	public void keystroke(KeyEvent keyEvent) {
		switch (keyEvent.getKeyCode()) {
			case KeyEvent.VK_SPACE ->
				// Switch to the Runner screen
					this.tateRunner.getManager(ScreenManager.class).showScreen(RunnerScreen.class);
			case KeyEvent.VK_DELETE -> {
				// Exit the actual application with a clear profile
				this.tateRunner.getManager(ProfileManager.class).clearProfile();
				System.exit(0);
			}
		}
	}

	private void randomlyLetKakashiWalk() {
		if (!showKakashi && Game.time().now() % 400 == 0) this.showKakashi = true;
		if (!this.showKakashi) return;

		this.kakashiX += 12;
		this.drawImage(this.kakashiX, TateScenes.DARK_FOREST.getFloorCoordinate(),
				this.kakashiAnimation.getCurrentFrame());

		if (this.kakashiX > TateRunnerGame.GAME_WIDTH + this.kakashiHeight) {
			this.showKakashi = false;
			this.kakashiX = -300;
		}
	}
}
