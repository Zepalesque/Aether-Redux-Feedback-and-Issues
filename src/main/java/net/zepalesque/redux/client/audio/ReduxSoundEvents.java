package net.zepalesque.redux.client.audio;

import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.zepalesque.redux.Redux;

public class ReduxSoundEvents {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Redux.MODID);
    public static final RegistryObject<SoundEvent> SENTRY_LAND_BASE = register("entity.sentry.land_base");
    public static final RegistryObject<SoundEvent> SENTRY_AMBIENT = register("entity.sentry.ambient");;
    public static final RegistryObject<SoundEvent> MIMIC_SLAM = register("entity.mimic.slam");

    public static final RegistryObject<SoundEvent> SHIMMERCOW_AMBIENT = register("entity.shimmercow.ambient");
    public static final RegistryObject<SoundEvent> SHIMMERCOW_HURT = register("entity.shimmercow.hurt");
    public static final RegistryObject<SoundEvent> SHIMMERCOW_DEATH = register("entity.shimmercow.death");

    public static final RegistryObject<SoundEvent> SWET_ATTACK = register("entity.swet.attack");

    public static final RegistryObject<SoundEvent> MYKAPOD_SHELL_CRACK = register("entity.mykapod.shell_crack");
    public static final RegistryObject<SoundEvent> MYKAPOD_SHELL_SHED = register("entity.mykapod.shell_shed");
    public static final RegistryObject<SoundEvent> MYKAPOD_SHELL_BREAK = register("entity.mykapod.shell_break");
    public static final RegistryObject<SoundEvent> MYKAPOD_MOVE = register("entity.mykapod.move");
    public static final RegistryObject<SoundEvent> MYKAPOD_HURT_HIDING = register("entity.mykapod.hurt_hiding");
    public static final RegistryObject<SoundEvent> MYKAPOD_HURT = register("entity.mykapod.hurt");
    public static final RegistryObject<SoundEvent> MYKAPOD_DEATH = register("entity.mykapod.death");

    public static final RegistryObject<SoundEvent> BLIGHTBUNNY_HURT = register("entity.blightbunny.hurt");
    public static final RegistryObject<SoundEvent> BLIGHTBUNNY_DEATH = register("entity.blightbunny.death");

    public static final RegistryObject<SoundEvent> CRAZY_COW_AMBIENT = register("entity.shimmercow.crazy_ambient");
    public static final RegistryObject<SoundEvent> CRAZY_COW_HURT = register("entity.shimmercow.crazy_hurt");
    public static final RegistryObject<SoundEvent> CRAZY_COW_DEATH = register("entity.shimmercow.crazy_death");


    public static final RegistryObject<SoundEvent> GLOWSTONE_BREAK = register("block.glowstone.break");
    public static final RegistryObject<SoundEvent> GLOWSTONE_PLACE = register("block.glowstone.place");
    public static final RegistryObject<SoundEvent> GLOWSTONE_STEP = register("block.glowstone.step");
    public static final RegistryObject<SoundEvent> GLOWSTONE_FALL = register("block.glowstone.fall");
    public static final RegistryObject<SoundEvent> GLOWSTONE_HIT = register("block.glowstone.hit");

    public static final RegistryObject<SoundEvent> FUNGUS_BOUNCE = register("block.fungus.bounce");

    public static final RegistryObject<SoundEvent> EMBER_BOUNCE_BIG = register("entity.ember.bounce_big");
    public static final RegistryObject<SoundEvent> EMBER_BOUNCE_MED = register("entity.ember.bounce_med");
    public static final RegistryObject<SoundEvent> EMBER_BOUNCE_SMALL = register("entity.ember.bounce_small");

    public static final RegistryObject<SoundEvent> EQUIP_BITTERSWEET_CHARM = register("item.accessory.equip_bittersweet_charm");
    public static final RegistryObject<SoundEvent> EQUIP_ENCHANTED_RING = register("item.accessory.equip_enchanted_ring");
    public static final RegistryObject<SoundEvent> EQUIP_SHROOM_RING = register("item.accessory.equip_shroom_ring");
    public static final RegistryObject<SoundEvent> EQUIP_GRAND_MEDAL = register("item.accessory.equip_grand_medal");
    public static final RegistryObject<SoundEvent> EQUIP_WISDOM_RING = register("item.accessory.equip_wisdom_ring");
    public static final RegistryObject<SoundEvent> EQUIP_SENTRY_RING = register("item.accessory.equip_sentry_ring");
    public static final RegistryObject<SoundEvent> EQUIP_CONSTRUCTION_RING = register("item.accessory.equip_construction_ring");
    public static final RegistryObject<SoundEvent> EQUIP_VAMPIRE_AMULET = register("item.accessory.equip_vampire_amulet");

    public static final RegistryObject<SoundEvent> SUBZERO_CROSSBOW_LOADING_END = register("item.subzero_crossbow.loading_end");
    public static final RegistryObject<SoundEvent> SUBZERO_CROSSBOW_LOADING_MIDDLE = register("item.subzero_crossbow.loading_middle");
    public static final RegistryObject<SoundEvent> SUBZERO_CROSSBOW_LOADING_START = register("item.subzero_crossbow.loading_start");
    public static final RegistryObject<SoundEvent> SUBZERO_CROSSBOW_QUICK_CHARGE_1 = register("item.subzero_crossbow.quick_charge_1");
    public static final RegistryObject<SoundEvent> SUBZERO_CROSSBOW_QUICK_CHARGE_2 = register("item.subzero_crossbow.quick_charge_2");
    public static final RegistryObject<SoundEvent> SUBZERO_CROSSBOW_QUICK_CHARGE_3 = register("item.subzero_crossbow.quick_charge_3");
    public static final RegistryObject<SoundEvent> SUBZERO_CROSSBOW_SHOOT = register("item.subzero_crossbow.shoot");
    public static final RegistryObject<SoundEvent> SUBZERO_CROSSBOW_HIT = register("item.subzero_crossbow.hit");

    public static final RegistryObject<SoundEvent> CONVERT_AMBROSIUM = register("item.convert.ambrosium");
    public static final RegistryObject<SoundEvent> CONVERT_SWET_BALL = register("item.convert.swet_ball");
    public static final RegistryObject<SoundEvent> CONVERT_BLIGHTED_SPORES = register("item.convert.blighted_spores");

    public static final RegistryObject<SoundEvent> INFUSE_ITEM = register("item.sfx.infuse");
    public static final RegistryObject<SoundEvent> INFUSION_EXPIRE = register("item.sfx.infusion_expire");

    public static final RegistryObject<SoundEvent> SPEAR_THROW = register("item.spear.throw");
    public static final RegistryObject<SoundEvent> SPEAR_LAND = register("item.spear.land");

    public static final RegistryObject<SoundEvent> BOOST_JUMP = register("generic.boost_jump");
    public static final RegistryObject<SoundEvent> FIREBALL_SHOOT = register("generic.fireball_shoot");
    public static final RegistryObject<SoundEvent> HEARTBEAT_FAST = register("generic.heartbeat_fast");
    public static final RegistryObject<SoundEvent> HEARTBEAT_MED = register("generic.heartbeat_med");
    public static final RegistryObject<SoundEvent> HEARTBEAT_SLOW = register("generic.heartbeat_slow");

    public static final RegistryObject<SoundEvent> BLIGHTSHADE_SPRAY = register("block.blightshade_spray");
    public static final RegistryObject<SoundEvent> QUICKROOTS_PICK = register("block.quickroots_pick");
    public static final RegistryObject<SoundEvent> LIGHTROOTS_PICK = register("block.lightroots_pick");

    public static final RegistryObject<SoundEvent> GOLD_AERCLOUD_WHOOSH = register("block.aercloud.gold_aercloud_whoosh");
    public static final RegistryObject<SoundEvent> PURPLE_AERCLOUD_ZOOM = register("block.aercloud.purple_aercloud_zoom");
    public static final RegistryObject<SoundEvent> GREEN_AERCLOUD_WUBBLE = register("block.aercloud.green_aercloud_wubble");


    public static final RegistryObject<SoundEvent> MUSIC_DISC_SENTIENCE = register("item.music_disc.sentience");

    public static final RegistryObject<SoundEvent> ADVANCEMENT_GOLD = register("ui.toast.aether_redux_gold");

    public static final RegistryObject<SoundEvent> REDUX_MENU = register("music.redux_menu");

    private static RegistryObject<SoundEvent> register(String name) {
        return SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(Redux.locate(name)));
    }
}
