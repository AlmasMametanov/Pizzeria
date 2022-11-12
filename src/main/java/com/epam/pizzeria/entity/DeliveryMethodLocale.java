package com.epam.pizzeria.entity;

import java.util.Objects;

public class DeliveryMethodLocale {
    private Long id;
    private Long localeId;
    private Long deliveryMethodId;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLocaleId() {
        return localeId;
    }

    public void setLocaleId(Long localeId) {
        this.localeId = localeId;
    }

    public Long getDeliveryMethodId() {
        return deliveryMethodId;
    }

    public void setDeliveryMethodId(Long deliveryMethodId) {
        this.deliveryMethodId = deliveryMethodId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeliveryMethodLocale that = (DeliveryMethodLocale) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(localeId, that.localeId) &&
                Objects.equals(deliveryMethodId, that.deliveryMethodId) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, localeId, deliveryMethodId, name);
    }

    @Override
    public String toString() {
        return "DeliveryMethodLocale{" +
                "id=" + id +
                ", localeId=" + localeId +
                ", deliveryMethodId=" + deliveryMethodId +
                ", name='" + name + '\'' +
                '}';
    }
}
