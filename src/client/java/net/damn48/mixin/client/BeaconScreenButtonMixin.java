package net.damn48.mixin.client;

import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * 效果按钮修复：低级信标也能点击所有效果按钮。
 *
 * <p>26.2 中信标效果按钮已改名为 {@code BeaconScreen$BeaconPowerButton}
 * （副效果为其子类 {@code BeaconScreen$BeaconUpgradePowerButton}），
 * 原版 {@code updateStatus(int level)} 以 {@code active = tier < level}
 * 限制按钮，1 级信标只能点击第一排（速度/急迫）。
 * 这里在每次更新状态时强制所有效果按钮可用。</p>
 *
 * <p>副效果按钮继承自本类，其 {@code updateStatus} 通过
 * {@code super.updateStatus} 走到这里，因此同样生效。</p>
 */
@Mixin(targets = "net.minecraft.client.gui.screens.inventory.BeaconScreen$BeaconPowerButton")
abstract class BeaconScreenButtonMixin extends AbstractWidget {
    public BeaconScreenButtonMixin(int x, int y, int width, int height, Component message) {
        super(x, y, width, height, message);
    }

    @Inject(at = @At("TAIL"), method = "updateStatus")
    private void updateStatus(int level, CallbackInfo ci) {
        this.active = true;
    }
}
