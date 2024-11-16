package net.zepalesque.redux.loot.modifer;

import com.aetherteam.nitrogen.loot.modifiers.NitrogenLootModifiers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctions;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;

import java.util.List;

public class RawOreModifier extends LootModifier {
    private static final Codec<LootItemFunction[]> LOOT_FUNCTIONS_CODEC = LootItemFunctions.ROOT_CODEC.listOf().xmap(list -> list.toArray(LootItemFunction[]::new), List::of);
    public static final MapCodec<RawOreModifier> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
            BuiltInRegistries.ITEM.byNameCodec().fieldOf("removed_item").forGetter(modifier -> modifier.toRemove),
            ItemStack.CODEC.fieldOf("added_item").forGetter(modifier -> modifier.rawOre),
            LOOT_FUNCTIONS_CODEC.fieldOf("functions").forGetter(modifier -> modifier.functions),
            LootModifier.LOOT_CONDITIONS_CODEC.fieldOf("conditions").forGetter(modifier -> modifier.conditions)
    ).apply(instance, RawOreModifier::new));

    private final Item toRemove;
    private final LootItemFunction[] functions;
    private final ItemStack rawOre;

    public RawOreModifier(Item toRemove, ItemStack rawOre, LootItemFunction[] functions, LootItemCondition[] conditions) {
        super(conditions);
        this.toRemove = toRemove;
        this.rawOre = rawOre;
        this.functions = functions;
    }

    public ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> lootStacks, LootContext context) {
        lootStacks.removeIf((itemStack) -> itemStack.is(this.toRemove));
        ItemStack i = this.rawOre;
        for (LootItemFunction function : this.functions) {
            i = function.apply(this.rawOre, context);
        }
        lootStacks.add(i);
        return lootStacks;
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}
