package net.zepalesque.redux.block.state;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.zepalesque.redux.block.state.enums.GrassSize;
import net.zepalesque.redux.block.state.enums.LogicatorMode;

import javax.annotation.Nullable;
import java.util.function.UnaryOperator;

public class ReduxStates {
    public static final BooleanProperty ENCHANTED = BooleanProperty.create("enchanted");
    public static final EnumProperty<GrassSize> GRASS_SIZE = EnumProperty.create("grass_size", GrassSize.class);

    public static final IntegerProperty LEAF_LAYERS = IntegerProperty.create("layers", 1, 16);

    public static final BooleanProperty NATURAL_GEN = BooleanProperty.create("natural_gen");

    public static final BooleanProperty LEFT_INPUT = BooleanProperty.create("left_input");
    public static final BooleanProperty RIGHT_INPUT = BooleanProperty.create("right_input");



    public static final EnumProperty<LogicatorMode> MODE_LOGICATOR = EnumProperty.create("mode", LogicatorMode.class);

    // TODO: Move to Zenith
    public static <P extends Comparable<P>> BlockState mapValue(BlockState state, Property<P> property, UnaryOperator<P> operation) {
        P original = state.getValue(property);
        P mapped = operation.apply(original);
        return state.setValue(property, mapped);
    }

    // TODO: Move to Zenith
    public static @Nullable <P extends Comparable<P>> BlockState setIfDifferent(BlockState state, Property<P> property, UnaryOperator<P> operation) {
        P original = state.getValue(property);
        P mapped = operation.apply(original);
        return state.setValue(property, mapped);
    }
}
