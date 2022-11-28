package com.xcodiq.taterunner.screen.implementation;

import com.xcodiq.taterunner.TateRunnerGame;
import com.xcodiq.taterunner.keystroke.Keystroke;
import com.xcodiq.taterunner.manager.implementation.ScreenManager;
import com.xcodiq.taterunner.manager.implementation.StateManager;
import com.xcodiq.taterunner.screen.TateGameScreen;
import com.xcodiq.taterunner.state.State;

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
		this.drawCenteredAnimatedText(0, 35, 550, Color.WHITE, 20f,
				">  Press SPACE to enter the game  <",
				">  Press SPACE to enter the game  <",
				"> Press SPACE to enter the game <");
	}

	@Keystroke
	public void keystroke(KeyEvent keyEvent) {
		switch (keyEvent.getKeyCode()) {
			case KeyEvent.VK_SPACE -> {
				// Set the current state as running
				this.tateRunner.getManager(StateManager.class).setCurrentState(State.RUNNING);

				// Switch to the Runner screen
				this.tateRunner.getManager(ScreenManager.class).showScreen(RunnerScreen.class);
			}
			case KeyEvent.VK_DELETE ->
				// Exit the actual application
					System.exit(0);
		}
	}
}
