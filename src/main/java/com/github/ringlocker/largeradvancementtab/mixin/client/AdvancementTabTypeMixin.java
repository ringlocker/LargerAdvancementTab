package com.github.ringlocker.largeradvancementtab.mixin.client;

import com.github.ringlocker.largeradvancementtab.LargerAdvancementTab;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screens.advancements.AdvancementTabType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(AdvancementTabType.class)
public class AdvancementTabTypeMixin {

    @Shadow @Final @Mutable private int width;
    @Shadow @Final @Mutable private int height;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void init(String string, int i, AdvancementTabType.Sprites sprites, AdvancementTabType.Sprites sprites2, int j, int k, int l, CallbackInfo ci) {
        if (((AdvancementTabType)(Object)this).name().equals("ABOVE")) {
            this.width = (int)(width * LargerAdvancementTab.MULTIPLIER);
            this.height = (int)(height * LargerAdvancementTab.MULTIPLIER);
        }
    }

    @ModifyArg(method = "drawIcon", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;renderFakeItem(Lnet/minecraft/world/item/ItemStack;II)V"), index = 1)
    private int modifyX(int i) {
        return (int) (i + (13F * (LargerAdvancementTab.MULTIPLIER - 1.0F)));
    }

    @ModifyArg(method = "drawIcon", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;renderFakeItem(Lnet/minecraft/world/item/ItemStack;II)V"), index = 2)
    private int modifyY(int i) {
        return (int) (i + (13F * (LargerAdvancementTab.MULTIPLIER - 1.0F)));
    }

}
