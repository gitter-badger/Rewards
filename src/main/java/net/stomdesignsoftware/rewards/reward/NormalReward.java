package net.stomdesignsoftware.rewards.reward;

import net.stomdesignsoftware.rewards.api.Reward;
import net.stomdesignsoftware.rewards.api.Test;

import java.util.List;

public class NormalReward {

    private List<Test> tests;
    private List<Reward> rewards;

    public NormalReward(List<Test> tests, List<Reward> rewards) {
        this.tests = tests;
        this.rewards = rewards;
    }


}
