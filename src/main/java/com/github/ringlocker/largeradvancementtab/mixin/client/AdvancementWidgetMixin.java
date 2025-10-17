package com.github.ringlocker.largeradvancementtab.mixin.client;

import com.github.ringlocker.largeradvancementtab.LargerAdvancementTab;
import net.minecraft.client.gui.screens.advancements.AdvancementWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(AdvancementWidget.class)
public class AdvancementWidgetMixin {

    @ModifyConstant(method = "<init>", constant = @Constant(floatValue = 27.0F))
    public float modifyGapX(float original) {
        return original + 5 * LargerAdvancementTab.GAP_MULTIPLIER;
    }

    @ModifyConstant(method = "<init>", constant = @Constant(floatValue = 28.0F))
    public float modifyGapY(float original) {
        return original + 5 * LargerAdvancementTab.GAP_MULTIPLIER;
    }

    @ModifyConstant(method = "drawConnectivity", constant = @Constant(intValue = 4))
    public int modifyGapY(int original) {
        return Math.round((float) original * LargerAdvancementTab.GAP_MULTIPLIER);
    }

}
