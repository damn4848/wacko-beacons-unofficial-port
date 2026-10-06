package net.damn48.mixin.client;

import net.minecraft.world.level.block.entity.BeaconBlockEntity;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.core.Holder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(BeaconBlockEntity.class)
public class BeaconBlockEntityMixin {
    /**
     * 漏洞一实现（Regeneration II）。
     *
     * <p>服务端只校验「所选效果是否属于合法信标效果列表」，而不校验
     * 「它是否可以作为主效果」，且只在主副效果相同时才要求 4 层金字塔。
     * 该字段在 Mixin 应用时以这里的值替换目标类的静态字段，使
     * Regeneration 通过服务端校验，从而可在不建满金字塔的情况下
     * 选择 Regeneration II。</p>
     *
     * <p>⚠️ 与 vanilla 值保持一致的说明：本移植版未改动逻辑，
     * 保持原项目（getcmdrolled/wacko-beacons）的原始取值。</p>
     */
    @Shadow
    public static final List<List<Holder<MobEffect>>> BEACON_EFFECTS = List.of(
        List.of(MobEffects.SPEED, MobEffects.HASTE),
        List.of(MobEffects.REGENERATION, MobEffects.RESISTANCE),
        List.of(MobEffects.JUMP_BOOST, MobEffects.STRENGTH),
        List.of()
    );
}