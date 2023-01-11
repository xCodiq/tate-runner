package com.xcodiq.taterunner.screen.button.implementation;

import com.xcodiq.taterunner.TateRunnerGame;
import com.xcodiq.taterunner.manager.implementation.ScreenManager;
import com.xcodiq.taterunner.screen.button.Button;
import com.xcodiq.taterunner.screen.implementation.CosmeticShopScreen;
import com.xcodiq.taterunner.util.image.ImageUtil;

public final class CosmeticShopButton extends Button {

	public CosmeticShopButton(TateRunnerGame tateRunner, double x, double y) {
		super(ImageUtil.loadImage("textures/button/cosmetics/cosmetics-unfocussed.png",115, 45),
				ImageUtil.loadImage("textures/button/cosmetics/cosmetics-focussed.png",115, 45),
				x, y);

		this.setClickEvent(mouseEvent -> {
			tateRunner.getManager(ScreenManager.class).showScreen(CosmeticShopScreen.class);
		});
	}
}
