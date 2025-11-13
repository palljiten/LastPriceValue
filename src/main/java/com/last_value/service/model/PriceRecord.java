package com.last_value.service.model;

import com.fasterxml.jackson.databind.JsonNode;

import java.time.Instant;

public record PriceRecord(String id, Instant asOF, JsonNode payload) {
}
