package com.rustedbrain.diploma.journeyfeverservice.model;

public class ServiceStatusInfo {

    private long id;
    private Status status;
    private long timeMillis;

    public ServiceStatusInfo() {
    }

    public ServiceStatusInfo(long id, Status status, long timeMillis) {
        this.id = id;
        this.status = status;
        this.timeMillis = timeMillis;
    }

    public long getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public long getTimeMillis() {
        return timeMillis;
    }

    @Override
    public String toString() {
        return "ServiceStatusInfo{" +
                "id=" + id +
                ", status=" + status +
                ", timeMillis=" + timeMillis +
                '}';
    }
}
