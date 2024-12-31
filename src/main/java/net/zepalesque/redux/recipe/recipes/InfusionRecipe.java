package net.zepalesque.redux.recipe.recipes;

import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.zepalesque.redux.recipe.ReduxRecipes;
import net.zepalesque.zenith.api.itemstack.ItemStackConstructor;
import net.zepalesque.zenith.api.recipe.recipes.AbstractStackingRecipe;
import net.zepalesque.zenith.api.recipe.serializer.StackingRecipeSerializer;

import java.util.Optional;

public class InfusionRecipe extends AbstractStackingRecipe {
    public static final String ADDED_INFUSION = "added_infusion";
    public InfusionRecipe(Ingredient ingredient, ItemStackConstructor result, Optional<CompoundTag> additional, Optional<Holder<SoundEvent>> sound) {
        super(ReduxRecipes.INFUSION.get(), ingredient, result, additional, sound);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ReduxRecipes.Serializers.INFUSION.get();
    }

    public static class Serializer extends StackingRecipeSerializer<InfusionRecipe> {
        public Serializer() {
            super(InfusionRecipe::new);
        }
    }
}
