package com.ordana.enchantery.enchantments;

import com.ordana.enchantery.EnchanteryLogic;
import com.ordana.enchantery.reg.ModTags;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class DiffusionCurseEnchantment extends Enchantment {
    public DiffusionCurseEnchantment(Rarity rarity, EquipmentSlot... equipmentSlots) {
        super(rarity, EnchantmentCategory.WEAPON, equipmentSlots);
    }

    public int getMinCost(int level) {
        return 25;
    }

    public int getMaxCost(int level) {
        return 50;
    }

    public int getMaxLevel() {
        return 1;
    }

    public boolean canEnchant(ItemStack stack) {
        return stack.getItem() instanceof AxeItem || super.canEnchant(stack);
    }

    public float getDamageBonus(int level, MobType type) {
        return 3f;
    }

    @Override
    public boolean isTreasureOnly() {
        return !EnchanteryLogic.getHolder(this).is(ModTags.BASIC);
    }

    @Override
    public boolean isTradeable() {
        return EnchanteryLogic.getHolder(this).is(ModTags.TRADEABLE);
    }

    @Override
    public boolean isDiscoverable() {
        return EnchanteryLogic.getHolder(this).is(ModTags.BASIC);
    }

    @Override
    public boolean isCurse() {
        return true;
    }

    public void doPostAttack(LivingEntity attacker, Entity target, int level) {
        if (target instanceof LivingEntity livingEntity) {
            if (level > 0) {
                var effectList = attacker.getActiveEffectsMap();
                for (var e : effectList.entrySet()) {
                    MobEffect effect = e.getKey();
                    livingEntity.addEffect(effectList.get(effect));
                }
            }
        }
    }
}
