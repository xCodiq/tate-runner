package com.xcodiq.taterunner.entity.implementation;

import com.xcodiq.taterunner.entity.bound.annotation.BoundContext;
import com.xcodiq.taterunner.entity.bound.type.BoundType;
import com.xcodiq.taterunner.entity.sprite.AnimatedSpriteEntity;
import com.xcodiq.taterunner.entity.sprite.SpriteEntity;
import com.xcodiq.taterunner.util.animation.ImageAnimation;
import com.xcodiq.taterunner.util.image.ImageUtil;

@BoundContext(
        boundType = BoundType.POLYGON,
        boundPath = "sprites/bat/bat.bounds")
public final class Bat extends AnimatedSpriteEntity {

    public Bat(double startingX, double startingY, int width, int height) {
        super(new ImageAnimation("bat", 4,100, width, height), startingX, startingY);
    }
}
