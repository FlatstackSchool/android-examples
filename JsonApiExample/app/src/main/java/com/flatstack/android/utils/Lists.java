package com.flatstack.android.utils;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import rx.functions.Func1;

/**
 * Created by adel on 6/7/14
 */
public class Lists {
    @SafeVarargs @NonNull public static <T> List<T> mutableOf(@NonNull T... objects) {
        List<T> list = new ArrayList<>(objects.length);
        Collections.addAll(list, objects);
        return list;
    }

    @SafeVarargs @NonNull public static <T> List<T> add(@NonNull List<T> list,
                                                        @NonNull T... objects) {
        Collections.addAll(list, objects);
        return list;
    }

    @NonNull
    public static String listToString(@NonNull List<String> strings, @NonNull String separator) {
        return listToString(strings, separator, s -> s);
    }

    @NonNull
    public static <T> String listToString(@NonNull List<T> strings, @NonNull String separator,
                                          @NonNull Func1<T, String> converter) {
        String result = "";
        if (strings.size() > 0) {
            for (int i = 0; i < strings.size() - 1; i++) {
                result += converter.call(strings.get(i)) + separator;
            }
            result += converter.call(strings.get(strings.size() - 1));
        }
        return result;
    }

    public static <T> List<T> copyIterator(Iterator<T> iter) {
        List<T> copy = new ArrayList<>();
        while (iter.hasNext())
            copy.add(iter.next());
        return copy;
    }

}