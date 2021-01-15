package com.service;

import com.entity.OrderBookRow;

import java.util.Collection;
import java.util.Map;

public interface QueryService {
    OrderBookRow countMaxBuy(Collection<OrderBookRow> rowList);

    OrderBookRow countMaxAsk(Collection<OrderBookRow> rowList);

    Integer countAmount(Map<Long, OrderBookRow> rowMap, Long price);


}
