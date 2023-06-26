package org.pipeman.mcserverdownloader.util;

import java.util.Arrays;
import java.util.List;

public class Utils {
    public static <T> List<T> listOf(T... values) {
        return Arrays.asList(values);
    }
}
