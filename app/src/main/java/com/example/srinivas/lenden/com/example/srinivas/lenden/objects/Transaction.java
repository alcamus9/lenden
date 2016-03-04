package com.example.srinivas.lenden.com.example.srinivas.lenden.objects;

/**
 * Created by srinivas on 3/3/2016.
 */
public class Transaction {

    private Long id;
    private enum status {SUCCESS, FAILURE, PENDING};
    private String description;
    private Long sourceId;
    private Long destId;
    private float amount;
    private enum requestType {ASK, PAY};

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public Long getDestId() {
        return destId;
    }

    public void setDestId(Long destId) {
        this.destId = destId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }
}
