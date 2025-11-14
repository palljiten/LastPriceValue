package com.last_value.service.service;

import com.last_value.service.model.PriceRecord;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.springframework.stereotype.Service;

@Service
public class InMemoryLastValueService implements LastValueService {

    private final Map<String, PriceRecord> committed = new ConcurrentHashMap<>();
    private final Map<UUID, BatchSession> batches = new ConcurrentHashMap<>();
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    @Override
    public UUID startBatch() {
        UUID batchId = UUID.randomUUID();
        batches.put(batchId, new BatchSession());
        return batchId;
    }

    @Override
    public void uploadChunks(UUID batchId, List<PriceRecord> records) {
        BatchSession batchSession = getBatchOrThrow(batchId);
        batchSession.addRecord(records);
    }

    @Override
    public void completeBatch(UUID batchId) {
        BatchSession batch = getBatchOrThrow(batchId);
        lock.writeLock().lock();
        try {
            batch.getRecords().forEach((id, record) ->
                committed.merge(id, record, (old, newer) ->
                    newer.asOF().isAfter(old.asOF()) ? newer : old
                )
            );
            batches.remove(batchId);
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public void cancelBatch(UUID batchId) {
        batches.remove(batchId);
    }

    @Override
    public Optional<PriceRecord> getLastPrice(String id) {
        lock.readLock().lock();
        try {
            return Optional.ofNullable(committed.get(id));
        } finally {
            lock.readLock().unlock();
        }

    }

    private BatchSession getBatchOrThrow(UUID batchId) {
        BatchSession batch = batches.get(batchId);
        if (batch == null) {
            throw new IllegalArgumentException("Batch ID not found: " + batchId);
        }
        return batch;
    }
}
