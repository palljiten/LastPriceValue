package com.last_value.service.service;

import com.last_value.service.model.PriceRecord;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryLastValueService implements LastValueService {

    private final Map<String, PriceRecord> committed = new ConcurrentHashMap<>();
    private final Map<UUID, BatchSession> batches = new ConcurrentHashMap<>();

    @Override
    public UUID startBatch() {
        return null;
    }

    @Override
    public void uploadChunks(UUID batchId, List<PriceRecord> records) {

    }

    @Override
    public void completeBatch(UUID batchId) {

    }

    @Override
    public void cancelBatch(UUID batchId) {

    }

    @Override
    public Optional<PriceRecord> getLastPrice(String id) {
        return Optional.empty();
    }
}
