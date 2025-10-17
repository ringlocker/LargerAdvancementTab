package com.github.ringlocker.largeradvancementtab.mixin.client;

import com.github.ringlocker.largeradvancementtab.LargerAdvancementTab;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.advancements.AdvancementTabType;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.github.ringlocker.largeradvancementtab.LargerAdvancementTab.MULTIPLIER;

@Environment(EnvType.CLIENT)
@Mixin(AdvancementTabType.class)
public class AdvancementTabTypeMixin {

    @Shadow @Final @Mutable private int width;
    @Shadow @Final @Mutable private int height;
    @Shadow public int getX(int i) { return 0; }
    @Shadow public int getY(int i) { return 0; }

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

    @Overwrite
    public void drawIcon(GuiGraphics guiGraphics, int i, int j, int k, ItemStack itemStack) {
        int x = i + this.getX(k);
        int y = j + this.getY(k);
        switch (((Enum<?>) (Object) this).ordinal()) {
            case 0:
                x += 6;
                y += 9;
                break;
            case 1:
                x += 6;
                y += 6;
                break;
            case 2:
                x += 10;
                y += 5;
                break;
            case 3:
                x += 6;
                y += 5;
        }

        guiGraphics.pose().pushMatrix();
        guiGraphics.pose().translate(x, y);
        guiGraphics.pose().scale(MULTIPLIER, MULTIPLIER);
        int x1 = - ((int) ((MULTIPLIER - 1.0F) * 10.0F));
        int y1 = -((int) ((MULTIPLIER - 1.0F) * 7.0F));
        guiGraphics.renderFakeItem(itemStack, x1, y1);
        guiGraphics.pose().popMatrix();
    }

}
