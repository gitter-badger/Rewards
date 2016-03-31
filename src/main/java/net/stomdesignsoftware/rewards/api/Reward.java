package net.stomdesignsoftware.rewards.api;

import ninja.leaping.configurate.ConfigurationNode;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

public interface Reward {

    boolean init(ConfigurationNode node);

    boolean reward(Player player);
}
