package com.xcodiq.taterunner.screen.implementation;

import com.xcodiq.taterunner.TateRunnerGame;
import com.xcodiq.taterunner.asset.color.TateColors;
import com.xcodiq.taterunner.asset.font.TateFonts;
import com.xcodiq.taterunner.asset.image.TateImages;
import com.xcodiq.taterunner.asset.sprite.TateSpriteRender;
import com.xcodiq.taterunner.asset.sprite.TateSprites;
import com.xcodiq.taterunner.manager.implementation.ProfileManager;
import com.xcodiq.taterunner.manager.implementation.ScreenManager;
import com.xcodiq.taterunner.profile.Profile;
import com.xcodiq.taterunner.screen.TateGameScreen;
import com.xcodiq.taterunner.screen.button.implementation.ReturnButton;
import com.xcodiq.taterunner.screen.button.implementation.cosmetic.CosmeticScenesButton;
import com.xcodiq.taterunner.screen.button.implementation.cosmetic.CosmeticSpritesButton;
import com.xcodiq.taterunner.screen.button.implementation.cosmetic.item.CosmeticShopItemButton;
import com.xcodiq.taterunner.screen.keystroke.Keystroke;
import com.xcodiq.taterunner.util.collection.ExpiringList;
import com.xcodiq.taterunner.util.text.ScreenText;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.concurrent.TimeUnit;

public final class CosmeticShopScreen extends TateGameScreen {

	private final ExpiringList<ScreenText> screenTexts = new ExpiringList<>(2, TimeUnit.SECONDS);
	private final Profile profile;

	private final int spritesAmount = TateSprites.values().length;
	private final int itemWidth = 400, itemHeight = 500, itemSpacing = 50;
	private final int maxMenuWidth = TateRunnerGame.GAME_WIDTH - itemWidth;
	private final int menuWidth = maxMenuWidth - (spritesAmount * itemWidth + (spritesAmount - 1) * itemSpacing);
	private final int startingX = ((TateRunnerGame.GAME_WIDTH - maxMenuWidth) / 2) + (menuWidth / 2);

	private final int itemContentOffset = 12;
	private ShopState shopState = ShopState.MAIN_MENU;

	public CosmeticShopScreen(TateRunnerGame tateRunner) {
		super(tateRunner, "Cosmetic Shop");

		// Fetch the profile
		this.profile = tateRunner.getManager(ProfileManager.class).getProfile();

		// Add the return button
		this.addButton(new ReturnButton(20, 20, button -> {
			switch (this.shopState) {
				case MAIN_MENU ->
					// Show the splash screen again
						tateRunner.getManager(ScreenManager.class).showScreen(SplashScreen.class);
				case SPRITES_MENU, SCENES_MENU ->
					// Switch the state back to main menu state
						this.shopState = ShopState.MAIN_MENU;
			}
		}));

		// Add the shop buttons
		this.addButton(new CosmeticSpritesButton(496, 380,
				button -> this.shopState = ShopState.SPRITES_MENU,
				clickCondition -> this.shopState == ShopState.MAIN_MENU));

		this.addButton(new CosmeticScenesButton(1120, 380,
				button -> this.shopState = ShopState.SCENES_MENU,
				clickCondition -> this.shopState == ShopState.MAIN_MENU));

		// Prepare the item buttons by looping through the sprites
		int buttonX = startingX;
		for (TateSprites sprite : TateSprites.values()) {
			final CosmeticShopItemButton shopItemButton = new CosmeticShopItemButton(buttonX + 25, 789);

			shopItemButton.setButtonRenderCondition(unused -> {
				if (profile.getEquippedTateSprite() == sprite) {
					return CosmeticShopItemButton.State.EQUIPPED;
				} else if (profile.getUnlockedTateSprites().contains(sprite)) {
					return CosmeticShopItemButton.State.EQUIP;
				} else {
					return CosmeticShopItemButton.State.PURCHASE;
				}
			});

			shopItemButton.setClickAction(button -> {
				if (button.isCurrentState(CosmeticShopItemButton.State.EQUIP)) {
					// Equip the sprite
					profile.setEquippedTateSprite(sprite);
					button.setCurrentState(CosmeticShopItemButton.State.EQUIPPED);

					final String text = "! Equipped " + sprite + " !";
					this.addScreenText(Color.CYAN, text);
				} else if (button.isCurrentState(CosmeticShopItemButton.State.PURCHASE)) {
					if (!this.profile.hasCoins(sprite.getPrice())) {
						final String text = "! Insufficient funds, you need "
											+ (sprite.getPrice() - this.profile.getCoins())
											+ " more coins !";
						this.addScreenText(Color.RED, text);
						return;
					}

					// Purchase the sprite
					profile.getUnlockedTateSprites().add(sprite);
					this.profile.removeCoins(sprite.getPrice());
					button.setCurrentState(CosmeticShopItemButton.State.EQUIP);

					final String text = "! Added " + sprite + " to your collection !";
					this.addScreenText(Color.GREEN, text);
				}
			});

			this.addButton(shopItemButton);
			buttonX += itemWidth + itemSpacing;
		}
	}

	@Override
	public void render() {
		// Draw the market background
		this.drawBackgroundImage(TateImages.MARKET_BACKGROUND.toImage());

		// Draw a fullscreen rectangle on with an alpha
		this.drawFullscreenCover(new Color(0, 0, 0, TateColors.MEDIUM_BACKGROUND_ALPHA));

		// Draw the header rectangle on with alpha
		this.drawRectangle(0, 110, new Rectangle(1920, 85),
				new Color(248, 149, 68, TateColors.MEDIUM_BACKGROUND_ALPHA));

		// Draw the title
		final String title = "COSMETIC SHOP";
		this.drawCenteredText(0, -330, Color.decode("#543603"), 140f, title); // shadow
		this.drawCenteredText(0, -335, Color.decode("#ffb52b"), 140f, title); // text

		// Draw the coins subtitle
		final String coinsSubTitle = "You currently have " + this.profile.getCoins() + " coins!";
		this.drawCenteredText(0, -300, TateFonts.SECONDARY_SUBTITLE.toFont(),
				Color.orange, 25f, coinsSubTitle);

		// Render the return button
		this.renderButton(ReturnButton.class);

		final Color orangeBoxColor = new Color(248, 149, 68, TateColors.LOW_BACKGROUND_ALPHA);
		// Draw things according to the shop state
		switch (this.shopState) {
			case MAIN_MENU -> {
				// Draw the two category button borders
				this.drawRectangle(400, 360, new Rectangle(500, 500), orangeBoxColor);
				this.drawRectangle(1020, 360, new Rectangle(500, 500), orangeBoxColor);

				// Render the two category buttons
				this.renderButton(CosmeticSpritesButton.class);
				this.renderButton(CosmeticScenesButton.class);
			}
			case SPRITES_MENU -> {
				int currentX = startingX;
				for (TateSprites sprite : TateSprites.values()) {
					// Draw a rectangle around the sprite
					this.drawRectangle(currentX, 360, new Rectangle(itemWidth, itemHeight), orangeBoxColor);

					// Draw the sprite name
					final int nameX = (currentX + (itemWidth / 2)) - (this.getStringWidth(sprite.toString(), TateFonts.SECONDARY_SUBTITLE, 30f) / 2) - itemContentOffset;
					this.drawText(nameX, 420, TateFonts.SECONDARY_SUBTITLE.toFont(), Color.decode("#ffb52b"), 30f, sprite.toString());

					// Draw the sprite render
					final TateSpriteRender render = sprite.getRender();
					final int renderX = (currentX + (itemWidth / 2)) + (render.getWidth() / 2) + itemContentOffset;
					this.drawImage(renderX, 720, render.getImage());

					// Draw the price if it's not owned yet
					if (!this.profile.ownsTateSprite(sprite)) {
						this.drawText(currentX + 25, 789 - 20, TateFonts.SECONDARY_SUBTITLE.toFont(),
								Color.decode("#ffb52b"), 30f, "$" + sprite.getPrice());
					}

					// Render the button for the sprite and update the current x
					this.renderButton(CosmeticShopItemButton.class);
					currentX += itemWidth + itemSpacing;
				}
			}
			case SCENES_MENU -> {
				//f
			}
		}

		// Render all the screen texts that will expire over time
		for (ScreenText screenText : screenTexts) {
			this.drawCenteredText(0, 400, TateFonts.SECONDARY_SUBTITLE.toFont(),
					screenText.getColor(), 30f, screenText.getText());
		}
	}

	@Keystroke
	public void keyStroke(KeyEvent keyEvent) {
		// Check if space is pressed
		if (keyEvent.getKeyCode() == KeyEvent.VK_UP) {
			this.profile.addCoins(100);

			final String text = "Added 100 to your account";
			this.addScreenText(Color.BLUE, text, 1, TimeUnit.SECONDS);
		} else if (keyEvent.getKeyCode() == KeyEvent.VK_DOWN) {
			if (!this.profile.hasCoins(100)) return;
			this.profile.removeCoins(100);

			final String text = "Removed 100 to your account";
			this.addScreenText(Color.BLUE, text, 1, TimeUnit.SECONDS);
		}
	}

	private void addScreenText(Color color, String text) {
		this.screenTexts.clear();
		this.screenTexts.add(new ScreenText(color, text));
	}

	private void addScreenText(Color color, String text, long duration, TimeUnit timeUnit) {
		this.screenTexts.clear();
		this.screenTexts.add(new ScreenText(color, text), duration, timeUnit);
	}

	private enum ShopState {
		MAIN_MENU,
		SPRITES_MENU,
		SCENES_MENU
	}
}
