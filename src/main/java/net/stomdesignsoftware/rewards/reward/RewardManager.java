package net.stomdesignsoftware.rewards.reward;

import net.stomdesignsoftware.rewards.Rewards;
import net.stomdesignsoftware.rewards.api.Reward;
import net.stomdesignsoftware.rewards.api.Test;
import net.stomdesignsoftware.rewards.api.Trigger;
import ninja.leaping.configurate.ConfigurationNode;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class RewardManager {

    private Map<String, Class<? extends Reward>> rewardMap;
    private Map<String, Class<? extends Test>> testMap;
    private Map<String, Class<? extends Trigger>> triggerMap;

    private List<NormalReward> normalRewards;
    private List<TriggerReward> triggerRewards;

    public RewardManager() {
        this.rewardMap = new HashMap<>();
        this.testMap = new HashMap<>();
        this.triggerMap = new HashMap<>();
        this.normalRewards = new LinkedList<>();
        this.triggerRewards = new LinkedList<>();
    }

    public void loadConfig(ConfigurationNode node) {

    }

    public void registerReward(String name, Class<? extends Reward> rewardClass) {
        if (rewardMap.containsKey(name)) {
            Rewards.logger().error("Name {} is already a Reward", name, new RuntimeException());
        }

        rewardMap.put(name, rewardClass);
    }

    public void registerTest(String name, Class<? extends Test> testClass) {
        if (testMap.containsKey(name)) {
            Rewards.logger().error("Name {} is already a Reward", name, new RuntimeException());
        }

        testMap.put(name, testClass);
    }

    public void registerTrigger(String name, Class<? extends Trigger> triggerClass) {
        if (triggerMap.containsKey(name)) {
            Rewards.logger().error("Name {} is already a Reward", name, new RuntimeException());
        }

        triggerMap.put(name, triggerClass);
    }
}
