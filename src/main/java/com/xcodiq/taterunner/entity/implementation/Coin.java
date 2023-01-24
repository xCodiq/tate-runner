package com.xcodiq.taterunner.entity.implementation;

import com.xcodiq.taterunner.entity.bound.annotation.BoundContext;
import com.xcodiq.taterunner.entity.bound.type.BoundType;
import com.xcodiq.taterunner.entity.sprite.AnimatedSpriteEntity;
import com.xcodiq.taterunner.util.animation.ImageAnimation;

@BoundContext(boundType = BoundType.BOX)
public final class Coin extends AnimatedSpriteEntity {

    public Coin(double startingX, double startingY) {
        super(new ImageAnimation("coin", 36,75, 150,150), startingX, startingY);
    }
}
