package com.xcodiq.taterunner.screen.button.implementation.cosmetic;

import com.xcodiq.taterunner.TateRunnerGame;
import com.xcodiq.taterunner.asset.sound.TateSounds;
import com.xcodiq.taterunner.manager.implementation.ScreenManager;
import com.xcodiq.taterunner.screen.button.StaticButton;
import com.xcodiq.taterunner.screen.implementation.CosmeticShopScreen;
import com.xcodiq.taterunner.util.image.ImageUtil;

public final class CosmeticShopButton extends StaticButton {

	public CosmeticShopButton(TateRunnerGame tateRunner, double x, double y) {
		super(ImageUtil.loadImage("textures/button/cosmetics/cosmetics-unfocussed.png", 138, 54),
				ImageUtil.loadImage("textures/button/cosmetics/cosmetics-focussed.png",138, 54),
				x, y);

		this.setClickAction(button -> {
			tateRunner.getManager(ScreenManager.class).showScreen(CosmeticShopScreen.class);
			TateSounds.BUTTON_CLICK.play();
		});
	}
}
