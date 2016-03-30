package net.stomdesignsoftware.rewards.reward;

import net.stomdesignsoftware.rewards.api.Reward;
import net.stomdesignsoftware.rewards.api.Trigger;

import java.util.List;

public class TriggerReward {

    private List<Trigger> triggers;
    private List<Reward> rewards;

    public TriggerReward(List<Trigger> triggers, List<Reward> rewards) {
        this.triggers = triggers;
        this.rewards = rewards;
    }

}
