package com.xcodiq.taterunner.screen.implementation;

import com.xcodiq.taterunner.TateRunnerGame;
import com.xcodiq.taterunner.asset.color.TateColors;
import com.xcodiq.taterunner.asset.image.TateImages;
import com.xcodiq.taterunner.manager.implementation.ScreenManager;
import com.xcodiq.taterunner.screen.TateGameScreen;
import com.xcodiq.taterunner.screen.button.ReturnButton;
import com.xcodiq.taterunner.screen.keystroke.Keystroke;
import de.gurkenlabs.litiengine.graphics.ShapeRenderer;

import java.awt.*;
import java.awt.event.KeyEvent;

public final class CosmeticShopScreen extends TateGameScreen {

	private ShopState shopState = ShopState.MAIN_MENU;

	public CosmeticShopScreen(TateRunnerGame tateRunner) {
		super(tateRunner, "Cosmetic Shop");

		this.addButton(new ReturnButton(20, 20, mouseEvent ->
				tateRunner.getManager(ScreenManager.class).showScreen(SplashScreen.class)));
	}

	@Override
	public void render() {
		switch (this.shopState) {
			case MAIN_MENU -> {
			}
			case SPRITES_MENU -> {
				//d
			}
			case SCENES_MENU -> {
				//f
			}
		}

		// Draw the market background
		this.drawBackgroundImage(TateImages.MARKET_BACKGROUND.toImage());

		// Draw a fullscreen rectangle on with an alpha
		this.graphics.setColor(new Color(0, 0, 0, TateColors.LOW_BACKGROUND_ALPHA));
		ShapeRenderer.render(this.graphics, new Rectangle(1920, 1080), 0, 0);

		this.graphics.setColor(new Color(248, 149, 68, (int) (255 * 0.37)));
		ShapeRenderer.render(this.graphics, new Rectangle(1920, 75), 0, 94);

		// Draw the title
		final String title = "COSMETIC SHOP";
		this.drawCenteredText(0, -255, Color.decode("#543603"), 120f, title); // shadow
		this.drawCenteredText(0, -260, Color.decode("#ffb52b"), 120f, title); // text

		this.getButtons().forEach(button -> button.render(this));
	}

	@Keystroke
	public void keyStroke(KeyEvent keyEvent) {
	}

	private enum ShopState {
		MAIN_MENU,
		SPRITES_MENU,
		SCENES_MENU;
	}
}
