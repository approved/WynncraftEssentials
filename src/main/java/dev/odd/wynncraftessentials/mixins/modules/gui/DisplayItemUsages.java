package dev.odd.wynncraftessentials.mixins.modules.gui;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.item.ItemStack;

@Mixin(ItemRenderer.class)
public abstract class DisplayItemUsages {

    @Deprecated
    Pattern potionNameMatch = Pattern.compile("^(§[a-z0-9])?Potion of .*\\[(\\d)\\/\\d\\]");
    
    Pattern itemUsagePattern = Pattern.compile("^.*\\[(\\d)\\/\\d\\]");

    /**
     * Displays item usages in the hotbar.
     * TODO: Modify to target the invoked signature and modify the amountText argument and make generic
     */
    @Inject(at = @At("HEAD"), method = "renderGuiItemOverlay(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/item/ItemStack;II)V", cancellable = true)
    public void onRenderItemOverlay(TextRenderer fontRenderer, ItemStack stack, int x, int y, CallbackInfo ci) {
        String potionName = stack.getName().asString().trim();
        Matcher m = itemUsagePattern.matcher(potionName);
        if (m.matches()) {
            this.renderGuiItemOverlay(fontRenderer, stack, x, y, m.group(1));
            ci.cancel();
        }
    }

    @Shadow
    public void renderGuiItemOverlay(TextRenderer fontRenderer, ItemStack stack, int x, int y, String amountText) {
    }
}