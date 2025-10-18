package com.github.ringlocker.largeradvancementtab;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;

public class LargerAdvancementTab implements ModInitializer {

    public static LargerAdvancementTabConfig config;

    @Override
    public void onInitialize() {
        AutoConfig.register(LargerAdvancementTabConfig.class, GsonConfigSerializer::new);
        config = AutoConfig.getConfigHolder(LargerAdvancementTabConfig.class).getConfig();
    }

}
