package com.last_value.service.controller;

import org.springframework.web.bind.annotation.*;
import com.last_value.service.model.PriceRecord;
import com.last_value.service.service.LastValueService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/prices")
public class PriceController {

    private final LastValueService lastValueService;
    
    public PriceController(LastValueService lastValueService) {
        this.lastValueService = lastValueService;
    }

    @PostMapping("/batch/start")
    public UUID startBatch() {
        return lastValueService.startBatch();
    }

    @PostMapping("/batch/{batchId}/upload")
    public void upload(@PathVariable UUID batchId, @RequestBody List<PriceRecord> records) {
        lastValueService.uploadChunks(batchId, records);
    }

    @PostMapping("/batch/{batchId}/complete")
    public void complete(@PathVariable UUID batchId) {
        lastValueService.completeBatch(batchId);
    }

    @PostMapping("/batch/{batchId}/cancel")
    public void cancel( UUID batchId) {
        lastValueService.cancelBatch(batchId);
    }
}
