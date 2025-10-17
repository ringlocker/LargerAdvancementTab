package com.github.ringlocker.largeradvancementtab;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class LargerAdvancementTab implements ModInitializer {

    public static final float MULTIPLIER = 2.0F;
    public static final float GAP_MULTIPLIER = Math.clamp(1 * (MULTIPLIER / 2F), 1.0F, 2.0F);

    @Override
    public void onInitialize() {
        if (FabricLoader.getInstance().isModLoaded("cloth-config2")) {
            AutoConfig.register(ModConfig.class, GsonConfigSerializer::new);
            ModConfig config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
            MULTIPLIER = config.titleMultiplier;
        }
    }

}
