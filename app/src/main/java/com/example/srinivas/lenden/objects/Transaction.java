package com.example.srinivas.lenden.objects;

/**
 * Created by srinivas on 3/3/2016.
 */
public class Transaction {
    public static enum STATUS {
        SUCCESS(1), FAILURE(0), PENDING(-1);

        private int value;

        private STATUS(int value) {
            this.value = value;
        }
    };

    private Long id;
    private String status;
    private String description;
    private Long sourceId;
    private Long destId;
    private double amount;
    private enum requestType {ASK, PAY};

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public Transaction(Long id, String description, Long sourceId, Long destId,
                       double amount, String status) {
        this.id = id;
        this.description = description;
        this.sourceId = sourceId;
        this.destId = destId;
        this.amount = amount;
        this.status = status;
    }

}
