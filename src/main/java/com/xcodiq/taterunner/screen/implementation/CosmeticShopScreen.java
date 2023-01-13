package com.xcodiq.taterunner.screen.implementation;

import com.xcodiq.taterunner.TateRunnerGame;
import com.xcodiq.taterunner.asset.color.TateColors;
import com.xcodiq.taterunner.asset.image.TateImages;
import com.xcodiq.taterunner.manager.implementation.ScreenManager;
import com.xcodiq.taterunner.screen.TateGameScreen;
import com.xcodiq.taterunner.screen.button.implementation.ReturnButton;
import com.xcodiq.taterunner.screen.button.implementation.cosmetic.CosmeticScenesButton;
import com.xcodiq.taterunner.screen.button.implementation.cosmetic.CosmeticSpritesButton;
import com.xcodiq.taterunner.screen.keystroke.Keystroke;
import de.gurkenlabs.litiengine.graphics.ShapeRenderer;

import java.awt.*;
import java.awt.event.KeyEvent;

public final class CosmeticShopScreen extends TateGameScreen {

	private ShopState shopState = ShopState.MAIN_MENU;

	public CosmeticShopScreen(TateRunnerGame tateRunner) {
		super(tateRunner, "Cosmetic Shop");

		this.addButton(new ReturnButton(20, 20, mouseEvent -> {
			switch (this.shopState) {
				case MAIN_MENU ->
					// Show the splash screen again
						tateRunner.getManager(ScreenManager.class).showScreen(SplashScreen.class);
				case SPRITES_MENU, SCENES_MENU ->
					// Switch the state back to main menu state
						this.shopState = ShopState.MAIN_MENU;
			}
		}));

		this.addButton(new CosmeticSpritesButton(496, 380, mouseEvent -> {
			this.shopState = ShopState.SPRITES_MENU;
		}));

		this.addButton(new CosmeticScenesButton(1120, 380, mouseEvent -> {
			this.shopState = ShopState.SCENES_MENU;
		}));
	}

	@Override
	public void render() {
		// Draw the market background
		this.drawBackgroundImage(TateImages.MARKET_BACKGROUND.toImage());

		// Draw a fullscreen rectangle on with an alpha
		this.drawFullscreenCover(new Color(0, 0, 0, TateColors.MEDIUM_BACKGROUND_ALPHA));

		// Draw the header rectangle on with alpha
		this.drawRectangle(0, 110, new Rectangle(1920, 75),
				new Color(248, 149, 68, TateColors.MEDIUM_BACKGROUND_ALPHA));

		// Draw the title
		final String title = "COSMETIC SHOP";
		this.drawCenteredText(0, -330, Color.decode("#543603"), 140f, title); // shadow
		this.drawCenteredText(0, -335, Color.decode("#ffb52b"), 140f, title); // text

		// Render the return button
		this.renderButton(ReturnButton.class);

		// Draw things according to the shop state
		switch (this.shopState) {
			case MAIN_MENU -> {
				final Color orangeBoxColor = new Color(248, 149, 68, TateColors.LOW_BACKGROUND_ALPHA);

				// Draw the two category button borders
				this.drawRectangle(400, 360, new Rectangle(500, 500), orangeBoxColor);
				this.drawRectangle(1020, 360, new Rectangle(500, 500), orangeBoxColor);

				// Render the two category buttons
				this.renderButton(CosmeticSpritesButton.class);
				this.renderButton(CosmeticScenesButton.class);
			}
			case SPRITES_MENU -> {
				//s
			}
			case SCENES_MENU -> {
				//f
			}
		}
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
