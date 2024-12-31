package net.zepalesque.redux.attachment;

import com.aetherteam.nitrogen.attachment.INBTSynchable;
import com.aetherteam.nitrogen.network.packet.SyncPacket;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.zepalesque.redux.client.audio.ReduxSounds;
import net.zepalesque.redux.client.particle.ReduxParticles;
import net.zepalesque.redux.item.ReduxItems;
import net.zepalesque.redux.network.packet.ReduxPlayerSyncPacket;
import net.zepalesque.zenith.api.serialization.codec.type.UnboundedHashMapCodec;
import org.apache.commons.lang3.tuple.Triple;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ReduxPlayerAttachment implements INBTSynchable {

    // TODO: Investigate, do any of these values actually NEED to be synchronized?
    private final Map<String, Triple<Type, Consumer<Object>, Supplier<Object>>> synchableFunctions = Map.ofEntries(
            Map.entry("max_aerjumps", Triple.of(Type.INT, (object) -> this.setMaxAerjumps((int) object), this::getMaxAerjumps)),
            Map.entry("base_aerjumps", Triple.of(Type.INT, (object) -> this.setBaseAerjumps((int) object), this::getBaseAerjumps)),
            Map.entry("performed_aerjumps", Triple.of(Type.INT, (object) -> this.setPerformedAerjumps((int) object), this::getPerformedAerjumps))
            );


    // TODO: Flesh out into bigger system in Zenith? Add registerable operators?
    private final Map<ResourceLocation, Integer> aerjumpCountModifiers;

    private int performedAerjumps = 0, baseAerjumps = 0, airTime = 0, prevPerformedAerjumps = 0;

    // cached value, updated when modifiers are added/removed
    private int maxAerjumps = 0;


    public ReduxPlayerAttachment() {
        this.aerjumpCountModifiers = new HashMap<>();
    }

    public static final Codec<ReduxPlayerAttachment> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            UnboundedHashMapCodec.of(ResourceLocation.CODEC, Codec.INT).fieldOf("aerjump_count_modifiers").forGetter(ReduxPlayerAttachment::getAerjumpCountModifiers),
            Codec.INT.fieldOf("base_aerjumps").forGetter(ReduxPlayerAttachment::getBaseAerjumps),
            Codec.INT.fieldOf("performed_aerjumps").forGetter(ReduxPlayerAttachment::getPerformedAerjumps),
            Codec.INT.fieldOf("performed_aerjumps_last_tick").forGetter(ReduxPlayerAttachment::getPrevPerformedAerjumps),
            Codec.intRange(0, 3).fieldOf("aerjump_air_time").forGetter(ReduxPlayerAttachment::getAirTime)
    ).apply(instance, ReduxPlayerAttachment::new));

    private ReduxPlayerAttachment(Map<ResourceLocation, Integer> aerjumpCountModifiers, int baseAerjumps, int performedAerjumps, int prevPerformedAerjumps, int airTime) {
        this.aerjumpCountModifiers = aerjumpCountModifiers;
        this.baseAerjumps = baseAerjumps;
        this.performedAerjumps = performedAerjumps;
        this.prevPerformedAerjumps = prevPerformedAerjumps;
        this.airTime = airTime;
        this.maxAerjumps = aerjumpCountModifiers.isEmpty() ? baseAerjumps : this.calcAerjumpMax();
    }

    protected int calcAerjumpMax() {
        int calc = baseAerjumps;
        for (int i : aerjumpCountModifiers.values()) {
            calc += i;
        }
        return calc;
    }

    public void addAerjumpModifier(Player player, ResourceLocation name, int addend) {
        boolean hadModifier = this.aerjumpCountModifiers.containsKey(name);
        this.aerjumpCountModifiers.putIfAbsent(name, addend);
        if (!hadModifier) {
            int i = calcAerjumpMax();
            if (this.maxAerjumps != i) {
                this.setSynched(player.getId(), Direction.CLIENT, "max_aerjumps", i);
            }
        }
    }

    public void removeAerjumpModifier(Player player, ResourceLocation name) {
        boolean hadModifier = this.aerjumpCountModifiers.containsKey(name);
        this.aerjumpCountModifiers.remove(name);
        if (hadModifier) {
            int i = calcAerjumpMax();
            if (this.maxAerjumps != i) {
                this.setSynched(player.getId(), Direction.CLIENT, "max_aerjumps", i);
            }
        }
    }

    public int getMaxAerjumps() {
        return this.maxAerjumps;
    }

    public int getBaseAerjumps() {
        return this.baseAerjumps;
    }

    public int getPerformedAerjumps() {
        return this.performedAerjumps;
    }

    public int getPrevPerformedAerjumps() {
        return prevPerformedAerjumps;
    }

    private void setMaxAerjumps(int maxAerjumps) {
        this.maxAerjumps = maxAerjumps;
    }

    private void setBaseAerjumps(int baseAerjumps) {
        this.baseAerjumps = baseAerjumps;
    }

    private void setPerformedAerjumps(int performedAerjumps) {
        this.performedAerjumps = performedAerjumps;
    }

    public int getAirTime() {
        return airTime;
    }

    public void onUpdate(Player player) {
        this.tickAirTime(player);
        this.tickAerjumps(player);
    }

    private Map<ResourceLocation, Integer> getAerjumpCountModifiers() {
        return aerjumpCountModifiers;
    }

    private void tickAirTime(Player player) {
        if (player.onGround()) {
            airTime = 0;
        } else {
            if (airTime < 3) airTime++;
        }
    }

    private void tickAerjumps(Player player) {
        this.prevPerformedAerjumps = this.getPerformedAerjumps();
        if (player.onGround()) {
            this.setSynched(player.getId(), Direction.CLIENT, "performed_aerjumps", 0);
        }
    }

    private boolean aerjumpsOnCooldown(Player player) {
        return player.getCooldowns().isOnCooldown(ReduxItems.AERBOUND_CAPE.get());
    }

    private void setAerjumpCooldown(Player player, int ticks) {
        player.getCooldowns().addCooldown(ReduxItems.AERBOUND_CAPE.get(), ticks);
    }

    public boolean canAerjump(Player player) {
        return !this.aerjumpsOnCooldown(player) && this.getPerformedAerjumps() < this.getMaxAerjumps() && !player.isInWater() && this.getAirTime() >= 3 && !player.mayFly() && !player.isSpectator() && !player.isPassenger();
    }

    public void prepareAerjump(Player player) {
        this.setSynched(player.getId(), Direction.CLIENT, "performed_aerjumps", this.getPerformedAerjumps() + 1);
        this.setAerjumpCooldown(player, 4);
    }

    public boolean tryAerjump(Player player, int jumpIndex) {
        if (canAerjump(player)) {
            prepareAerjump(player);
            doAerjumpMovement(player, jumpIndex);
            return true;
        }
        return false;
    }


    public void doAerjumpMovement(Player player, int jumpIndex) {
        double dx = player.getDeltaMovement().x() * 1.4D;
        double dy = 0.35D + (jumpIndex == 0 ? 0.1D : 0.5D);
        double dz = player.getDeltaMovement().z() * 1.4D;
        player.setDeltaMovement(dx, dy, dz);
        player.level().playSound(null, player.getX(), player.getY(), player.getZ(), ReduxSounds.AERJUMP.get(), SoundSource.PLAYERS, 0.4f,
                0.9F + player.level().random.nextFloat() * 0.2F);
    }


    public static void spawnAerjumpParticles(Level level, double x, double y, double z) {
        if (level != null) {
            RandomSource random = level.getRandom();
            double radius = 1.25D;
            double height = 0.3D;
            for (int i = 0; i < 12; i++) {
                double x2 = x + (random.nextDouble() * radius) - (radius * 0.5D);
                double y2 = y + (random.nextDouble() * height);
                double z2 = z + (random.nextDouble() * radius) - (radius * 0.5D);
                level.addParticle(ReduxParticles.SHINY_CLOUD, x2, y2, z2, 0.0D, random.nextDouble() * -0.1D, 0.0D);
            }
        }
    }

    @Override
    public Map<String, Triple<Type, Consumer<Object>, Supplier<Object>>> getSynchableFunctions() {
        return synchableFunctions;
    }

    @Override
    public SyncPacket getSyncPacket(int playerID, String key, Type type, Object value) {
        return new ReduxPlayerSyncPacket(playerID, key, type, value);
    }

    public static @NotNull ReduxPlayerAttachment get(@NotNull Player player) {
        return player.getData(ReduxDataAttachments.REDUX_PLAYER.get());
    }


}
