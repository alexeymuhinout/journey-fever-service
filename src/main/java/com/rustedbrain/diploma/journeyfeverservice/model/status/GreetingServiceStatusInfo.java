package com.rustedbrain.diploma.journeyfeverservice.model.status;

public class GreetingServiceStatusInfo extends ServiceStatusInfo {

    private final String greeting;

    public GreetingServiceStatusInfo(long id, Status status, long timeMillis, String greeting) {
        super(id, status, timeMillis);
        this.greeting = greeting;
    }

    public String getGreeting() {
        return greeting;
    }

    @Override
    public String toString() {
        return "GreetingServiceStatusInfo{" +
                "greeting='" + greeting + '\'' +
                "} " + super.toString();
    }
}
