package net.stomdesignsoftware.rewards.api;

import net.stomdesignsoftware.rewards.event.RewardTriggerEvent;
import ninja.leaping.configurate.ConfigurationNode;
import org.spongepowered.api.Sponge;

public interface Trigger {

    boolean init(ConfigurationNode node);

    default void trigger() {
        Sponge.getEventManager().post(new RewardTriggerEvent(this));
    }
}
