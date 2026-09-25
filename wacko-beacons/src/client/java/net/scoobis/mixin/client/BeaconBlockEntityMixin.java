package net.scoobis.mixin.client;

import net.minecraft.world.level.block.entity.BeaconBlockEntity;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.core.Holder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(BeaconBlockEntity.class)
public class BeaconBlockEntityMixin {
    @Shadow
    public static final List<List<Holder<MobEffect>>> BEACON_EFFECTS = List.of(
        List.of(MobEffects.SPEED, MobEffects.HASTE),
        List.of(MobEffects.REGENERATION, MobEffects.RESISTANCE),
        List.of(MobEffects.JUMP_BOOST, MobEffects.STRENGTH),
        List.of()
    );
}