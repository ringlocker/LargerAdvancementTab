package com.github.ringlocker.largeradvancementtab.mixin.client;

import com.github.ringlocker.largeradvancementtab.LargerAdvancementTab;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.advancements.AdvancementTab;
import net.minecraft.client.gui.screens.advancements.AdvancementWidget;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.core.ClientAsset;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Environment(EnvType.CLIENT)
@Mixin(AdvancementTab.class)
public class AdvancementTabMixin {

    @Unique private static final int WIDTH = (int) (252.0F * LargerAdvancementTab.MULTIPLIER);
    @Unique private static final int HEIGHT = (int) (140.0F * LargerAdvancementTab.MULTIPLIER);

    @Shadow private double scrollX;
    @Shadow private double scrollY;
    @Shadow private int minX = Integer.MAX_VALUE;
    @Shadow private int minY = Integer.MAX_VALUE;
    @Shadow private int maxX = Integer.MIN_VALUE;
    @Shadow private int maxY = Integer.MIN_VALUE;
    @Shadow private boolean centered;
    @Final @Shadow private DisplayInfo display;
    @Final @Shadow private AdvancementWidget root;

    @ModifyConstant(method = "drawTooltips", constant = @Constant(intValue = 234), require = 2)
    public int modifyBackgroundWidthDrawTooltips(int original) {
        return WIDTH - (scale(10) + Math.round(8F * LargerAdvancementTab.MULTIPLIER));
    }

    @ModifyConstant(method = "drawTooltips", constant = @Constant(intValue = 113), require = 2)
    public int modifyBackgroundHeightDrawTooltips(int original) {
        return HEIGHT - (scale(19) + Math.round(8F * LargerAdvancementTab.MULTIPLIER));
    }

    /**
     * @author ringlocker
     * @reason make background render correctly, instead of to the nearest 16th int.
     */
    @Overwrite
    public void drawContents(GuiGraphics guiGraphics, int x, int y) {

        if (!this.centered) {
            this.scrollX = ((double) WIDTH / 2) - 9 - (double) (this.maxX + this.minX) / 2;
            this.scrollY = ((double) HEIGHT / 2) - 14 - (double) (this.maxY + this.minY) / 2;
            this.centered = true;
        }

        guiGraphics.enableScissor(x, y, x + WIDTH - scale(18), y + HEIGHT - scale(27))    ;
        guiGraphics.pose().pushMatrix();
        guiGraphics.pose().translate((float)x, (float)y);
        ResourceLocation background = this.display.getBackground().map(ClientAsset::texturePath).orElse(TextureManager.INTENTIONAL_MISSING_TEXTURE);

        int scrollXInt = Mth.floor(this.scrollX);
        int scrollYInt = Mth.floor(this.scrollY);
        int nearestSixteenthScrollX = scrollXInt % 16;
        int nearestSixteenthScrollY = scrollYInt % 16;
        int indexesWidth = WIDTH / 16;
        int indexesHeight = HEIGHT / 16;

        for(int xIndex = -1; xIndex <= indexesWidth; ++xIndex) {
            for(int yIndex = -1; yIndex <= indexesHeight; ++yIndex) {
                guiGraphics.blit(
                        RenderPipelines.GUI_TEXTURED,
                        background,
                        nearestSixteenthScrollX + 16 * xIndex,
                        nearestSixteenthScrollY + 16 * yIndex,
                        0.0F, 0.0F,
                        xIndex == indexesWidth ? WIDTH % 16 : 16,
                        yIndex == indexesHeight ? HEIGHT % 16 : 16,
                        16, 16
                );
            }
        }

        this.root.drawConnectivity(guiGraphics, scrollXInt, scrollYInt, true);
        this.root.drawConnectivity(guiGraphics, scrollXInt, scrollYInt, false);
        this.root.draw(guiGraphics, scrollXInt, scrollYInt);

        guiGraphics.pose().popMatrix();
        guiGraphics.disableScissor();
    }

    @ModifyConstant(method = "scroll", constant = @Constant(intValue = 234), require = 2)
    public int modifyBackgroundWidthScroll(int original) {
        return WIDTH - scale(18);
    }

    @ModifyConstant(method = "scroll", constant = @Constant(intValue = 113), require = 2)
    public int modifyBackgroundHeightScroll(int original) {
        return HEIGHT - scale(27);
    }

    @Unique
    private static int scale(int original) {
        return (int) ((float) original * LargerAdvancementTab.MULTIPLIER);
    }

}