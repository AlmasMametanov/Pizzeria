package com.epam.pizzeria.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Order implements Serializable {
    private Long id;
    private Long userId;
    private Long statusId;
    private Integer totalPrice;
    private Timestamp dateStart;
    private String readyIn;
    private Long deliveryMethodId;
    private List<OrderDetail> orderDetailList;
    private StatusLocale statusLocale;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Timestamp getDateStart() {
        return dateStart;
    }

    public void setDateStart(Timestamp dateStart) {
        this.dateStart = dateStart;
    }

    public String getReadyIn() {
        return readyIn;
    }

    public void setReadyIn(String readyIn) {
        this.readyIn = readyIn;
    }

    public Long getDeliveryMethodId() {
        return deliveryMethodId;
    }

    public void setDeliveryMethodId(Long deliveryMethodId) {
        this.deliveryMethodId = deliveryMethodId;
    }

    public List<OrderDetail> getOrderDetailList() {
        return orderDetailList;
    }

    public void setOrderDetailList(List<OrderDetail> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }

    public StatusLocale getStatusLocale() {
        return statusLocale;
    }

    public void setStatusLocale(StatusLocale statusLocale) {
        this.statusLocale = statusLocale;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) &&
                Objects.equals(userId, order.userId) &&
                Objects.equals(statusId, order.statusId) &&
                Objects.equals(totalPrice, order.totalPrice) &&
                Objects.equals(dateStart, order.dateStart) &&
                Objects.equals(readyIn, order.readyIn) &&
                Objects.equals(deliveryMethodId, order.deliveryMethodId) &&
                Objects.equals(orderDetailList, order.orderDetailList) &&
                Objects.equals(statusLocale, order.statusLocale);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, statusId, totalPrice, dateStart, readyIn, deliveryMethodId, orderDetailList, statusLocale);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", userId=" + userId +
                ", statusId=" + statusId +
                ", totalPrice=" + totalPrice +
                ", dateStart=" + dateStart +
                ", readyIn='" + readyIn + '\'' +
                ", deliveryMethodId=" + deliveryMethodId +
                ", orderDetailList=" + orderDetailList +
                ", statusLocale=" + statusLocale +
                '}';
    }
}
