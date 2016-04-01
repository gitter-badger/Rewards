package net.stomdesignsoftware.rewards.impl.trigger;

import net.stomdesignsoftware.rewards.Rewards;
import net.stomdesignsoftware.rewards.api.Trigger;
import ninja.leaping.configurate.ConfigurationNode;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.DestructEntityEvent;
import org.spongepowered.api.event.filter.cause.First;

import java.util.Optional;

public class MobKillTrigger implements Trigger {

    private EntityType entityType;

    @Override public boolean init(ConfigurationNode node) {
        Object value = node.getValue();
        if (!(value instanceof String)) {
            return false;
        }

        Optional<EntityType> type = Sponge.getRegistry().getType(EntityType.class, (String) value);
        if(!type.isPresent()) {
            Rewards.logger().warn("Invalid entity type {}. Types: {}", value, Sponge.getRegistry().getAllOf(EntityType.class));
            return false;
        }

        this.entityType = type.get();
        return true;
    }

    @Listener public void onMobKill(DestructEntityEvent.Death event, @First Player player) {
        if (event.getTargetEntity().getType().equals(entityType)) {
            trigger(player);
        }
    }
}
