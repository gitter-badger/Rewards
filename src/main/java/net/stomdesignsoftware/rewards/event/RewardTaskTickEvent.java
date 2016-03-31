package net.stomdesignsoftware.rewards.event;

import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.cause.Cause;

/**
 * @author Nick Peterson
 */
public class RewardTaskTickEvent implements Event {

    private long interval;
    private String taskName;

    public RewardTaskTickEvent(String taskName, long interval) {
        this.taskName = taskName;
        this.interval = interval;
    }

    public String getTaskName() {
        return taskName;
    }

    public long getInterval() {
        return interval;
    }

    @Override
    public Cause getCause() {
        return Cause.source(taskName).build();
    }
}
