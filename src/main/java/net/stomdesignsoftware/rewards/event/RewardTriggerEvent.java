package net.stomdesignsoftware.rewards.event;

import net.stomdesignsoftware.rewards.api.Trigger;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.entity.living.humanoid.player.TargetPlayerEvent;

public class RewardTriggerEvent implements Event, TargetPlayerEvent {

    private Trigger trigger;
    private Player player;

    public RewardTriggerEvent(Trigger trigger, Player player) {
        this.trigger = trigger;
        this.player = player;
    }

    @Override public Cause getCause() {
        return Cause.source(trigger).build();
    }

    public Trigger getTrigger() {
        return trigger;
    }

    @Override public Player getTargetEntity() {
        return player;
    }
}
