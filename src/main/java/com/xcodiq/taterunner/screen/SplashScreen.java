package com.xcodiq.taterunner.screen;

import com.xcodiq.taterunner.TateRunnerGame;
import com.xcodiq.taterunner.keystroke.Keystroke;
import com.xcodiq.taterunner.manager.ScreenManager;

import java.awt.*;
import java.awt.event.KeyEvent;

public final class SplashScreen extends TateGameScreen {

	public SplashScreen(TateRunnerGame tateRunner) {
		super(tateRunner, "Splash");
	}

	@Override
	public void render() {
		// Draw the title
		final String title = "WELCOME TO TATE RUNNER";
		this.drawCenteredText(0, 5, Color.decode("#034954"), 60f, title); // shadow
		this.drawCenteredText(Color.decode("#2be3ff"), 60f, title); // text

		// Draw the subtitle
		this.drawCenteredAnimatedText(0, 35, 350, Color.WHITE, 20f,
				">  Press SPACE to enter the game  <",
				">  Press SPACE to enter the game  <",
				">  Press SPACE to enter the game  <",
				"> Press SPACE to enter the game <");
	}

	@Keystroke
	public void keystroke(KeyEvent keyEvent) {
		if (keyEvent.getKeyCode() == KeyEvent.VK_SPACE) {
			this.tateRunner.getManager(ScreenManager.class).showScreen(TestScreen.class);

		} else if (keyEvent.getKeyCode() == KeyEvent.VK_DELETE) {
			// Exit the actual application
			System.exit(0);
		}
	}
}
