package com.last_value.service.service;

import com.last_value.service.model.PriceRecord;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BatchSession {

    private final Map<String, PriceRecord> records = new ConcurrentHashMap<>();

    void addRecord(List<PriceRecord> batch) {

        batch.forEach(r->records.merge(r.id(),r,(old,newer)->
            newer.asOF().isAfter(old.asOF()) ? newer : old
        ));
    }

    Map<String, PriceRecord> getRecords() {
        return records;
    }
}
