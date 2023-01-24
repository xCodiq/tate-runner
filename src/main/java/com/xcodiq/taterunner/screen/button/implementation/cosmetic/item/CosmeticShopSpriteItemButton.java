package com.xcodiq.taterunner.screen.button.implementation.cosmetic.item;

import com.xcodiq.taterunner.screen.button.StatedButton;

import java.util.List;

public class CosmeticShopSpriteItemButton extends StatedButton {

	public CosmeticShopSpriteItemButton(double x, double y) {
		super(List.of(ShopItemButtonState.values()), x, y, 346, 56);
	}
}
