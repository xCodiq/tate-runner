package com.xcodiq.taterunner.screen.implementation;

import com.xcodiq.taterunner.TateRunnerGame;
import com.xcodiq.taterunner.asset.color.TateColors;
import com.xcodiq.taterunner.asset.image.TateImages;
import com.xcodiq.taterunner.manager.implementation.ScreenManager;
import com.xcodiq.taterunner.screen.TateGameScreen;
import com.xcodiq.taterunner.screen.button.implementation.ReturnButton;
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
	}

	@Override
	public void render() {
		// Draw the market background
		this.drawBackgroundImage(TateImages.MARKET_BACKGROUND.toImage());

		// Draw a fullscreen rectangle on with an alpha
		this.graphics.setColor(new Color(0, 0, 0, TateColors.MEDIUM_BACKGROUND_ALPHA));
		ShapeRenderer.render(this.graphics, new Rectangle(1920, 1080), 0, 0);

		// Draw the header rectangle on with alpha
		this.graphics.setColor(new Color(248, 149, 68, TateColors.MEDIUM_BACKGROUND_ALPHA));
		ShapeRenderer.render(this.graphics, new Rectangle(1920, 75), 0, 108 * TateRunnerGame.IMAGE_SCALE);

		// Draw the title
		final String title = "COSMETIC SHOP";
		this.drawCenteredText(0, (int) (-330 * TateRunnerGame.IMAGE_SCALE), Color.decode("#543603"), 120f, title); // shadow
		this.drawCenteredText(0, (int) (-335 * TateRunnerGame.IMAGE_SCALE), Color.decode("#ffb52b"), 120f, title); // text

		// Render the return button
		this.renderButton(ReturnButton.class);

		// Draw things according to the shop state
		switch (this.shopState) {
			case MAIN_MENU -> {
				// Draw the two category button borders
				this.graphics.setColor(new Color(248, 149, 68, TateColors.LOW_BACKGROUND_ALPHA));
				ShapeRenderer.render(this.graphics, new Rectangle((int) (500 * TateRunnerGame.IMAGE_SCALE), (int) (500 * TateRunnerGame.IMAGE_SCALE)), (250 + 150) * TateRunnerGame.IMAGE_SCALE, 360 * TateRunnerGame.IMAGE_SCALE);

				this.graphics.setColor(new Color(248, 149, 68, TateColors.LOW_BACKGROUND_ALPHA));
				ShapeRenderer.render(this.graphics, new Rectangle((int) (500 * TateRunnerGame.IMAGE_SCALE), (int) (500 * TateRunnerGame.IMAGE_SCALE)), (1170 - 150) * TateRunnerGame.IMAGE_SCALE, 360 * TateRunnerGame.IMAGE_SCALE);

				this.renderButton(CosmeticSpritesButton.class);
			}
			case SPRITES_MENU -> {
//				this.drawText();
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
