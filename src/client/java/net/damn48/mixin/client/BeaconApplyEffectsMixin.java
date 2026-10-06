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
import org.spongepowered.asm.mixin.Overwrite;

import java.util.List;
import java.util.Objects;

/**
 * 效果施加修复：移除副效果必须 4 级的硬编码门槛。
 *
 * <p>原版 {@code applyEffects} 即使通过了 {@code updateEffects} 校验，
 * 也会在此处被 {@code levels >= 4} 的门槛拦下——副效果（如 Regeneration II）
 * 在 1 级信标上根本不会被施加。这里重写该方法，去掉等级门槛：
 * <ul>
 *   <li>主/副效果相同 → 主效果以 II 级（amplifier 1）施加，任意等级</li>
 *   <li>副效果与主效果不同 → 副效果直接以 II 级施加，任意等级（与 GUI 的 "II" 标签一致）</li>
 * </ul>
 * 其余逻辑（作用范围、时长、环境效果、可见性）与原版一致。</p>
 */
@Mixin(BeaconBlockEntity.class)
public class BeaconApplyEffectsMixin {
    /**
     * @author damn48
     * @reason 移除信标副效果施加的等级门槛（26.2 服务端校验绕过的一部分）
     */
    @Overwrite
    private static void applyEffects(Level level, BlockPos pos, int levels, Holder<MobEffect> primaryPower, Holder<MobEffect> secondaryPower) {
        if (!level.isClientSide() && primaryPower != null) {
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
}
