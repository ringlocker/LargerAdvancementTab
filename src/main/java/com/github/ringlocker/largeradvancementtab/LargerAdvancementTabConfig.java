package com.github.ringlocker.largeradvancementtab;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = "assets/largeradvancementtab")
public class LargerAdvancementTabConfig implements ConfigData {

    public float advancementTabSizeMultiplier = 2.0F;
    public boolean dimBackgroundOnAdvancementHover = false;


}
