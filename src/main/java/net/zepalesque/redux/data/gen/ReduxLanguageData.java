package net.zepalesque.redux.data.gen;

import net.minecraft.data.PackOutput;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.client.audio.ReduxSounds;
import net.zepalesque.redux.data.prov.ReduxLanguageProvider;
import net.zepalesque.redux.item.ReduxItems;
import net.zepalesque.zenith.util.DatagenUtil;

public class ReduxLanguageData extends ReduxLanguageProvider {

    public ReduxLanguageData(PackOutput output) {
        super(output, Redux.MODID);
    }

    @Override
    protected void addTranslations() {
        Redux.BLOCK_SETS.forEach(set -> set.langData(this));

        addBlock(ReduxBlocks.SHORT_AETHER_GRASS);
        addLore(ReduxBlocks.SHORT_AETHER_GRASS, "Blades of the Aether's grass. It feels slightly cool to the touch.");

        addBlock(ReduxBlocks.GILDENROOT_LEAVES);
        addLore(ReduxBlocks.GILDENROOT_LEAVES, "Leaves of the Gildenroot tree, a variation of Skyroot that has been touched by Ambrosium but has not fully adapted as Golden Oaks have. These sometimes will drop Gildenroot Saplings");

        addBlock(ReduxBlocks.GILDENROOT_LEAF_PILE);
        addLore(ReduxBlocks.GILDENROOT_LEAF_PILE, "A pile of Gildenroot Leaves. These can be stacked on top of eachother to make various sizes!");

        addBlock(ReduxBlocks.GOLDEN_OAK_LEAF_PILE);
        addLore(ReduxBlocks.GOLDEN_OAK_LEAF_PILE, "A pile of Golden Oak Leaves. These can be stacked on top of eachother to make various sizes!");

        addBlock(ReduxBlocks.SKYROOT_LEAF_PILE);
        addLore(ReduxBlocks.SKYROOT_LEAF_PILE, "A pile of Skyroot Leaves. These can be stacked on top of eachother to make various sizes!");

        addBlock(ReduxBlocks.GOLDEN_CLOVERS);
        addLore(ReduxBlocks.GOLDEN_CLOVERS, "A nice patch of clovers that can be found in the Gilded Groves.");

        addBlock(ReduxBlocks.GOLDEN_VINES);
        addBlock(ReduxBlocks.GOLDEN_VINES_PLANT);
        addLore(ReduxBlocks.GOLDEN_VINES, "A golden vine that grows in a symbiotic relationship with Golden Oak trees when they are healthy, such as when found in the Gilded Groves");

        addBlock(ReduxBlocks.CARVED_PILLAR);
        addLore(ReduxBlocks.CARVED_PILLAR, "A pillar made of Carved Stone. Pillars look nice for supporting a build, along with giving it nice corners.");
        addBlock(ReduxBlocks.SENTRY_PILLAR);
        addLore(ReduxBlocks.SENTRY_PILLAR, "A pillar made of Sentry Stone. Pillars look nice for supporting a build, along with giving it nice corners.");
        addBlock(ReduxBlocks.CARVED_BASE);
        addLore(ReduxBlocks.CARVED_BASE, "A nice decorative base block made of Carved Stone. Looks very nice at the bottom of walls!");
        addBlock(ReduxBlocks.SENTRY_BASE);
        addLore(ReduxBlocks.SENTRY_BASE, "A nice decorative base block made of Sentry Stone. Looks very nice at the bottom of walls!");

        addBlock(ReduxBlocks.LOCKED_CARVED_PILLAR);
        addBlock(ReduxBlocks.LOCKED_SENTRY_PILLAR);
        addBlock(ReduxBlocks.LOCKED_CARVED_BASE);
        addBlock(ReduxBlocks.LOCKED_SENTRY_BASE);

        addBlock(ReduxBlocks.TRAPPED_CARVED_PILLAR);
        addBlock(ReduxBlocks.TRAPPED_SENTRY_PILLAR);
        addBlock(ReduxBlocks.TRAPPED_CARVED_BASE);
        addBlock(ReduxBlocks.TRAPPED_SENTRY_BASE);

        addBlock(ReduxBlocks.BOSS_DOORWAY_CARVED_PILLAR);
        addBlock(ReduxBlocks.BOSS_DOORWAY_SENTRY_PILLAR);
        addBlock(ReduxBlocks.BOSS_DOORWAY_CARVED_BASE);
        addBlock(ReduxBlocks.BOSS_DOORWAY_SENTRY_BASE);

        addBlock(ReduxBlocks.RUNELIGHT);
        addLore(ReduxBlocks.RUNELIGHT, "A glowing block of circuitry made of Veridium, which can be easily toggled on and off. Found in Bronze Dungeons.");
        addBlock(ReduxBlocks.LOCKED_RUNELIGHT);

        addBlock(ReduxBlocks.LOCKED_SENTRITE_BRICKS);


        addBlock(ReduxBlocks.WYNDSPROUTS);
        addBlock(ReduxBlocks.POTTED_WYNDSPROUTS);
        addLore(ReduxBlocks.WYNDSPROUTS, "A common plant found in the Aether. They occasionally drop Wynd Oats, the main edible source of grain in the Aether.");

        addBlock(ReduxBlocks.SKYSPROUTS);
        addBlock(ReduxBlocks.POTTED_SKYSPROUTS);
        addLore(ReduxBlocks.SKYSPROUTS, "A relative of the common Wyndsprouts, this flowering grass is found in the Skyfields.");

        addBlock(ReduxBlocks.LOGICATOR, "Redstone Logicator");
        // TODO
        addLore(ReduxBlocks.LOGICATOR, "A fascinating circuit made with an exotic material not found in the Aether - Redstone. This little diode takes in two inputs on the side, and will perform a logical operation on the two for the output. The operation is controlled by the torch on the top and the back input. The torch controls AND/OR mode, and the back input controls exclusivity (XNOR/XOR).");


        addItem(ReduxItems.WYND_OATS);
        addLore(ReduxItems.WYND_OATS, "A pile of Wynd Oats. These can be grown into the Wynd Oat plant.");
        addItem(ReduxItems.WYND_OAT_PANICLE);
        addLore(ReduxItems.WYND_OAT_PANICLE, "A panicle of grown Wynd Oats. This can be used for a variety of recipes.");
        // TODO: Reimplement said recipes

//        add(ReduxItems.WYND_BAGEL);
//        addLore(ReduxItems.WYND_BAGEL, "A nice bagel made with some harvested Wynd Oats.");
//
//        add(ReduxItems.BLUEBERRY_BAGEL);
//        addLore(ReduxItems.BLUEBERRY_BAGEL, "A bagel made with Blue Berries. This is much more filling than a plain Wynd Bagel");
//
//        add(ReduxItems.OATMEAL);
//        addLore(ReduxItems.OATMEAL, "A nice bowl of Oatmeal. Specifically, this is Wynd Oatmeal, as it was made with Wynd Oats.");

        addItem(ReduxItems.VERIDIUM_INGOT);
        addLore(ReduxItems.VERIDIUM_INGOT, "An bar of pure Veridium, a metal that when coming in contact with ambrosium, takes on a glowing light blue color, strengthening temporarily.");
        addItem(ReduxItems.VERIDIUM_NUGGET);
        addLore(ReduxItems.VERIDIUM_NUGGET, "A small chunk of Veridium. This can be crafted to and from Veridium Ingots.");

        addItem(ReduxItems.RAW_VERIDIUM);
        addLore(ReduxItems.RAW_VERIDIUM, "A chunk of Raw Veridium. This can be smelted into an ingot.");


        addItem(ReduxItems.REFINED_SENTRITE);
        addLore(ReduxItems.REFINED_SENTRITE, "The purified form of Sentrite. This can be used for a variety of different things, but is commonly found associated with Sentry technology.");

        addItem(ReduxItems.SENTRITE_CHUNK);
        addLore(ReduxItems.SENTRITE_CHUNK, "A chunk of purified Sentrite. These are occasionally dropped from Sentries, and can be crafted into Refined Sentrite.");

        addBlock(ReduxBlocks.SENTRITE_CHAIN);
        addLore(ReduxBlocks.SENTRITE_CHAIN, "A chain made of purified Sentrite. This is crafted with a piece of Refined Sentrite and two Sentrite Chunks.");
        addBlock(ReduxBlocks.SENTRITE_LANTERN);
        addLore(ReduxBlocks.SENTRITE_LANTERN, "A lantern made of Sentrite. You can place it on the ground or hang it on the ceiling!");

        addItem(ReduxItems.VERIDIUM_PICKAXE);
        addLore(ReduxItems.VERIDIUM_PICKAXE, "A pickaxe made of Veridium. This can be infused by right-clicking with an Ambrosium Shard to make it far more powerful for a short time!");
        addItem(ReduxItems.INFUSED_VERIDIUM_PICKAXE);
        addLore(ReduxItems.INFUSED_VERIDIUM_PICKAXE, "A pickaxe made of Veridium. This can be infused by right-clicking with an Ambrosium Shard to make it far more powerful for a short time!");

        addItem(ReduxItems.VERIDIUM_AXE);
        addLore(ReduxItems.VERIDIUM_AXE, "A axe made of Veridium. This can be infused by right-clicking with an Ambrosium Shard to make it far more powerful for a short time!");
        addItem(ReduxItems.INFUSED_VERIDIUM_AXE);
        addLore(ReduxItems.INFUSED_VERIDIUM_AXE, "A axe made of Veridium. This can be infused by right-clicking with an Ambrosium Shard to make it far more powerful for a short time!");

        addItem(ReduxItems.VERIDIUM_SHOVEL);
        addLore(ReduxItems.VERIDIUM_SHOVEL, "A shovel made of Veridium. This can be infused by right-clicking with an Ambrosium Shard to make it far more powerful for a short time!");
        addItem(ReduxItems.INFUSED_VERIDIUM_SHOVEL);
        addLore(ReduxItems.INFUSED_VERIDIUM_SHOVEL, "A shovel made of Veridium. This can be infused by right-clicking with an Ambrosium Shard to make it far more powerful for a short time!");

        addItem(ReduxItems.VERIDIUM_HOE);
        addLore(ReduxItems.VERIDIUM_HOE, "A hoe made of Veridium. This can be infused by right-clicking with an Ambrosium Shard to make it far more powerful for a short time!");
        addItem(ReduxItems.INFUSED_VERIDIUM_HOE);
        addLore(ReduxItems.INFUSED_VERIDIUM_HOE, "A hoe made of Veridium. This can be infused by right-clicking with an Ambrosium Shard to make it far more powerful for a short time!");

        addBlock(ReduxBlocks.VERIDIUM_ORE);
        addLore(ReduxBlocks.VERIDIUM_ORE, "The ore of Veridium. This can be found around the Aether");

        addBlock(ReduxBlocks.RAW_VERIDIUM_BLOCK, "Block of Raw Veridium");
        addLore(ReduxBlocks.RAW_VERIDIUM_BLOCK, "A block of raw Veridium. This can be crafted from Raw Veridium.");

        addBlock(ReduxBlocks.VERIDIUM_BLOCK, "Block of Veridium");
        addLore(ReduxBlocks.VERIDIUM_BLOCK, "A block of pure Veridium. This can be crafted from Veridium Ingots.");

        addBlock(ReduxBlocks.REFINED_SENTRITE_BLOCK, "Block of Refined Sentrite");
        addLore(ReduxBlocks.REFINED_SENTRITE_BLOCK, "A block of the refined form of Sentrite, crafted with Refined Sentrite.");

        addGuiText("shift_info", "Hold [%s] for more info...");
        addTooltip("infusion_charge", "Infusion Charge: %s");
        addGuiText("infusion_info", "Can be infused by right-clicking with an Ambrosium Shard");

        addPackDescription("mod", "The Aether: Redux Resources");
        addPackTitle("tintable_grass", "Redux - Tintable Grass");
        addPackDescription("tintable_grass", "Grass tint textures for the Aether: Redux");

        addPackTitle("bronze_upgrade", "Redux - Bronze Dungeon Upgrade");
        addPackDescription("bronze_upgrade", "Configurable in config/aether_redux/common.toml");

        addSubtitle(ReduxSounds.INFUSE_ITEM, DatagenUtil::subtitleFor, "Item infuses");
        addSubtitle(ReduxSounds.INFUSION_EXPIRE, DatagenUtil::subtitleFor, "Item infusion runs out");
        addSubtitle(ReduxSounds.LOGICATOR_CLICK, DatagenUtil::subtitleFor, "Logicator clicks");
    }
}
