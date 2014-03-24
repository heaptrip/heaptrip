package com.heaptrip.domain.entity.image;

import com.heaptrip.domain.entity.BaseObject;

/**
 * Simple image
 */
public class SimpleImage extends BaseObject {

    // image name
    // TODO konovalov: add multi language text
    private String name;

    // references to files in GridFS
    private FileReferences refs;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FileReferences getRefs() {
        return refs;
    }

    public void setRefs(FileReferences refs) {
        this.refs = refs;
    }
}
