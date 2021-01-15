package com.service;

import com.entity.OrderBookRow;

import java.util.Map;

public interface OperationService {
    void processBuy(Map<Long, OrderBookRow> map, Integer amount);

    void processSell(Map<Long, OrderBookRow> map, Integer amount);
}
