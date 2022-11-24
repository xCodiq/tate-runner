package com.xcodiq.taterunner.screen;

import java.awt.*;

public final class SplashScreen extends TateGameScreen {

	public SplashScreen() {
		super("Splash");
	}

	@Override
	public void render() {
		this.drawCenteredText(Color.decode("#2be3ff"), 60f, "WELCOME TO TATE RUNNER");
		this.drawCenteredAnimatedText(0, 35, 750, Color.WHITE, 20f,
				"Loading the game, please wait",
				"Loading the game, please wait.",
				"Loading the game, please wait..",
				"Loading the game, please wait...");
	}
}
