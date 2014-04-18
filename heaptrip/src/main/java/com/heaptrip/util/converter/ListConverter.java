package com.heaptrip.util.converter;

import java.util.ArrayList;
import java.util.List;

public class ListConverter {

    static public <FROM, TO> List<TO> convertList(List<FROM> fromList, Converter<FROM, TO> converter) {
        ArrayList<TO> result = new ArrayList<>();
        if (fromList != null && !fromList.isEmpty()) {
            for (FROM from : fromList) {
                result.add(converter.convert(from));

            }
        }
        return result;
    }
}