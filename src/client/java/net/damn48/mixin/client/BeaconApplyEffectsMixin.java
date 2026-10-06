package net.damn48.mixin.client;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BeaconBlockEntity;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Objects;

/**
 * 效果施加修复：移除副效果必须 4 级的硬编码门槛。
 *
 * <p>原版 {@code applyEffects} 即使通过了 {@code updateEffects} 校验，
 * 也会被 {@code levels >= 4} 的门槛拦下——副效果（如 Regeneration II）
 * 在低级信标上根本不会被施加。本 Mixin 取消原版逻辑，改为
 * 去掉等级门槛的等效实现。</p>
 *
 * <p>⚠️ 使用 {@code @Inject + cancel} 而非 {@code @Overwrite}：非破坏式
 * 重写会保留原方法体与全部注入点，避免与其他模组（如 carpet-org-addition
 * 的 {@code BeaconBlockEntityMixin}，用 mixinextras {@code @WrapOperation}
 * 注入 {@code applyEffects}）发生注入冲突导致启动崩溃。</p>
 *
 * <ul>
 *   <li>主/副效果相同 → 主效果以 II 级（amplifier 1）施加，任意等级</li>
 *   <li>副效果与主效果不同 → 副效果直接以 II 级施加，任意等级（与 GUI 的 "II" 标签一致）</li>
 * </ul>
 * 其余逻辑（作用范围、时长、环境效果、可见性）与原版一致。
 */
@Mixin(BeaconBlockEntity.class)
public class BeaconApplyEffectsMixin {
    @Inject(method = "applyEffects", at = @At("HEAD"), cancellable = true)
    private static void wacko$applyEffects(Level level, BlockPos pos, int levels, Holder<MobEffect> primaryPower, Holder<MobEffect> secondaryPower, CallbackInfo ci) {
        // 与原版一致的提前返回条件：客户端侧或没有主效果时不做任何事
        if (level.isClientSide() || primaryPower == null) {
            return;
        }
        ci.cancel();
        double d = levels * 10 + 10;
        int amplifier = Objects.equals(primaryPower, secondaryPower) ? 1 : 0;
        int duration = (9 + levels * 2) * 20;
        AABB aabb = new AABB(pos).inflate(d).expandTowards(0.0, level.getHeight(), 0.0);
        List<Player> list = level.getEntitiesOfClass(Player.class, aabb);
        for (Player player : list) {
            player.addEffect(new MobEffectInstance(primaryPower, duration, amplifier, true, true));
        }
        if (secondaryPower != null && !Objects.equals(primaryPower, secondaryPower)) {
            for (Player player : list) {
                player.addEffect(new MobEffectInstance(secondaryPower, duration, 1, true, true));
            }
        }
    }
}