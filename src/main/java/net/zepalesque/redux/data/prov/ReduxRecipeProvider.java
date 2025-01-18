package net.zepalesque.redux.data.prov;

import com.aetherteam.aether.data.providers.AetherRecipeProvider;
import com.aetherteam.aether.recipe.builder.AetherCookingRecipeBuilder;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.zepalesque.redux.recipe.recipes.InfusionRecipe;
import net.zepalesque.unity.data.prov.UnityRecipeProvider;
import net.zepalesque.zenith.api.item.stack.ItemStackConstructor;
import net.zepalesque.zenith.api.recipe.builder.StackingRecipeBuilder;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public abstract class ReduxRecipeProvider extends UnityRecipeProvider {

    public ReduxRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String id) {
        super(output, lookupProvider, id);
    }

    public static void woodFromLogs(RecipeOutput recipeOutput, ItemLike wood, ItemLike log) {
        RecipeProvider.woodFromLogs(recipeOutput, wood, log);
    }

    public static void planksFromLog(RecipeOutput recipeOutput, ItemLike planks, TagKey<Item> logs, int resultCount) {
        RecipeProvider.planksFromLog(recipeOutput, planks, logs, resultCount);
    }

    public ShapedRecipeBuilder fence(Supplier<? extends Block> fence, Supplier<? extends Block> material) {
        return super.fence(fence, material);
    }

    public ShapedRecipeBuilder fenceGate(Supplier<? extends Block> fenceGate, Supplier<? extends Block> material) {
        return super.fenceGate(fenceGate, material);
    }

    public static RecipeBuilder doorBuilder(ItemLike door, Ingredient material) {
        return RecipeProvider.doorBuilder(door, material);
    }

    public static String getHasName(ItemLike itemLike) {
        return RecipeProvider.getHasName(itemLike);
    }

    public static String getItemName(ItemLike itemLike) {
        return RecipeProvider.getItemName(itemLike);
    }

    public static Criterion<InventoryChangeTrigger.TriggerInstance> has(ItemLike itemLike) {
        return RecipeProvider.has(itemLike);
    }

    public static RecipeBuilder trapdoorBuilder(ItemLike trapdoor, Ingredient material) {
        return RecipeProvider.trapdoorBuilder(trapdoor, material);
    }

    public static void pressurePlate(RecipeOutput output, ItemLike pressurePlate, ItemLike material) {
        RecipeProvider.pressurePlate(output, pressurePlate, material);
    }

    public static RecipeBuilder buttonBuilder(ItemLike button, Ingredient material) {
        return RecipeProvider.buttonBuilder(button, material);
    }

    public static void woodenBoat(RecipeOutput output, ItemLike boat, ItemLike material) {
        RecipeProvider.woodenBoat(output, boat, material);
    }

    public static void chestBoat(RecipeOutput output, ItemLike boat, ItemLike material) {
        RecipeProvider.chestBoat(output, boat, material);
    }

    public static void slab(RecipeOutput output, RecipeCategory category, ItemLike slab, ItemLike material) {
        RecipeProvider.slab(output, category, slab, material);
    }

    public RecipeBuilder stairs(Supplier<? extends Block> stairs, Supplier<? extends Block> material) {
        return super.stairs(stairs, material);
    }

    public static void wall(RecipeOutput output, RecipeCategory category, ItemLike wall, ItemLike material) {
        RecipeProvider.wall(output, category, wall, material);
    }

    public static void bookshelf(RecipeOutput output, ItemLike plank, ItemLike bookshelf) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, bookshelf, 1)
                .define('P', plank)
                .define('B', Items.BOOK)
                .pattern("PPP")
                .pattern("BBB")
                .pattern("PPP")
                .unlockedBy(getHasName(Items.BOOK), has(Items.BOOK))
                .save(output, getItemName(plank) + "_to_" + getItemName(bookshelf));
    }

    @Override
    public void stonecuttingRecipe(RecipeOutput output, RecipeCategory category, ItemLike item, ItemLike ingredient) {
        super.stonecuttingRecipe(output, category, item, ingredient);
    }

    @Override
    public void stonecuttingRecipe(RecipeOutput output, RecipeCategory category, ItemLike item, ItemLike ingredient, int count) {
        super.stonecuttingRecipe(output, category, item, ingredient, count);
    }

    public static String getConversionRecipeName(ItemLike result, ItemLike ingredient) {
        return RecipeProvider.getConversionRecipeName(result, ingredient);
    }

    public static String getConversionRecipeNameSwitched(ItemLike result, ItemLike ingredient) {
        return getItemName(ingredient) + "_to_" + getItemName(result);
    }

    public SimpleCookingRecipeBuilder smeltingOreRecipe(ItemLike result, ItemLike ingredient, float experience) {
        return super.smeltingOreRecipe(result, ingredient, experience);
    }

    public SimpleCookingRecipeBuilder blastingOreRecipe(ItemLike result, ItemLike ingredient, float experience) {
        return super.blastingOreRecipe(result, ingredient, experience);
    }

    public SimpleCookingRecipeBuilder smeltingOreRecipe(ItemLike result, ItemLike ingredient, float experience, int cookTime) {
        return SimpleCookingRecipeBuilder.smelting(Ingredient.of(ingredient), RecipeCategory.MISC, result, experience, cookTime)
                .unlockedBy(getHasName(ingredient), has(ingredient));
    }

    public SimpleCookingRecipeBuilder blastingOreRecipe(ItemLike result, ItemLike ingredient, float experience, int cookTime) {
        return SimpleCookingRecipeBuilder.blasting(Ingredient.of(ingredient), RecipeCategory.MISC, result, experience, cookTime)
                .unlockedBy(getHasName(ingredient), has(ingredient));
    }

    @Override
    public AetherCookingRecipeBuilder enchantingRecipe(RecipeCategory category, ItemLike result, ItemLike ingredient, float experience, int duration) {
        return super.enchantingRecipe(category, result, ingredient, experience, duration);
    }

    @Override
    public AetherCookingRecipeBuilder enchantingRecipe(RecipeCategory category, ItemLike result, TagKey<Item> ingredient, float experience, int duration, String unlockName) {
        return super.enchantingRecipe(category, result, ingredient, experience, duration, unlockName);
    }

    @SuppressWarnings("deprecation")
    public StackingRecipeBuilder infuse(ItemLike result, ItemLike ingredient) {
        return StackingRecipeBuilder.recipe(Ingredient.of(ingredient), new ItemStackConstructor(result.asItem().builtInRegistryHolder(), Optional.empty()), InfusionRecipe::new);
    }

    @Override
    public ResourceLocation name(String name) {
        return super.name(name);
    }


}
