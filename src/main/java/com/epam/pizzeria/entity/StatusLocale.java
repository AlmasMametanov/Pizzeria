package com.epam.pizzeria.entity;

import java.io.Serializable;
import java.util.Objects;

public class StatusLocale implements Serializable {
    private Long id;
    private Long statusId;
    private Long localeId;
    private String name;

    public StatusLocale() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public Long getLocaleId() {
        return localeId;
    }

    public void setLocaleId(Long localeId) {
        this.localeId = localeId;
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
        StatusLocale that = (StatusLocale) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(statusId, that.statusId) &&
                Objects.equals(localeId, that.localeId) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, statusId, localeId, name);
    }

    @Override
    public String toString() {
        return "StatusLocale{" +
                "id=" + id +
                ", statusId=" + statusId +
                ", localeId=" + localeId +
                ", name='" + name + '\'' +
                '}';
    }
}
