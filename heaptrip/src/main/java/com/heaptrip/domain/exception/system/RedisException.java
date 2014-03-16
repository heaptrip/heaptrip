package com.heaptrip.domain.exception.system;

import com.heaptrip.domain.entity.journal.ModuleEnum;
import com.heaptrip.domain.exception.Journalable;
import com.heaptrip.domain.exception.Mailable;
import com.heaptrip.domain.exception.SystemException;

public class RedisException extends SystemException implements Journalable, Mailable {

    @Override
    public ModuleEnum getModule() {
        return ModuleEnum.REDIS;
    }
}
