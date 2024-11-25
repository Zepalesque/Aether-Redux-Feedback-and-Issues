package net.zepalesque.redux.blockset.wood;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.blockset.wood.type.AbstractBookshelfSet;
import net.zepalesque.redux.blockset.wood.type.LogWallWoodSet;
import net.zepalesque.redux.blockset.wood.type.RegularBookshelfSet;

public class ReduxWoodSets {

    public static final LogWallWoodSet CRYSTAL = register(new RegularBookshelfSet("crystal", MapColor.COLOR_LIGHT_BLUE, MapColor.TERRACOTTA_CYAN, SoundType.WOOD));

    public static <T extends AbstractBookshelfSet<?>> T register(T set) {
        Redux.BLOCK_SETS.add(set);
        return set;
    }
}
