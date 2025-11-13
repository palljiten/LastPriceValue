package com.last_value.service.service;

import com.last_value.service.model.PriceRecord;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LastValueService {

    UUID startBatch();
    void uploadChunks(UUID batchId, List<PriceRecord> records);
    void completeBatch(UUID batchId);
    void cancelBatch(UUID batchId);
    Optional<PriceRecord> getLastPrice(String id);
}
