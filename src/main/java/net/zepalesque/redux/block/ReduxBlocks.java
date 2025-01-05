package net.zepalesque.redux.block;

import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.block.dungeon.DoorwayBlock;
import com.aetherteam.aether.block.dungeon.TrappedBlock;
import com.aetherteam.aether.block.natural.AetherDoubleDropBlock;
import com.aetherteam.aether.entity.AetherEntityTypes;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChainBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockBehaviour.OffsetType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.block.dungeon.DoorwayPillarBlock;
import net.zepalesque.redux.block.dungeon.RunelightBlock;
import net.zepalesque.redux.block.dungeon.TrappedPillarBlock;
import net.zepalesque.redux.block.natural.DoubleDropsMossCarpet;
import net.zepalesque.redux.block.natural.GoldenCloversBlock;
import net.zepalesque.redux.block.natural.HangingAetherVinesBody;
import net.zepalesque.redux.block.natural.HangingAetherVinesHead;
import net.zepalesque.redux.block.natural.bush.CustomBoundsBushBlock;
import net.zepalesque.redux.block.natural.crop.WyndoatsBlock;
import net.zepalesque.redux.block.natural.leaves.FallingLeavesBlock;
import net.zepalesque.redux.block.redstone.LogicatorBlock;
import net.zepalesque.redux.block.state.ReduxBlockBuilders;
import net.zepalesque.redux.client.particle.ReduxParticles;
import net.zepalesque.redux.data.resource.registries.ReduxFeatureConfig;
import net.zepalesque.unity.block.natural.DoubleDropsCarpet;
import net.zepalesque.unity.block.natural.DoubleDropsGrowthBlock;
import net.zepalesque.unity.block.natural.leaves.LeafPileBlock;
import net.zepalesque.unity.data.resource.registries.UnityFeatureConfig;
import net.zepalesque.unity.event.hook.BlockHooks;
import net.zepalesque.zenith.api.block.CommonPlantBounds;
import net.zepalesque.zenith.api.blockset.type.AbstractWoodSet;
import net.zepalesque.zenith.mixin.mixins.common.accessor.FireAccessor;

public class ReduxBlocks extends ReduxBlockBuilders {

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Redux.MODID);

    public static DeferredBlock<FallingLeavesBlock> GILDENROOT_LEAVES = register("gildenroot_leaves",
            () -> new FallingLeavesBlock(ReduxParticles.GILDENROOT_LEAF, Properties.ofFullCopy(AetherBlocks.SKYROOT_LEAVES.get()).mapColor(MapColor.QUARTZ)));

    public static DeferredBlock<LeafPileBlock> GILDENROOT_LEAF_PILE = register("gildenroot_leaf_pile",
            () -> new LeafPileBlock(GILDENROOT_LEAVES));

    public static DeferredBlock<FallingLeavesBlock> SHADEROOT_LEAVES = register("shaderoot_leaves",
            () -> new FallingLeavesBlock(ReduxParticles.SHADEROOT_LEAF, Properties.ofFullCopy(AetherBlocks.SKYROOT_LEAVES.get()).mapColor(MapColor.TERRACOTTA_PURPLE)));

    public static DeferredBlock<LeafPileBlock> SHADEROOT_LEAF_PILE = register("shaderoot_leaf_pile",
            () -> new LeafPileBlock(SHADEROOT_LEAVES));

    public static DeferredBlock<GoldenCloversBlock> GOLDEN_CLOVERS = register("golden_clovers",
            () -> new GoldenCloversBlock(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.GOLD)
                    .noCollission()
                    .sound(SoundType.PINK_PETALS)
                    .pushReaction(PushReaction.DESTROY)
                    .replaceable()
            ));

    // TODO: Moss BlockSet
    public static DeferredBlock<DoubleDropsGrowthBlock> BLEAKMOSS_BLOCK = register("bleakmoss_block",
            () -> new DoubleDropsGrowthBlock(Properties.ofFullCopy(Blocks.MOSS_BLOCK).mapColor(MapColor.TERRACOTTA_MAGENTA), ReduxFeatureConfig.BLEAKMOSS_BONEMEAL));
    public static DeferredBlock<DoubleDropsMossCarpet> BLEAKMOSS_CARPET = register("bleakmoss_carpet",
            () -> new DoubleDropsMossCarpet(Properties.ofFullCopy(Blocks.MOSS_CARPET).mapColor(MapColor.TERRACOTTA_MAGENTA)));

    public static final DeferredBlock<Block> CARVED_PILLAR = register("carved_pillar", () -> new RotatedPillarBlock(Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).strength(0.5F, 6.0F).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> SENTRY_PILLAR = register("sentry_pillar", () -> new RotatedPillarBlock(Properties.ofFullCopy(CARVED_PILLAR.get()).lightLevel(state -> 11)));
    public static final DeferredBlock<Block> CARVED_BASE = register("carved_base", () -> new Block(Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).strength(0.5F, 6.0F).requiresCorrectToolForDrops()));
    public static final DeferredBlock<Block> SENTRY_BASE = register("sentry_base", () -> new Block(Properties.ofFullCopy(CARVED_BASE.get()).lightLevel(state -> 11)));

    public static final DeferredBlock<Block> LOCKED_CARVED_PILLAR = register("locked_carved_pillar", () -> new RotatedPillarBlock(Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).strength(-1.0F, 3600000.0F)));
    public static final DeferredBlock<Block> LOCKED_SENTRY_PILLAR = register("locked_sentry_pillar", () -> new RotatedPillarBlock(Properties.ofFullCopy(LOCKED_CARVED_PILLAR.get()).lightLevel(state -> 11)));
    public static final DeferredBlock<Block> LOCKED_CARVED_BASE = register("locked_carved_base", () -> new Block(Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).strength(-1.0F, 3600000.0F)));
    public static final DeferredBlock<Block> LOCKED_SENTRY_BASE = register("locked_sentry_base", () -> new Block(Properties.ofFullCopy(LOCKED_CARVED_BASE.get()).lightLevel(state -> 11)));

    public static final DeferredBlock<Block> TRAPPED_CARVED_PILLAR = register("trapped_carved_pillar", () -> new TrappedPillarBlock(AetherEntityTypes.SENTRY::get, () -> CARVED_PILLAR.get().defaultBlockState(), Properties.ofFullCopy(CARVED_PILLAR.get())));
    public static final DeferredBlock<Block> TRAPPED_SENTRY_PILLAR = register("trapped_sentry_pillar", () -> new TrappedPillarBlock(AetherEntityTypes.SENTRY::get, () -> SENTRY_PILLAR.get().defaultBlockState(), Properties.ofFullCopy(SENTRY_PILLAR.get())));
    public static final DeferredBlock<Block> TRAPPED_CARVED_BASE = register("trapped_carved_base", () -> new TrappedBlock(AetherEntityTypes.SENTRY::get, () -> CARVED_BASE.get().defaultBlockState(), Properties.ofFullCopy(CARVED_BASE.get())));
    public static final DeferredBlock<Block> TRAPPED_SENTRY_BASE = register("trapped_sentry_base", () -> new TrappedBlock(AetherEntityTypes.SENTRY::get, () -> SENTRY_BASE.get().defaultBlockState(), Properties.ofFullCopy(SENTRY_BASE.get())));

    public static final DeferredBlock<Block> BOSS_DOORWAY_CARVED_PILLAR = register("boss_doorway_carved_pillar", () -> new DoorwayPillarBlock(AetherEntityTypes.SLIDER::get, Properties.ofFullCopy(CARVED_PILLAR.get())));
    public static final DeferredBlock<Block> BOSS_DOORWAY_SENTRY_PILLAR = register("boss_doorway_sentry_pillar", () -> new DoorwayPillarBlock(AetherEntityTypes.SLIDER::get, Properties.ofFullCopy(SENTRY_PILLAR.get())));
    public static final DeferredBlock<Block> BOSS_DOORWAY_CARVED_BASE = register("boss_doorway_carved_base", () -> new DoorwayBlock(AetherEntityTypes.SLIDER::get, Properties.ofFullCopy(CARVED_BASE.get())));
    public static final DeferredBlock<Block> BOSS_DOORWAY_SENTRY_BASE = register("boss_doorway_sentry_base", () -> new DoorwayBlock(AetherEntityTypes.SLIDER::get, Properties.ofFullCopy(SENTRY_BASE.get())));

    public static final DeferredBlock<Block> RUNELIGHT = register("runelight", () ->
            new RunelightBlock(Properties.of()
                    .mapColor(state -> state.getValue(RunelightBlock.LIT) ? MapColor.COLOR_LIGHT_BLUE : MapColor.LAPIS)
                    .lightLevel(state -> state.getValue(RunelightBlock.LIT) ? 13 : 1)
                    .strength(0.7F, 6.0F)
                    .sound(SoundType.COPPER_BULB)
                    .requiresCorrectToolForDrops()
                    .instrument(NoteBlockInstrument.IRON_XYLOPHONE),
                    false
            ));

    public static final DeferredBlock<Block> LOCKED_RUNELIGHT = register("locked_runelight", () ->
            new RunelightBlock(Properties.of()
                    .mapColor(state -> state.getValue(RunelightBlock.LIT) ? MapColor.COLOR_LIGHT_BLUE : MapColor.LAPIS)
                    .lightLevel(state -> state.getValue(RunelightBlock.LIT) ? 13 : 1)
                    .strength(-1.0F, 3600000.0F)
                    .sound(SoundType.COPPER_BULB)
                    .instrument(NoteBlockInstrument.IRON_XYLOPHONE),
                    true
            ));

    public static final DeferredBlock<Block> LOCKED_SENTRITE_BRICKS = register("locked_sentrite_bricks", () ->
            new Block(Properties.of()
                    .mapColor(MapColor.DEEPSLATE)
                    .strength(-1.0F, 3600000.0F)
                    .sound(SoundType.NETHER_BRICKS)
                    .instrument(NoteBlockInstrument.BASEDRUM)
            ));

    public static DeferredBlock<Block> WYNDSPROUTS = register("wyndsprouts",
            () -> new CustomBoundsBushBlock.Enchanted(CommonPlantBounds.BUSH, Properties.ofFullCopy(Blocks.SHORT_GRASS).sound(SoundType.CHERRY_SAPLING).offsetType(OffsetType.XZ).hasPostProcess((s, l, p) -> true)));

    public static DeferredBlock<Block> SKYSPROUTS = register("skysprouts",
            () -> new CustomBoundsBushBlock(CommonPlantBounds.BUSH, Properties.ofFullCopy(Blocks.SHORT_GRASS).sound(SoundType.CHERRY_SAPLING).offsetType(OffsetType.XZ)));

    public static DeferredBlock<Block> WYNDOATS = BLOCKS.register("wyndoats",
            () -> new WyndoatsBlock(Properties.ofFullCopy(Blocks.WHEAT)));

    public static DeferredBlock<Block> SENTRITE_CHAIN = register("sentrite_chain",
            () -> new ChainBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CHAIN)));

    public static DeferredBlock<Block> SENTRITE_LANTERN = register("sentrite_lantern",
            () -> new LanternBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.LANTERN).mapColor(MapColor.DEEPSLATE).lightLevel(state -> 13)));

    public static DeferredBlock<Block> SENTRITE_BARS = register("sentrite_bars",
            () -> new IronBarsBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BARS).mapColor(MapColor.DEEPSLATE)));


    public static DeferredBlock<Block> RUNIC_LANTERN = register("runic_lantern",
            () -> new LanternBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.LANTERN).mapColor(MapColor.DEEPSLATE).lightLevel(state -> 8)));


    public static final DeferredBlock<Block> VERIDIUM_ORE = register(
            "veridium_ore",
            () -> new AetherDoubleDropBlock(
                    Block.Properties.of()
                            .mapColor(MapColor.WOOL)
                            .instrument(NoteBlockInstrument.BASEDRUM)
                            .strength(3.0F)
                            .requiresCorrectToolForDrops()
            )
    );

    public static final DeferredBlock<Block> RAW_VERIDIUM_BLOCK = register(
            "raw_veridium_block",
            () -> new Block(
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.LAPIS)
                            .instrument(NoteBlockInstrument.BASEDRUM)
                            .requiresCorrectToolForDrops()
                            .sound(SoundType.STONE)
                            .strength(3.0F, 6.0F)
            )
    );

    public static final DeferredBlock<Block> VERIDIUM_BLOCK = register(
            "veridium_block",
            () -> new Block(
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.LAPIS)
//                            .instrument(NoteBlockInstrument.)
                            .requiresCorrectToolForDrops()
                            .strength(5.0F, 6.0F)
                            .sound(SoundType.METAL)
            )
    );

    public static final DeferredBlock<Block> REFINED_SENTRITE_BLOCK = register(
            "refined_sentrite_block",
            () -> new Block(
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.COLOR_GRAY)
//                            .instrument(NoteBlockInstrument.IRON_XYLOPHONE)
                            .requiresCorrectToolForDrops()
                            .strength(6.0F, 6.0F)
                            .sound(SoundType.NETHERITE_BLOCK)
            )
    );

    public static DeferredBlock<HangingAetherVinesHead> GOLDEN_VINES = register("golden_vines",
            () -> new HangingAetherVinesHead(BlockBehaviour.Properties.ofFullCopy(Blocks.WEEPING_VINES)
                    .mapColor(MapColor.GOLD).sound(SoundType.CAVE_VINES), BlockTags.LEAVES, ReduxBlocks.GOLDEN_VINES_PLANT));

    public static DeferredBlock<HangingAetherVinesBody> GOLDEN_VINES_PLANT = BLOCKS.register("golden_vines_plant",
            () -> new HangingAetherVinesBody(BlockBehaviour.Properties.ofFullCopy(Blocks.WEEPING_VINES_PLANT)
                    .mapColor(MapColor.GOLD).sound(SoundType.CAVE_VINES), BlockTags.LEAVES, ReduxBlocks.GOLDEN_VINES));

    public static final DeferredBlock<LogicatorBlock> LOGICATOR = register("logicator",
            () -> new LogicatorBlock(BlockBehaviour.Properties.of().instabreak().sound(SoundType.STONE).pushReaction(PushReaction.DESTROY))
    );

    public static void registerFlammability() {
        FireAccessor accessor = (FireAccessor) Blocks.FIRE;
        Redux.BLOCK_SETS.forEach(set -> set.flammables(accessor));
    }

    public static void registerToolConversions() {
        Redux.BLOCK_SETS.forEach(set -> {
            if (set instanceof AbstractWoodSet wood) {
                wood.setupStrippables(BlockHooks.ToolConversions.STRIPPABLES);
            }
        });
    }
}
