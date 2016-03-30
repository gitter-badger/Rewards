package net.stomdesignsoftware.rewards.event;

import net.stomdesignsoftware.rewards.api.Trigger;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.cause.Cause;

public class RewardTriggerEvent implements Event {

    private Trigger trigger;

    public RewardTriggerEvent(Trigger trigger) {
        this.trigger = trigger;
    }

    @Override public Cause getCause() {
        return Cause.source(trigger).build();
    }

    public Trigger getTrigger() {
        return trigger;
    }
}
