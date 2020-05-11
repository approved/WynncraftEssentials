package dev.odd.wynncraftessentials.mixins.modules.gui;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;

@Mixin(ItemRenderer.class)
public abstract class DisplayItemUsages {

    Pattern itemUsagePattern = Pattern.compile("^.*\\[(\\d)\\/\\d\\]");

    @Shadow
    float zOffset;

    @Inject(at = @At(value = "INVOKE", target = "net/minecraft/item/ItemStack.getCount()I"),
            method = "renderGuiItemOverlay(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V",
            cancellable = true)
    public void onRenderGuiItemOverlay(TextRenderer fontRenderer, ItemStack stack, int x, int y, String amountText, CallbackInfo ci) {
        MatrixStack matrixStack = new MatrixStack();
        if (stack.getCount() == 1 && amountText == null) {
            String itemName = stack.getName().asString().trim();
            Matcher m = itemUsagePattern.matcher(itemName);
            if(m.matches()){
                String string = m.group(1);
                matrixStack.translate(0.0D, 0.0D, (double)(this.zOffset + 200.0F));
                VertexConsumerProvider.Immediate immediate = VertexConsumerProvider.immediate(Tessellator.getInstance().getBuffer());
                fontRenderer.draw(string, (float)(x + 19 - 2 - fontRenderer.getStringWidth(string)), (float)(y + 6 + 3), 16777215, true, matrixStack.peek().getModel(), immediate, false, 0, 15728880);
                immediate.draw();
            }
         }
    }
}