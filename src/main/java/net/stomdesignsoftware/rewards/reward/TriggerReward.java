package net.stomdesignsoftware.rewards.reward;

import net.stomdesignsoftware.rewards.api.Reward;

import java.util.LinkedList;
import java.util.List;

public class TriggerReward {

    private List<String> triggers;
    private List<Reward> rewards;

    public TriggerReward() {
        this.triggers = new LinkedList<>();
        this.rewards = new LinkedList<>();
    }

}
