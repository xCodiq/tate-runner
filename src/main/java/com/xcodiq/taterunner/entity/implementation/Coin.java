package com.xcodiq.taterunner.entity.implementation;

import com.xcodiq.taterunner.entity.bound.annotation.BoundContext;
import com.xcodiq.taterunner.entity.bound.type.BoundType;
import com.xcodiq.taterunner.entity.sprite.AnimatedSpriteEntity;
import com.xcodiq.taterunner.util.animation.ImageAnimation;

@BoundContext(boundType = BoundType.POLYGON, boundPath = "sprites/coin/coin.bounds")
public final class Coin extends AnimatedSpriteEntity {

    public Coin(double startingX, double startingY) {
        super(new ImageAnimation("coin", 36,75, 70,70), startingX, startingY);
    }
}
