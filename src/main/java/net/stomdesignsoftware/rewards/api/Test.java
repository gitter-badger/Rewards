package net.stomdesignsoftware.rewards.api;

import ninja.leaping.configurate.ConfigurationNode;
import org.spongepowered.api.entity.living.player.Player;

public interface Test {

    boolean init(ConfigurationNode node);

    boolean testPlayer(Player player);
}
