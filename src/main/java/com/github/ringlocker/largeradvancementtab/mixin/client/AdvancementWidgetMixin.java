package com.github.ringlocker.largeradvancementtab.mixin.client;

import com.github.ringlocker.largeradvancementtab.LargerAdvancementTab;
import net.minecraft.client.gui.screens.advancements.AdvancementWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(AdvancementWidget.class)
public class AdvancementWidgetMixin {

    @ModifyConstant(method = "<init>", constant = @Constant(floatValue = 27.0F))
    public float modifyGapX(float original) {
        return original + 5 * gapMultiplier();
    }

    @ModifyConstant(method = "<init>", constant = @Constant(floatValue = 28.0F))
    public float modifyGapY(float original) {
        return original + 5 * gapMultiplier();
    }

    @ModifyConstant(method = "drawConnectivity", constant = @Constant(intValue = 4))
    public int modifyGapY(int original) {
        return Math.round((float) original * gapMultiplier());
    }

    @Unique
    private static float gapMultiplier() {
        return Math.clamp(1 * (LargerAdvancementTab.config.advancementTabSizeMultiplier / 2F), 1.0F, 2.0F);
    }

}
