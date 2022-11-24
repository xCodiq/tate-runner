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
		this.drawCenteredText(Color.decode("#2be3ff"), 60f, "WELCOME TO TATE RUNNER");

		// Draw the subtitle
		this.drawCenteredAnimatedText(0, 35, 3000, Color.WHITE, 20f,
				"Tourner dans le vide, vide",
				"Tourner dans le vide, il me fait tourner",
				"Dans le vide, vide, vide",
				"Tourner, tourner dans le vide",
				"Tourner dans le vide, il me fait tourner");
	}

	@Keystroke
	public void keystroke(KeyEvent keyEvent) {
		if (keyEvent.getKeyCode() == KeyEvent.VK_ESCAPE) {
			this.tateRunner.getManager(ScreenManager.class).showScreen(TestScreen.class);

		} else if (keyEvent.getKeyCode() == KeyEvent.VK_DELETE) {
			// Exit the actual application
			System.exit(0);
		}
	}
}
