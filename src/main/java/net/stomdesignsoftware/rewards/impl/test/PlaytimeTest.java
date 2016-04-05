package net.stomdesignsoftware.rewards.impl.test;

import com.google.common.base.Objects;
import net.stomdesignsoftware.rewards.Rewards;
import net.stomdesignsoftware.rewards.api.Reward;
import net.stomdesignsoftware.rewards.api.Test;
import net.stomdesignsoftware.rewards.event.RewardTaskTickEvent;
import net.stomdesignsoftware.rewards.util.Config;
import ninja.leaping.configurate.ConfigurationNode;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;

public class PlaytimeTest implements Test {

    private static Config playtimeData;
    private long testTime = 0;

    private static Config data() {
        if(playtimeData == null)
            playtimeData = new Config(Rewards.instance.getConfigDir(), "data/playtimedata");
        return playtimeData;
    }

    @Override
    public boolean init(ConfigurationNode node) {
        //Register Events
        if(!EventListener.initialized)
            new EventListener();

        //Get value
        testTime = node.getLong();
        return testTime != 0;
    }

    @Override
    public boolean testPlayer(Player player) {
        long playTime = data().getNode(player.getUniqueId().toString()).getLong();
        return playTime >= testTime;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("Test Time", testTime).toString();
    }

    private final static class EventListener {

        private static boolean initialized = false;

        private EventListener() {
            if(!initialized)
                Sponge.getEventManager().registerListeners(Rewards.instance, this);
        }

        @Listener
        public void onTaskTick(RewardTaskTickEvent event) {
            for(Player player : Sponge.getServer().getOnlinePlayers()) {
                long playTime = data().getNode(player.getUniqueId().toString()).getLong();
                playTime += event.getInterval();
                data().getNode(player.getUniqueId().toString()).setValue(playTime);
            }
        }
    }
}
