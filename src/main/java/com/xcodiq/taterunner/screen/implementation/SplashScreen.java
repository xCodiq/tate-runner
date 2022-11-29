package com.xcodiq.taterunner.screen.implementation;

import com.xcodiq.taterunner.TateRunnerGame;
import com.xcodiq.taterunner.keystroke.Keystroke;
import com.xcodiq.taterunner.manager.implementation.ScreenManager;
import com.xcodiq.taterunner.screen.TateGameScreen;
import com.xcodiq.taterunner.screen.button.implementation.StoreButton;
import de.gurkenlabs.litiengine.resources.Resources;

import java.awt.*;
import java.awt.event.KeyEvent;

public final class SplashScreen extends TateGameScreen {

	private final Font subtitleFont;

	public SplashScreen(TateRunnerGame tateRunner) {
		super(tateRunner, "Splash");

		// Set up font
		this.subtitleFont = Resources.fonts().get("font/slkscr.ttf");

		// Create all the buttons for this screen
		this.addButton(new StoreButton(tateRunner));
	}

	@Override
	public void render() {
		// Draw the title
		final String title = "TATE RUNNER";
		this.drawCenteredText(0, 5, Color.decode("#034954"), 100f, title); // shadow
		this.drawCenteredText(Color.decode("#2be3ff"), 100f, title); // text

		// Draw the subtitle
		this.drawCenteredAnimatedText(0, 40, 550, this.subtitleFont, Color.WHITE, 20f,
				">  Press SPACE to enter the game  <",
				">  Press SPACE to enter the game  <",
				"> Press SPACE to enter the game <");

		// Draw the buttons
		this.getButtons().forEach(button -> button.render(this));
	}

	@Keystroke
	public void keystroke(KeyEvent keyEvent) {
		switch (keyEvent.getKeyCode()) {
			case KeyEvent.VK_SPACE -> {
				// Switch to the Runner screen
				this.tateRunner.getManager(ScreenManager.class).showScreen(RunnerScreen.class);
			}
			case KeyEvent.VK_DELETE ->
					// Exit the actual application
					System.exit(0);
		}
	}
}
