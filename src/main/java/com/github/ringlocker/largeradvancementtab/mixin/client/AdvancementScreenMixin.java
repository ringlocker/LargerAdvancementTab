package com.github.ringlocker.largeradvancementtab.mixin.client;

import com.github.ringlocker.largeradvancementtab.LargerAdvancementTab;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.advancements.AdvancementTab;
import net.minecraft.client.gui.screens.advancements.AdvancementsScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(AdvancementsScreen.class)
public class AdvancementScreenMixin {

    @Shadow private static final Component TITLE = Component.translatable("gui.advancements");
    @Shadow private AdvancementTab selectedTab;

    @ModifyConstant(method = "mouseClicked", constant = @Constant(intValue = 252))
    public int modifyWidthMouseClicked(int original) {
       return width();
    }

    @ModifyConstant(method = "mouseClicked", constant = @Constant(intValue = 140))
    public int modifyHeightMouseClicked(int original) {
       return height();
    }

    @ModifyConstant(method = "render", constant = @Constant(intValue = 252))
    public int modifyWidthMouseRender(int original) {
       return width();
    }

    @ModifyConstant(method = "render", constant = @Constant(intValue = 140))
    public int modifyHeightRender(int original) {
       return height();
    }

    @ModifyConstant(method = "renderWindow", constant = @Constant(intValue = 252))
    public int modifyWidthRenderWindow(int original) {
        return width();
    }

    @ModifyConstant(method = "renderWindow", constant = @Constant(intValue = 140))
    public int modifyHeightWRenderWindow(int original) {
        return height();
    }

    @ModifyConstant(method = "renderWindow", constant = @Constant(intValue = 256), require = 2)
    public int modifyWidthHeightBackgroundRenderWindow(int original) {
        return scale(original);
    }

    @ModifyArg(method = "renderWindow", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/advancements/AdvancementTab;drawTab(Lnet/minecraft/client/gui/GuiGraphics;IIZ)V"), index = 2)
    public int modifyTabHeightRenderWindow(int y) {
        return y + (int) (Math.abs(multiplier() - 1) * 4F);
    }

    @ModifyArg(method = "renderWindow", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/advancements/AdvancementTab;drawIcon(Lnet/minecraft/client/gui/GuiGraphics;II)V"), index = 2)
    public int modifyTabIconHeightRenderWindow(int y) {
        return y + (int) (Math.abs(multiplier() - 1) * 4F);
    }

    @Inject(method = "renderWindow", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;drawString(Lnet/minecraft/client/gui/Font;Lnet/minecraft/network/chat/Component;IIIZ)V", shift = At.Shift.BEFORE), cancellable = true)
    public void modifyTitleSizeRenderWindow(GuiGraphics guiGraphics, int x, int y, CallbackInfo ci) {
        guiGraphics.pose().pushMatrix();
        guiGraphics.pose().translate(x, y);
        guiGraphics.pose().scale(multiplier(), multiplier());
        guiGraphics.drawString(Minecraft.getInstance().font, this.selectedTab != null ? this.selectedTab.getTitle() : TITLE, 8,  6, -12566464, false);
        guiGraphics.pose().popMatrix();
        ci.cancel();
    }

    @ModifyConstant(method = "renderInside", constant = @Constant(intValue = 9), require = 2)
    public int modifyBorderWidthRenderInside(int original) {
        return scale(original);
    }

    @ModifyConstant(method = "renderInside", constant = @Constant(intValue = 18), require = 2)
    public int modifyBorderHeightRenderInside(int original) {
        return scale(original);
    }

    @ModifyConstant(method = "renderTooltips", constant = @Constant(intValue = 9), require = 2)
    public int modifyBorderWidthRenderTooltips(int original) {
        return scale(original);
    }

    @ModifyConstant(method = "renderTooltips", constant = @Constant(intValue = 18), require = 2)
    public int modifyBorderHeightRenderTooltips(int original) {
        return scale(original);
    }

    @ModifyConstant(method = "renderInside", constant = @Constant(intValue = 234))
    public int modifySadWidthRenderInside(int original) {
        return scale(original);
    }

    @ModifyConstant(method = "renderInside", constant = @Constant(intValue = 113))
    public int modifySadHeightRenderInside(int original) {
        return scale(original);
    }

    @ModifyConstant(method = "renderInside", constant = @Constant(intValue = 117))
    public int modifySadTextWidthRenderInside(int original) {
        return scale(original);
    }

    @ModifyConstant(method = "renderInside", constant = @Constant(intValue = 56))
    public int modifySadTextHeightRenderInside(int original) {
        return scale(original);
    }

    @Unique
    private static int scale(int original) {
        return (int) ((float) original * multiplier());
    }

    @Unique
    private static float multiplier() {
        return LargerAdvancementTab.config.advancementTabSizeMultiplier;
    }

    @Unique
    private static int width() {
        return (int) (252.0F * multiplier());
    }
    
    @Unique
    private static int height() {
        return (int) (140.0F * multiplier());
    }

}