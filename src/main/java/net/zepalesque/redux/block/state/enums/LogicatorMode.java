package net.zepalesque.redux.block.state.enums;

import net.minecraft.util.StringRepresentable;

import java.util.function.BinaryOperator;

public enum LogicatorMode implements StringRepresentable {
    AND("and", Boolean::logicalAnd),
    OR("or", Boolean::logicalOr),
    XNOR("xnor", (b1, b2) -> b1 == b2), // XAND
    XOR("xor", Boolean::logicalXor);

    final String name;
    final BinaryOperator<Boolean> operator;
    LogicatorMode(String name, BinaryOperator<Boolean> operator) {
        this.name = name;
        this.operator = operator;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }

    public boolean operate(boolean b1, boolean b2) {
        return this.operator.apply(b1, b2);
    }

    public LogicatorMode flipOperationType() {
        return switch (this) {
            case OR -> AND;
            case AND -> OR;
            case XOR -> XNOR;
            case XNOR -> XOR;
        };
    }
    public LogicatorMode flipExclusivity() {
        return switch (this) {
            case OR -> XOR;
            case AND -> XNOR;
            case XOR -> OR;
            case XNOR -> AND;
        };
    }

    public boolean isExclusive() {
        return this == XOR || this == XNOR;
    }

    public boolean isOr() {
        return this == OR || this == XOR;
    }

    public static LogicatorMode getMode(boolean isExclusive, boolean isOr) {
        return !isExclusive ? !isOr ? AND : OR : !isOr ? XNOR : XOR;
    }
}
