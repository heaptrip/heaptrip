package com.heaptrip.util.converter;

import java.util.ArrayList;
import java.util.List;

public class ListConverter {

    public static <FROM, TO> List<TO> convertList(List<FROM> fromList, Converter<FROM, TO> converter) {
        ArrayList<TO> result = new ArrayList<>();
        if (fromList != null && !fromList.isEmpty()) {
            for (FROM from : fromList) {
                result.add(converter.convert(from));

            }
        }
        return result;
    }

    public static <FROM, TO> TO[] convertList(FROM[] fromList, Converter<FROM, TO> converter) {
        ArrayList<TO> result = new ArrayList<>();
        if (fromList != null && fromList.length > 0) {
            for (FROM from : fromList) {
                result.add(converter.convert(from));
            }
        }
        return ListConverter.toArray(result);
    }


    private static <T> T[] toArray(List<T> fromList) {
        T[] toArray = (T[]) new Object[0];
        if (fromList != null && !fromList.isEmpty()) {
            toArray = (T[]) java.lang.reflect.Array.newInstance(fromList.get(0).getClass(), fromList.size());
            for (int i = 0; i < fromList.size(); i++) {
                toArray[i] = fromList.get(i);
            }
        }
        return toArray;
    }
}