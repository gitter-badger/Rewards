package net.stomdesignsoftware.rewards.reward;

import com.google.common.base.Objects;
import net.stomdesignsoftware.rewards.Rewards;
import net.stomdesignsoftware.rewards.api.Reward;
import net.stomdesignsoftware.rewards.api.Trigger;
import net.stomdesignsoftware.rewards.event.RewardTriggerEvent;
import org.spongepowered.api.event.Listener;

import java.util.List;

public class TriggerReward {

    private String name;
    private List<Trigger> triggers;
    private List<Reward> rewards;

    public TriggerReward(String name, List<Trigger> triggers, List<Reward> rewards) {
        this.name = name;
        this.triggers = triggers;
        this.rewards = rewards;
    }

    @Listener public void onTrigger(RewardTriggerEvent event) {
        if(triggers.contains(event.getTrigger())) {
            Rewards.debug("Reward Triggered: {}", name);
            rewards.forEach(reward -> reward.reward(event.getTargetEntity()));
        }
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("Name", name).add("Triggers", triggers).add("Rewards", rewards).toString();
    }
}
