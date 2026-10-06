package net.damn48.mixin.client;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.inventory.BeaconMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * 服务端校验绕过：低级信标也能写入高级效果。
 *
 * <p>26.2 中服务端 {@code BeaconMenu.updateEffects} 会调用
 * {@code BeaconBlockEntity.validateEffects} 校验：副效果要求信标达到 4 级、
 * 主/副效果必须当前等级可达，校验失败会直接断开玩家连接。
 * 本 Mixin 将该校验恒重定向为通过，使 1 级信标也能写入 Strength I
 * 等本不该获得的效果。</p>
 *
 * <p>⚠️ 该 Mixin 在客户端环境：单机（集成服务端）直接生效；
 * 独立服务器必须同样安装本模组才生效。</p>
 */
@Mixin(BeaconMenu.class)
public class BeaconMenuMixin {
    @Redirect(
        method = "updateEffects",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/block/entity/BeaconBlockEntity;validateEffects(Lnet/minecraft/core/Holder;Lnet/minecraft/core/Holder;I)Z"
        )
    )
    private boolean wacko$bypassValidation(Holder<MobEffect> primary, Holder<MobEffect> secondary, int levels) {
        return true;
    }
}
