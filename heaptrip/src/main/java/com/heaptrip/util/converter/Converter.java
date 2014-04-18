package com.heaptrip.util.converter;

public interface Converter<FROM, TO> {
    TO convert(FROM from);
}
