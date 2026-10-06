package net.damn48.mixin.client;

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

    /**
     * 漏洞二实现（低级信标获取高级效果）。
     *
     * <p>服务端不校验所选效果在当前信标等级下是否可达，因此只要
     * 信标 GUI 中的效果按钮可点击，1 级信标也能选中 Strength I。
     * 这里在每次 tick 时把按钮强制置为可用（active），无视金字塔等级。</p>
     */
    @Inject(at = @At("TAIL"), method = "tick")
    private void tick(int level, CallbackInfo ci) {
        this.active = true;
    }
}