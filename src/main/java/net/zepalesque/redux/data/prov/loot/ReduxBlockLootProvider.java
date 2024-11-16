package net.zepalesque.redux.data.prov.loot;

import com.aetherteam.aether.data.providers.AetherBlockLootSubProvider;
import com.aetherteam.aether.loot.functions.DoubleDrops;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.zepalesque.redux.block.state.ReduxStates;

import java.util.Set;
import java.util.function.Function;

// Many of these are just public overrides with no differences, as this is used by the BlockSets
public abstract class ReduxBlockLootProvider extends AetherBlockLootSubProvider {

    public ReduxBlockLootProvider(Set<Item> items, FeatureFlagSet flags, HolderLookup.Provider registries) {
        super(items, flags, registries);
    }

    protected LootTable.Builder naturalDropBase(Block block, ItemLike other) {
        return LootTable.lootTable()
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(applyExplosionDecay(other, LootItem.lootTableItem(other)))
                                .when(
                                        LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(ReduxStates.NATURAL_GEN, true))
                                ).apply(DoubleDrops.builder())
                ).withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(applyExplosionDecay(other, LootItem.lootTableItem(block)))
                                .when(
                                        LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(ReduxStates.NATURAL_GEN, false))
                                ).apply(DoubleDrops.builder())
                );
    }

    public void naturalDrop(Block block, ItemLike other) {
        this.add(block, naturalDropBase(block, other));
    }

    @Override
    public void dropSelf(Block block) {
        super.dropSelf(block);
    }

    @Override
    public void dropOther(Block block, ItemLike item) {
        super.dropOther(block, item);
    }

    @Override
    public void add(Block block, LootTable.Builder builder) {
        super.add(block, builder);
    }

    @Override
    public void add(Block block, Function<Block, LootTable.Builder> factory) {
        super.add(block, factory);
    }

    @Override
    public LootTable.Builder createDoorTable(Block pDoorBlock) {
        return super.createDoorTable(pDoorBlock);
    }

    @Override
    public LootTable.Builder createSingleItemTableWithSilkTouch(Block pBlock, ItemLike pItem) {
        return super.createSingleItemTableWithSilkTouch(pBlock, pItem);
    }

    @Override
    public LootTable.Builder createSingleItemTableWithSilkTouch(Block pBlock, ItemLike pItem, NumberProvider pCount) {
        return super.createSingleItemTableWithSilkTouch(pBlock, pItem, pCount);
    }

    @Override
    public void dropPottedContents(Block pFlowerPot) {
        super.dropPottedContents(pFlowerPot);
    }

    @Override
    public LootTable.Builder createSlabItemTable(Block pBlock) {
        return super.createSlabItemTable(pBlock);
    }

    // Only drops with shears
    public Function<Block, LootTable.Builder> shears() {
        return shearsOr(Blocks.AIR);
    }

    // Drops another without shears
    public Function<Block, LootTable.Builder> shearsOr(ItemLike drop, float chance, float min, float max) {
        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        return (block) -> createSilkTouchOrShearsDispatchTable(block, this.applyExplosionDecay(block, LootItem.lootTableItem(drop).when(LootItemRandomChanceCondition.randomChance(chance)).apply(SetItemCountFunction.setCount(UniformGenerator.between(min, max))).apply(ApplyBonusCount.addUniformBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))));
    }

    public Function<Block, LootTable.Builder> shearsOr(ItemLike drop, float chance) {
        return shearsOr(drop, chance, 1.0F, 1.0F);
    }

    public Function<Block, LootTable.Builder> shearsOr(ItemLike drop) {
        return shearsOr(drop, 0.25F);
    }

}
