package com.xcodiq.taterunner.asset.font;

import com.xcodiq.taterunner.asset.Asset;

import java.awt.*;

public interface TateFont extends Asset {

	float getDefaultFontSize();

	Font toFont();

	Font toFont(float size);
}
