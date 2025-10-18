package com.github.ringlocker.largeradvancementtab.mixin.client;

import com.github.ringlocker.largeradvancementtab.LargerAdvancementTab;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.advancements.AdvancementTabType;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(AdvancementTabType.class)
public class AdvancementTabTypeMixin {

    @Shadow @Final private int width;
    @Shadow @Final private int height;

    @Inject(method = "getX", at = @At("HEAD"), cancellable = true)
    private void getScaledX(int index, CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue((int) (this.getX2(index) * multiplier()));
    }

    @Inject(method = "getY", at = @At("HEAD"), cancellable = true)
    private void getScaledY(int index, CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue((int) (this.getY2(index) * multiplier()));
    }

    @ModifyArg(method = "draw", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/ResourceLocation;IIII)V"), index = 4)
    public int setWidthDrawTab(int x) {
        return scale(x);
    }
     @ModifyArg(method = "draw", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/ResourceLocation;IIII)V"), index = 5)
    public int setHeightDrawTab(int y) {
        return scale(y);
    }

    @Inject(method = "drawIcon", at = @At("HEAD"), cancellable = true)
    public void drawIcon(GuiGraphics guiGraphics, int i, int j, int k, ItemStack itemStack, CallbackInfo ci) {
        float multiplier = multiplier();

        int x = i + (int) (this.getX2(k) * multiplier);
        int y = j + (int) (this.getY2(k) * multiplier);

        switch (((AdvancementTabType) (Object) this)) {
            case ABOVE -> {
                x += (int) (6 * multiplier);
                y += (int) (9 * multiplier);
            }
            case BELOW -> {
                x += (int) (6 * multiplier);
                y += (int) (6 * multiplier);
            }
            case LEFT -> {
                x += (int) (10 * multiplier);
                y += (int) (5 * multiplier);
            }
            case RIGHT -> {
                x += (int) (6 * multiplier);
                y += (int) (5 * multiplier);
            }
        }

        guiGraphics.pose().pushMatrix();
        guiGraphics.pose().translate(x, y);
        guiGraphics.pose().scale(multiplier, multiplier);
        guiGraphics.renderFakeItem(itemStack, 0, 0);
        guiGraphics.pose().popMatrix();

        ci.cancel();
    }

    @Inject(method = "isMouseOver", at = @At("HEAD"), cancellable = true)
    public void isMouseOver(int i, int j, int k, double mouseX, double mouseY, CallbackInfoReturnable<Boolean> cir) {
        float mult = multiplier();

        double left = i + (this.getX2(k) * mult);
        double top  = j + (this.getY2(k) * mult);

        double right = left + (this.width * mult);
        double bottom = top + (this.height * mult);

        boolean inside = mouseX > left && mouseX < right
                && mouseY > top  && mouseY < bottom;

        cir.setReturnValue(inside);
        cir.cancel();
    }

    @Unique
    private int getX2(int index) {
        return switch (((AdvancementTabType) (Object) this)) {
            case ABOVE, BELOW -> (this.width + 4) * index;
            case LEFT -> -this.width + 4;
            case RIGHT -> 248;
        };
    }

    @Unique
    private int getY2(int index) {
        return switch (((AdvancementTabType) (Object) this)) {
            case ABOVE -> -this.height + 4;
            case BELOW -> 136;
            case LEFT, RIGHT -> (this.height + 4) * index;
        };
    }

    @Unique
    private static int scale(int original) {
        return (int) ((float) original * multiplier());
    }

    @Unique
    private static float multiplier() {
        return LargerAdvancementTab.config.advancementTabSizeMultiplier;
    }

}
