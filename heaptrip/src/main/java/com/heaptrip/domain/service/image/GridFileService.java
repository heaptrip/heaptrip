package com.heaptrip.domain.service.image;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

/**
 * Service to work with GridFS
 */
public interface GridFileService {

    /**
     * Save file to GridFS
     *
     * @param fileName file name
     * @param is       input stream
     * @return file id
     */
    public String saveFile(String fileName, InputStream is) throws IOException;

    /**
     * Get file
     *
     * @param fileId file id
     * @return input stream
     */
    public InputStream getFile(String fileId);

    /**
     * Remove file from GridFS
     *
     * @param fileId file id
     */
    public void removeFile(String fileId);

    /**
     * Remove list of files from GridFS
     *
     * @param fileIds collection of file id
     */
    public void removeFiles(Collection fileIds);
}
