package hu.palkonyves.ws.rest.dto;

public class ElapsedTime extends BaseDto {
    
    public static String TIME_UNIT_MS = "ms";
    public static String TIME_UNIT_NS = "ns";
    public static String TIME_UNIT_SEC = "sec";

    private Long elapsedTime;
    private String timeUnit;
    

    public Long getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(Long elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public String getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(String timeUnit) {
        this.timeUnit = timeUnit;
    }
}
