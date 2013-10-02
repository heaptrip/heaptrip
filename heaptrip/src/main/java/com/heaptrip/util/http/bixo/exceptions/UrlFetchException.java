package com.heaptrip.util.http.bixo.exceptions;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import com.heaptrip.util.http.bixo.datum.UrlStatus;


@SuppressWarnings({ "serial" })
public class UrlFetchException extends BaseFetchException {
    
    public UrlFetchException() {
        super();
    }
    
    public UrlFetchException(String url, String msg) {
        super(url, msg);
    }

    @Override
    public UrlStatus mapToUrlStatus() {
        return UrlStatus.ERROR_INVALID_URL;
    }
    
    public void readFields(DataInput input) throws IOException {
        readBaseFields(input);
    }

    public void write(DataOutput output) throws IOException {
        writeBaseFields(output);
    }

    public int compareTo(UrlFetchException e) {
        return compareToBase(e);
    }

}
