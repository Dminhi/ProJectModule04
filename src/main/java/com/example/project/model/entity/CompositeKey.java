package com.example.project.model.entity;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CompositeKey implements Serializable {
    private Long orderID;
    private Long productID;

    // Constructor không đối số
    public CompositeKey() {}

    // Constructor với các khóa ngoại
    public CompositeKey(Long orderId, Long productId) {
        this.orderID = orderId;
        this.productID = productId;
    }

    // Getters và Setters

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompositeKey that = (CompositeKey) o;
        return Objects.equals(orderID, that.orderID) &&
                Objects.equals(productID, that.productID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderID, productID);
    }

}
