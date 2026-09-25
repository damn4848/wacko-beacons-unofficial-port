package net.scoobis.mixin.client;

import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net.minecraft.client.gui.screens.inventory.BeaconScreen$EffectButtonWidget")
abstract class BeaconScreenButtonMixin extends AbstractWidget {
    public BeaconScreenButtonMixin(int x, int y, int width, int height, Component message) {
        super(x, y, width, height, message);
    }

    @Inject(at = @At("TAIL"), method = "tick")
    private void tick(int level, CallbackInfo ci) {
        this.active = true;
    }
}