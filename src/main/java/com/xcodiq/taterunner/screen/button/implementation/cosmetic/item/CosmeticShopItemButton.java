package com.xcodiq.taterunner.screen.button.implementation.cosmetic.item;

import com.xcodiq.taterunner.screen.button.StatedButton;
import com.xcodiq.taterunner.screen.button.model.ButtonState;
import com.xcodiq.taterunner.util.image.ImageUtil;
import com.xcodiq.taterunner.util.multiple.Pair;

import java.awt.image.BufferedImage;
import java.util.Locale;

public class CosmeticShopItemButton extends StatedButton {

	public CosmeticShopItemButton(double x, double y) {
		super(State.values(), x, y, 346, 56);
	}

	public enum State implements ButtonState {
		EQUIPPED(new Pair<>(
				ImageUtil.loadImage("textures/button/cosmeticshop/item/equipped/equipped-item-unfocussed.png"),
				ImageUtil.loadImage("textures/button/cosmeticshop/item/equipped/equipped-item-unfocussed.png")
		)),
		PURCHASE(new Pair<>(
				ImageUtil.loadImage("textures/button/cosmeticshop/item/purchase/purchase-item-unfocussed.png"),
				ImageUtil.loadImage("textures/button/cosmeticshop/item/purchase/purchase-item-focussed.png")
		)),
		EQUIP(new Pair<>(
				ImageUtil.loadImage("textures/button/cosmeticshop/item/equip/equip-item-unfocussed.png"),
				ImageUtil.loadImage("textures/button/cosmeticshop/item/equip/equip-item-focussed.png")
		));

		private final Pair<BufferedImage, BufferedImage> images;

		State(Pair<BufferedImage, BufferedImage> images) {
			this.images = images;
		}

		@Override
		public String getName() {
			return this.name().toUpperCase(Locale.ROOT);
		}

		@Override
		public Pair<BufferedImage, BufferedImage> getImages() {
			return this.images;
		}
	}
}
