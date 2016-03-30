package net.stomdesignsoftware.rewards.api;

import net.stomdesignsoftware.rewards.event.RewardTriggerEvent;
import ninja.leaping.configurate.ConfigurationNode;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;

public interface Trigger {

    boolean init(ConfigurationNode node);

    default void trigger(Player player) {
        Sponge.getEventManager().post(new RewardTriggerEvent(this, player));
    }
}
