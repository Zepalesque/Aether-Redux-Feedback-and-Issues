package net.zepalesque.redux;

import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.function.Function;

// TODO: Expand, add methods for primitives, move to Zenith
public class ArrayUtil {

    public static <T> T[] generateContents(T[] array, Function<Integer, ? extends T> factory) {
        for (int index = 0; index < array.length; index++) {
            array[index] = factory.apply(index);
        }
        return array;
    }

    public static <T, O extends T> T[] copyFrom(T[] array, O[] toCopy, @Nullable T padding) {
        boolean shouldPad = array.length > toCopy.length;
        int split = shouldPad ? toCopy.length : array.length;
        System.arraycopy(toCopy, 0, array, 0, split);
        if (shouldPad) {
            Arrays.fill(array, split, array.length, padding);
        }
        return array;
    }

    // Uses Fisher-Yates algorithm
    public static <T> void shuffle(T[] array, RandomSource rand) {
        for (int i = array.length - 1; i > 0; i--) {
            int index = rand.nextInt(i + 1);
            T element = array[i];

            array[i] = array[index];
            array[index] = element;
        }
    }
}
