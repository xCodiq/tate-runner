package com.xcodiq.taterunner.entity.implementation;

import com.xcodiq.taterunner.entity.bound.annotation.BoundContext;
import com.xcodiq.taterunner.entity.bound.type.BoundType;
import com.xcodiq.taterunner.entity.sprite.AnimatedSpriteEntity;
import com.xcodiq.taterunner.entity.sprite.SpriteEntity;
import com.xcodiq.taterunner.util.animation.ImageAnimation;
import com.xcodiq.taterunner.util.image.ImageUtil;


@BoundContext(
        boundType = BoundType.POLYGON,
        boundPath = "sprites/coins/coins.bounds")
        public final class Coins extends SpriteEntity {

    public Coins(double startingX, double startingY, int width, int height) {
        super(ImageUtil.loadImage("sprites/coins/coins_texture.png", width,height), startingX, startingY);
    }

}
