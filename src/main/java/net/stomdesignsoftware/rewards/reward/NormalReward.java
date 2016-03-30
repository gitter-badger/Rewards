package net.stomdesignsoftware.rewards.reward;

import net.stomdesignsoftware.rewards.api.Reward;

import java.util.LinkedList;
import java.util.List;

public class NormalReward {

    private List<String> tests;
    private List<Reward> rewards;

    public NormalReward() {
        this.tests = new LinkedList<>();
        this.rewards = new LinkedList<>();
    }



}
