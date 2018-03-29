package com.example.demo.client.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 *
 * @author dpoljak
 * @param <T>
 */
@JsonPropertyOrder({"correlationId", "total", "data"})
public class WrapperDTO<T> {

    private Long total;
    private T data;
    private String correlationId;

    public WrapperDTO() {
        this(null, null);
    }

    public WrapperDTO(T data) {
        this(1L, data);
    }

    public WrapperDTO(Long total, T data) {
        this.total = total;
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long totalCount) {
        this.total = totalCount;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    @Override
    public String toString() {
        return "WrapperDTO{"
                + "total=" + total
                + ", data=" + data
                + ", correlationId='" + correlationId + '\''
                + '}';
    }
}
