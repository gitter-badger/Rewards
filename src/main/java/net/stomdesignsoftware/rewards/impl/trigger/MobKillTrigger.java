package net.stomdesignsoftware.rewards.impl.trigger;

import com.google.common.base.Objects;
import net.stomdesignsoftware.rewards.Rewards;
import net.stomdesignsoftware.rewards.api.Trigger;
import ninja.leaping.configurate.ConfigurationNode;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.living.Creature;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.DestructEntityEvent;
import org.spongepowered.api.event.filter.cause.First;

import java.util.Optional;

public class MobKillTrigger implements Trigger {

    private EntityType entityType;

    @Override
    public boolean init(ConfigurationNode node) {
        String value = node.getString();

        Optional<EntityType> type = Sponge.getRegistry().getType(EntityType.class, value);
        if (!type.isPresent()) {
            Rewards.logger().warn("Invalid entity type {}. Types: {}", value, Sponge.getRegistry().getAllOf(EntityType.class).stream().filter(entityType ->
                    Creature.class.isAssignableFrom(entityType.getEntityClass())
            ));
            return false;
        }

        this.entityType = type.get();
        return true;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("Entity Type", entityType.getId()).toString();
    }

    @Listener
    public void onMobKill(DestructEntityEvent.Death event, @First Player player) {
        if (entityType.getEntityClass().isAssignableFrom(event.getTargetEntity().getType().getEntityClass())) {
            trigger(player);
        }
    }
}
