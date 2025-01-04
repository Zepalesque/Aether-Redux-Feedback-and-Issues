package net.zepalesque.redux.block.state;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.zepalesque.redux.block.state.enums.GrassSize;
import net.zepalesque.redux.block.state.enums.LogicatorMode;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.function.UnaryOperator;

public class ReduxStates {

    public static final BooleanProperty NATURAL_GEN = BooleanProperty.create("natural_gen");

    public static final BooleanProperty LEFT_INPUT = BooleanProperty.create("left_input");
    public static final BooleanProperty RIGHT_INPUT = BooleanProperty.create("right_input");

    public static final EnumProperty<LogicatorMode> MODE_LOGICATOR = EnumProperty.create("mode", LogicatorMode.class);
}
