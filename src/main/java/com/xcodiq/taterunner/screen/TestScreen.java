package com.xcodiq.taterunner.screen;

import com.xcodiq.taterunner.TateRunnerGame;
import com.xcodiq.taterunner.keystroke.Keystroke;
import com.xcodiq.taterunner.manager.ScreenManager;

import java.awt.*;
import java.awt.event.KeyEvent;

public final class TestScreen extends TateGameScreen {

	public TestScreen(TateRunnerGame tateRunner) {
		super(tateRunner, "Test");
	}

	@Override
	public void render() {
		this.drawCenteredText(Color.WHITE, 30f, "This is a test screen!");
	}

	@Keystroke
	public void keystroke(KeyEvent keyEvent) {
		if (keyEvent.getKeyCode() == KeyEvent.VK_ESCAPE) {
			final ScreenManager screenManager = this.tateRunner.getManager(ScreenManager.class);
			screenManager.showScreen(SplashScreen.class);
		}
	}
}
