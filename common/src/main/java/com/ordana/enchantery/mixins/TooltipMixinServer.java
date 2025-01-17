package com.ordana.enchantery.mixins;

import com.ordana.enchantery.EnchanteryLogic;
import com.ordana.enchantery.reg.ModEnchants;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemStack.class)
public class TooltipMixinServer {
    @Inject(method = "inventoryTick", at = @At(value = "HEAD"))
    public void inventoryTick(Level level, Entity entity, int inventorySlot, boolean isCurrentItem, CallbackInfo ci) {
        EnchanteryLogic.leechingCurseLogic(level, entity, inventorySlot);
    }

    @ModifyVariable(
            method = "hurt",
            at = @At(value = "HEAD"), index = 1, argsOnly = true)
    public int extraDamage(int amount) {
        int f = EnchantmentHelper.getItemEnchantmentLevel(ModEnchants.DIMINISHING_CURSE.get(), (ItemStack)(Object) this);
        if (f > 0) amount = amount + f;
        return amount;
    }
}
