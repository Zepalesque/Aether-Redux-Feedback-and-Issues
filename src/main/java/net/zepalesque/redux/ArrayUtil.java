package net.zepalesque.redux;

import java.util.function.Function;

public class ArrayUtil {

    public static <T> T[] generateContents(T[] array, Function<Integer, T> factory) {
        for (int index = 0; index < array.length; index++) {
            array[index] = factory.apply(index);
        }
        return array;
    }
}
