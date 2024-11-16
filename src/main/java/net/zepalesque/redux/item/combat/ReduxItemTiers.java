package net.zepalesque.redux.item.combat;

import com.aetherteam.aether.AetherTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.util.Lazy;
import net.zepalesque.redux.item.ReduxItems;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public enum ReduxItemTiers implements Tier {

    VERIDIUM(BlockTags.INCORRECT_FOR_IRON_TOOL, 750, 2.25F, 1.0F, 0, () -> Ingredient.of(ReduxItems.VERIDIUM_INGOT.get())),
    INFUSED_VERIDIUM(BlockTags.INCORRECT_FOR_IRON_TOOL, 750, 7.0F, 1.0F, 0, () -> Ingredient.of(ReduxItems.VERIDIUM_INGOT.get()));

    private final TagKey<Block> incorrectBlocksForDrops;
    private final int maxUses;
    private final float efficiency;
    private final float attackDamage;
    private final int enchantability;
    private final Lazy<Ingredient> repairMaterial;

    ReduxItemTiers(TagKey<Block> incorrectBlocksForDrops, int maxUses, float efficiency, float attackDamage, int enchantability, Supplier<Ingredient> repairMaterial) {
        this.incorrectBlocksForDrops = incorrectBlocksForDrops;
        this.maxUses = maxUses;
        this.efficiency = efficiency;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
        this.repairMaterial = Lazy.of(repairMaterial);
    }

    @Override
    public int getUses() {
        return this.maxUses;
    }

    @Override
    public float getSpeed() {
        return this.efficiency;
    }

    @Override
    public float getAttackDamageBonus() {
        return this.attackDamage;
    }

    @Override
    public TagKey<Block> getIncorrectBlocksForDrops() {
        return this.incorrectBlocksForDrops;
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantability;
    }

    @Override
    @NotNull
    public Ingredient getRepairIngredient() {
        return this.repairMaterial.get();
    }



}
