package com.entity;

import com.entity.type.Type;

public class OrderBookRow {
    private Long price;
    private Integer size;
    private Type type;

    public OrderBookRow(Long price, Integer size, Type type) {
        this.price = price;
        this.size = size;
        this.type = type;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "OrderBookRow{" +
                "price=" + price +
                ", size=" + size +
                ", type=" + type +
                '}';
    }
}
