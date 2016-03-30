package net.stomdesignsoftware.rewards.reward;

import net.stomdesignsoftware.rewards.api.Reward;
import net.stomdesignsoftware.rewards.api.Trigger;
import net.stomdesignsoftware.rewards.event.RewardTriggerEvent;
import net.stomdesignsoftware.rewards.util.RewardMessageBuilder;
import org.spongepowered.api.event.Listener;

import java.util.List;

public class TriggerReward {

    private List<Trigger> triggers;
    private List<Reward> rewards;

    public TriggerReward(List<Trigger> triggers, List<Reward> rewards) {
        this.triggers = triggers;
        this.rewards = rewards;
    }

    @Listener public void onTrigger(RewardTriggerEvent event) {
        if(triggers.contains(event.getTrigger())) {
            rewards.forEach(reward -> reward.reward(event.getTargetEntity()));
            event.getTargetEntity().sendMessage(new RewardMessageBuilder().addAll(rewards).build());
        }
    }

}
