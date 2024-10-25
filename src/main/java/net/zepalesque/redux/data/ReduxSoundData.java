package net.zepalesque.redux.data;

import com.aetherteam.aether.Aether;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SoundDefinitionsProvider;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.client.audio.ReduxSoundEvents;

public class ReduxSoundData extends SoundDefinitionsProvider {
    public ReduxSoundData(PackOutput output, String modid, ExistingFileHelper helper) {
        super(output, modid, helper);
    }

    @Override
    public void registerSounds() {

        this.add(ReduxSoundEvents.MIMIC_SLAM,
                definition().with(sound("aether_redux:entity/mimic/mimic_slam"))
                        .subtitle("subtitles.aether_redux.entity.mimic_slam"));
        this.add(ReduxSoundEvents.HEARTBEAT_FAST,
                definition().with(sound("aether_redux:generic/heartbeat/heartbeat_fast")));
        this.add(ReduxSoundEvents.HEARTBEAT_MED,
                definition().with(sound("aether_redux:generic/heartbeat/heartbeat_med")));
        this.add(ReduxSoundEvents.HEARTBEAT_SLOW,
                definition().with(sound("aether_redux:generic/heartbeat/heartbeat_slow")));
        this.add(ReduxSoundEvents.SENTRY_LAND_BASE,
                definition().with(sound("minecraft:mob/slime/big1"), sound("minecraft:mob/slime/big2"), sound("minecraft:mob/slime/big3"), sound("minecraft:mob/slime/big4"))
                        .subtitle("subtitles.aether.entity.sentry.jump"));
        this.add(ReduxSoundEvents.SENTRY_AMBIENT, definition().with(sound("aether_redux:generic/silence")));
        this.add(ReduxSoundEvents.EQUIP_BITTERSWEET_CHARM,
                definition().with(
                        sound("minecraft:item/armor/equip_chain1"),
                        sound("minecraft:item/armor/equip_chain2"),
                        sound("minecraft:item/armor/equip_chain3"),
                        sound("minecraft:item/armor/equip_chain4"),
                        sound("minecraft:item/armor/equip_chain5"),
                        sound("minecraft:item/armor/equip_chain6")
                ).subtitle("subtitles.aether_redux.item.accessory.equip_bittersweet_charm")
        );
        this.add(ReduxSoundEvents.EQUIP_ENCHANTED_RING,
                definition().with(
                        sound("minecraft:mob/turtle/armor")
                ).subtitle("subtitles.aether_redux.item.accessory.equip_enchanted_ring")
        );
        this.add(ReduxSoundEvents.EQUIP_SHROOM_RING,
                definition().with(
                        sound("minecraft:mob/turtle/armor")
                ).subtitle("subtitles.aether_redux.item.accessory.equip_shroom_ring")
        );
        this.add(ReduxSoundEvents.EQUIP_GRAND_MEDAL,
                definition().with(
                        sound("minecraft:item/armor/equip_gold1"),
                        sound("minecraft:item/armor/equip_gold2"),
                        sound("minecraft:item/armor/equip_gold3"),
                        sound("minecraft:item/armor/equip_gold4"),
                        sound("minecraft:item/armor/equip_gold5"),
                        sound("minecraft:item/armor/equip_gold6")
                ).subtitle("subtitles.aether_redux.item.accessory.equip_grand_medal")
        );
        this.add(ReduxSoundEvents.EQUIP_CONSTRUCTION_RING,
                definition().with(
                        sound("minecraft:item/armor/equip_gold1"),
                        sound("minecraft:item/armor/equip_gold2"),
                        sound("minecraft:item/armor/equip_gold3"),
                        sound("minecraft:item/armor/equip_gold4"),
                        sound("minecraft:item/armor/equip_gold5"),
                        sound("minecraft:item/armor/equip_gold6")
                ).subtitle("subtitles.aether_redux.item.accessory.equip_construction_ring")
        );
        this.add(ReduxSoundEvents.SWET_ATTACK,
                definition().with(
                        sound("minecraft:mob/slime/attack1"),
                        sound("minecraft:mob/slime/attack2")
                ).subtitle("subtitles.aether_redux.entity.swet_attack")
        );
        this.add(ReduxSoundEvents.EQUIP_WISDOM_RING,
                definition().with(
                        sound("minecraft:item/armor/equip_iron1"),
                        sound("minecraft:item/armor/equip_iron2"),
                        sound("minecraft:item/armor/equip_iron3"),
                        sound("minecraft:item/armor/equip_iron4"),
                        sound("minecraft:item/armor/equip_iron5"),
                        sound("minecraft:item/armor/equip_iron6")
                ).subtitle("subtitles.aether_redux.item.accessory.equip_wisdom_ring")
        );
        this.add(ReduxSoundEvents.EQUIP_SENTRY_RING,
                definition().with(
                        sound("minecraft:item/armor/equip_iron1"),
                        sound("minecraft:item/armor/equip_iron2"),
                        sound("minecraft:item/armor/equip_iron3"),
                        sound("minecraft:item/armor/equip_iron4"),
                        sound("minecraft:item/armor/equip_iron5"),
                        sound("minecraft:item/armor/equip_iron6")
                ).subtitle("subtitles.aether_redux.item.accessory.equip_sentry_ring")
        );
        this.add(ReduxSoundEvents.EQUIP_VAMPIRE_AMULET,
                definition().with(
                        sound("minecraft:item/armor/equip_gold1"),
                        sound("minecraft:item/armor/equip_gold2"),
                        sound("minecraft:item/armor/equip_gold3"),
                        sound("minecraft:item/armor/equip_gold4"),
                        sound("minecraft:item/armor/equip_gold5"),
                        sound("minecraft:item/armor/equip_gold6")
                ).subtitle("subtitles.aether_redux.item.accessory.equip_vampire_amulet")
        );
        this.add(ReduxSoundEvents.BOOST_JUMP,
                definition().with(sound("aether_redux:generic/boost_jump"))
                        .subtitle("subtitles.aether_redux.generic.boost_jump"));
        this.add(ReduxSoundEvents.FIREBALL_SHOOT,
                definition().with(sound("aether_redux:generic/fireball_shoot"))
                        .subtitle("subtitles.aether_redux.generic.fireball_shoot"));
        this.add(ReduxSoundEvents.BLIGHTSHADE_SPRAY,
                definition().with(sound("aether_redux:block/blightshade_spray"))
                        .subtitle("subtitles.aether_redux.block.blightshade_spray"));
        this.add(ReduxSoundEvents.MUSIC_DISC_SENTIENCE, definition().with(sound("aether_redux:music/ancient_sentrite_disc").stream()));
        this.add(ReduxSoundEvents.CONVERT_AMBROSIUM,
                definition().with(sound("aether_redux:item/convert_ambrosium"))
                        .subtitle("subtitles.aether_redux.item.convert_ambrosium"));
        this.add(ReduxSoundEvents.CONVERT_SWET_BALL,
                definition().with(sound("aether_redux:item/convert_swet_ball"))
                        .subtitle("subtitles.aether_redux.item.convert_swet_ball"));
        this.add(ReduxSoundEvents.CONVERT_BLIGHTED_SPORES,
                definition().with(sound("aether_redux:item/convert_blighted_spores"))
                        .subtitle("subtitles.aether_redux.item.convert_blighted_spores"));
    this.add(ReduxSoundEvents.INFUSE_ITEM,
            definition().with(sound("aether_redux:item/infuse_item"))
                    .subtitle("subtitles.aether_redux.item.infuse_item"));
        this.add(ReduxSoundEvents.INFUSION_EXPIRE,
                definition().with(sound("aether_redux:item/infusion_expire"))
                        .subtitle("subtitles.aether_redux.item.infusion_expire"));
        this.add(ReduxSoundEvents.SPEAR_THROW,
                definition().with(sound("aether_redux:item/spear_throw"))
                        .subtitle("subtitles.aether_redux.item.spear_throw"));
        this.add(ReduxSoundEvents.SPEAR_LAND,
                definition().with(sound("aether_redux:item/spear_land"))
                        .subtitle("subtitles.aether_redux.item.spear_land"));
        this.add(ReduxSoundEvents.QUICKROOTS_PICK, definition().with(
                sound("minecraft:item/sweet_berries/pick_from_bush1"),
                sound("minecraft:item/sweet_berries/pick_from_bush2")
        ).subtitle("subtitles.aether_redux.block.quickroots_pick"));
        this.add(ReduxSoundEvents.LIGHTROOTS_PICK, definition().with(
                sound("minecraft:item/sweet_berries/pick_from_bush1"),
                sound("minecraft:item/sweet_berries/pick_from_bush2")
        ).subtitle("subtitles.aether_redux.block.lightroots_pick"));
        this.add(ReduxSoundEvents.FUNGUS_BOUNCE,
                definition().with(sound("aether_redux:block/fungus/bounce"))
                        .subtitle("subtitles.aether_redux.block.fungus_bounce")
        );

        this.add(ReduxSoundEvents.EMBER_BOUNCE_BIG, definition().with(
                sound("aether_redux:entity/ember/ember_bouncebig")
        ).subtitle("subtitles.aether_redux.entity.ember_bounce"));

        this.add(ReduxSoundEvents.EMBER_BOUNCE_MED, definition().with(
                sound("aether_redux:entity/ember/ember_bouncemed")
        ).subtitle("subtitles.aether_redux.entity.ember_bounce"));

        this.add(ReduxSoundEvents.EMBER_BOUNCE_SMALL, definition().with(
                sound("aether_redux:entity/ember/ember_bouncesmall")
        ).subtitle("subtitles.aether_redux.entity.ember_bounce"));

        this.add(ReduxSoundEvents.GLOWSTONE_BREAK,
                definition().with(
                        sound("aether_redux:block/glowstone/break1"),
                        sound("aether_redux:block/glowstone/break2"),
                        sound("aether_redux:block/glowstone/break3")
                ).subtitle("subtitles.block.generic.break")
        );
        this.add(ReduxSoundEvents.GLOWSTONE_PLACE,
                definition().with(
                        sound("aether_redux:block/glowstone/place1"),
                        sound("aether_redux:block/glowstone/place2"),
                        sound("aether_redux:block/glowstone/place3"),
                        sound("aether_redux:block/glowstone/place4")
                ).subtitle("subtitles.block.generic.place")
        );
        this.add(ReduxSoundEvents.GLOWSTONE_STEP,
                definition().with(
                        sound("aether_redux:block/glowstone/step1"),
                        sound("aether_redux:block/glowstone/step2"),
                        sound("aether_redux:block/glowstone/step3"),
                        sound("aether_redux:block/glowstone/step4"),
                        sound("aether_redux:block/glowstone/step5"),
                        sound("aether_redux:block/glowstone/step6"),
                        sound("aether_redux:block/glowstone/step7"),
                        sound("aether_redux:block/glowstone/step8"),
                        sound("aether_redux:block/glowstone/step9"),
                        sound("aether_redux:block/glowstone/step10"),
                        sound("aether_redux:block/glowstone/step11"),
                        sound("aether_redux:block/glowstone/step12")
                ).subtitle("subtitles.block.generic.footsteps")
        );
        this.add(ReduxSoundEvents.GLOWSTONE_HIT,
                definition().with(
                        sound("aether_redux:block/glowstone/step1"),
                        sound("aether_redux:block/glowstone/step2"),
                        sound("aether_redux:block/glowstone/step3"),
                        sound("aether_redux:block/glowstone/step4"),
                        sound("aether_redux:block/glowstone/step5"),
                        sound("aether_redux:block/glowstone/step6"),
                        sound("aether_redux:block/glowstone/step7"),
                        sound("aether_redux:block/glowstone/step8"),
                        sound("aether_redux:block/glowstone/step9"),
                        sound("aether_redux:block/glowstone/step10"),
                        sound("aether_redux:block/glowstone/step11"),
                        sound("aether_redux:block/glowstone/step12")
                ).subtitle("subtitles.block.generic.hit")
        );
        this.add(ReduxSoundEvents.GLOWSTONE_FALL,
                definition().with(
                        sound("aether_redux:block/glowstone/step1"),
                        sound("aether_redux:block/glowstone/step2"),
                        sound("aether_redux:block/glowstone/step3"),
                        sound("aether_redux:block/glowstone/step4"),
                        sound("aether_redux:block/glowstone/step5"),
                        sound("aether_redux:block/glowstone/step6"),
                        sound("aether_redux:block/glowstone/step7"),
                        sound("aether_redux:block/glowstone/step8"),
                        sound("aether_redux:block/glowstone/step9"),
                        sound("aether_redux:block/glowstone/step10"),
                        sound("aether_redux:block/glowstone/step11"),
                        sound("aether_redux:block/glowstone/step12")
                )
        );


        this.add(ReduxSoundEvents.SHIMMERCOW_AMBIENT,
                definition().with(sound("aether_redux:entity/shimmercow/ambient1")).with(sound("aether_redux:entity/shimmercow/ambient2"))
                        .subtitle("subtitles.aether_redux.entity.shimmercow_ambient"));
        this.add(ReduxSoundEvents.SHIMMERCOW_HURT,
                definition().with(sound("aether_redux:entity/shimmercow/hurt"))
                        .subtitle("subtitles.aether_redux.entity.shimmercow_hurt"));
        this.add(ReduxSoundEvents.SHIMMERCOW_DEATH,
                definition().with(sound("aether_redux:entity/shimmercow/hurt"))
                        .subtitle("subtitles.aether_redux.entity.shimmercow_death"))
        ;
        this.add(ReduxSoundEvents.CRAZY_COW_AMBIENT,
                definition().with(sound("aether_redux:entity/shimmercow/vwakazoom"))
                        .subtitle("subtitles.aether_redux.entity.crazy_cow_ambient"));
        this.add(ReduxSoundEvents.CRAZY_COW_HURT,
                definition().with(sound("aether_redux:entity/shimmercow/noo"))
                        .subtitle("subtitles.aether_redux.entity.crazy_cow_hurt"));
        this.add(ReduxSoundEvents.CRAZY_COW_DEATH,
                definition().with(sound("aether_redux:entity/shimmercow/y_do_u_do_dis"))
                        .subtitle("subtitles.aether_redux.entity.crazy_cow_death"));

        this.add(ReduxSoundEvents.MYKAPOD_SHELL_CRACK,
                definition()
                        .with(sound("mob/turtle/egg/egg_crack1"))
                        .with(sound("mob/turtle/egg/egg_crack2"))
                        .with(sound("mob/turtle/egg/egg_crack3"))
                        .with(sound("mob/turtle/egg/egg_crack4"))
                        .with(sound("mob/turtle/egg/egg_crack5"))
                        .subtitle("subtitles.aether_redux.entity.mykapod_shell_crack"));
        this.add(ReduxSoundEvents.MYKAPOD_SHELL_SHED,
                definition()
                        .with(sound("mob/turtle/egg/jump_egg1"))
                        .with(sound("mob/turtle/egg/jump_egg2"))
                        .with(sound("mob/turtle/egg/jump_egg3"))
                        .with(sound("mob/turtle/egg/jump_egg4"))
                        .subtitle("subtitles.aether_redux.entity.mykapod_shell_shed"));

        this.add(ReduxSoundEvents.MYKAPOD_SHELL_BREAK,
                definition()
                        .with(sound("mob/turtle/egg/egg_break1"))
                        .with(sound("mob/turtle/egg/egg_break2"))
                        .subtitle("subtitles.aether_redux.entity.mykapod_shell_break"));


        this.add(ReduxSoundEvents.MYKAPOD_MOVE,
                definition()
                        .with(sound("block/honeyblock/slide1").volume(0.8))
                        .with(sound("block/honeyblock/slide1").pitch(0.9).volume(0.8))
                        .with(sound("block/honeyblock/slide2").volume(0.8))
                        .with(sound("block/honeyblock/slide2").pitch(0.8).volume(0.8))
                        .with(sound("block/honeyblock/slide3").volume(0.8))
                        .with(sound("block/honeyblock/slide3").pitch(0.8).volume(0.8))
                        .with(sound("block/honeyblock/slide4").volume(0.8))
                        .with(sound("block/honeyblock/slide4").pitch(0.8).volume(0.8))
                        .subtitle("subtitles.aether_redux.entity.mykapod_move"));


        this.add(ReduxSoundEvents.MYKAPOD_HURT_HIDING,
                definition()
                        .with(sound("block/deepslate/step1"))
                        .with(sound("block/deepslate/step2"))
                        .with(sound("block/deepslate/step3"))
                        .with(sound("block/deepslate/step4"))
                        .with(sound("block/deepslate/step5"))
                        .with(sound("block/deepslate/step6"))
                        .subtitle("subtitles.aether_redux.entity.mykapod_hurt"));

        this.add(ReduxSoundEvents.MYKAPOD_HURT,
                definition()
                        .with(sound(Redux.locate("entity/mykapod/hurt1")))
                        .with(sound(Redux.locate("entity/mykapod/hurt2")))
                        .with(sound(Redux.locate("entity/mykapod/hurt3")))
                        .subtitle("subtitles.aether_redux.entity.mykapod_hurt"));

        this.add(ReduxSoundEvents.MYKAPOD_DEATH,
                definition()
                        .with(sound(Redux.locate("entity/mykapod/hurt1")))
                        .with(sound(Redux.locate("entity/mykapod/hurt2")))
                        .with(sound(Redux.locate("entity/mykapod/hurt3")))
                        .subtitle("subtitles.aether_redux.entity.mykapod_death"));
        
        this.add(ReduxSoundEvents.BLIGHTBUNNY_HURT,
                definition()
                        .with(sound(Redux.locate("entity/blightbunny/hurt")))
                        .subtitle("subtitles.aether_redux.entity.blightbunny_hurt"));

        this.add(ReduxSoundEvents.BLIGHTBUNNY_DEATH,
                definition()
                        .with(sound(Redux.locate("entity/blightbunny/death")))
                        .subtitle("subtitles.aether_redux.entity.blightbunny_death"));

        this.add(ReduxSoundEvents.ADVANCEMENT_GOLD,
                definition().with(sound(Redux.locate("achievement/achievement_gold")).stream()));

        this.add(ReduxSoundEvents.SUBZERO_CROSSBOW_SHOOT,
                definition()
                        .with(sound(Redux.locate("item/subzero_crossbow/shoot1")))
                        .with(sound(Redux.locate("item/subzero_crossbow/shoot2")))
                        .with(sound(Redux.locate("item/subzero_crossbow/shoot3")))
                        .subtitle("subtitles.aether_redux.item.subzero_crossbow_shoot"));

        this.add(ReduxSoundEvents.SUBZERO_CROSSBOW_LOADING_START,
                definition()
                        .with(sound(Redux.locate("item/subzero_crossbow/loading_start")))
                        .subtitle("subtitles.aether_redux.item.subzero_crossbow_charge"));

        this.add(ReduxSoundEvents.SUBZERO_CROSSBOW_LOADING_MIDDLE,
                definition()
                        .with(sound("item/crossbow/loading_middle1"))
                        .with(sound("item/crossbow/loading_middle2"))
                        .with(sound("item/crossbow/loading_middle3"))
        );
        this.add(ReduxSoundEvents.SUBZERO_CROSSBOW_LOADING_END,
                definition()
                        .with(sound(Redux.locate("item/subzero_crossbow/loading_end")))
                        .subtitle("subtitles.aether_redux.item.subzero_crossbow_load"));

        this.add(ReduxSoundEvents.SUBZERO_CROSSBOW_QUICK_CHARGE_1,
                definition()
                        .with(sound(Redux.locate("item/subzero_crossbow/quick_charge/quick1_1")))
                        .with(sound(Redux.locate("item/subzero_crossbow/quick_charge/quick1_2")))
                        .with(sound(Redux.locate("item/subzero_crossbow/quick_charge/quick1_3")))
                        .subtitle("subtitles.aether_redux.item.subzero_crossbow_charge"));

        this.add(ReduxSoundEvents.SUBZERO_CROSSBOW_QUICK_CHARGE_2,
                definition()
                        .with(sound(Redux.locate("item/subzero_crossbow/quick_charge/quick2_1")))
                        .with(sound(Redux.locate("item/subzero_crossbow/quick_charge/quick2_2")))
                        .with(sound(Redux.locate("item/subzero_crossbow/quick_charge/quick2_3")))
                        .subtitle("subtitles.aether_redux.item.subzero_crossbow_charge"));

        this.add(ReduxSoundEvents.SUBZERO_CROSSBOW_QUICK_CHARGE_3,
                definition()
                        .with(sound(Redux.locate("item/subzero_crossbow/quick_charge/quick3_1")))
                        .with(sound(Redux.locate("item/subzero_crossbow/quick_charge/quick3_2")))
                        .with(sound(Redux.locate("item/subzero_crossbow/quick_charge/quick3_3")))
                        .subtitle("subtitles.aether_redux.item.subzero_crossbow_charge"));

        this.add(ReduxSoundEvents.SUBZERO_CROSSBOW_HIT,
                definition()
                        .with(sound(Redux.locate("item/subzero_crossbow/arrow/hit1")))
                        .with(sound(Redux.locate("item/subzero_crossbow/arrow/hit2")))
                        .with(sound(Redux.locate("item/subzero_crossbow/arrow/hit3")))
                        .subtitle("subtitles.aether_redux.item.subzero_crossbow_hit"));

        this.add(ReduxSoundEvents.GOLD_AERCLOUD_WHOOSH,
                definition()
                        .with(sound(new ResourceLocation(Aether.MODID, "entity/zephyr/shoot")))
                        .subtitle("subtitles.aether_redux.block.aercloud.gold_aercloud_whoosh"));

        this.add(ReduxSoundEvents.PURPLE_AERCLOUD_ZOOM,
                definition()
                        .with(sound(Redux.locate("block/aercloud/purple_aercloud_zoom")))
                        .subtitle("subtitles.aether_redux.block.aercloud.purple_aercloud_zoom"));

        this.add(ReduxSoundEvents.GREEN_AERCLOUD_WUBBLE,
                definition()
                        .with(sound(Redux.locate("block/aercloud/green_aercloud_wubble")))
                        .subtitle("subtitles.aether_redux.block.aercloud.green_aercloud_wubble"));

        this.add(ReduxSoundEvents.REDUX_MENU,
                definition()
                        .with(sound(Redux.locate("music/redux_menu")).stream()));
    }

}