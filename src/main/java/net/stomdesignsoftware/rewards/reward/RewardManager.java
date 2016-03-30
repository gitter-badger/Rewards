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

    public void loadConfig(ConfigurationNode rootNode) {
        this.normalRewards.clear();
        this.triggerRewards.clear();

        for (ConfigurationNode node : rootNode.getChildrenMap().values()) {
            if (node.getNode("rewards").isVirtual()) {
                Rewards.logger().warn("Invalid section. Doesn't contain a \"rewards\" section. Section: {}", node.getKey().toString());
                continue;
            }

            if (node.getNode("tests").isVirtual() && node.getNode("triggers").isVirtual()) {
                Rewards.logger()
                        .warn("Invalid section. Doesn't contain a \"tests\" or a \"triggers\" section. Section: {}", node.getKey().toString());
                continue;
            }

            //Parse Rewards
            List<Reward> rewards = new LinkedList<>();

            for (ConfigurationNode oNode : node.getNode("rewards").getChildrenMap().values()) {
                if (!rewardMap.containsKey(oNode.getKey().toString())) {
                    continue;
                }

                try {
                    Reward reward = rewardMap.get(oNode.getKey().toString()).newInstance();
                    if (!reward.init(oNode)) {
                        continue;
                    }
                    rewards.add(reward);
                } catch (InstantiationException e) {
                    Rewards.logger().error("Unable to instantiate a reward.", e);
                } catch (IllegalAccessException e) {
                    Rewards.logger().error("Don't have access to a reward.", e);
                }
            }

            if (rewards.isEmpty()) {
                Rewards.logger().warn("Invalid section. \"rewards\" either has invalid rewards or doesn't contain anything. Section: {}",
                        node.getKey().toString());
                continue;
            }


            if (!node.getNode("tests").isVirtual()) {
                //Parse Tests

                List<Test> tests = new LinkedList<>();

                for (ConfigurationNode oNode : node.getNode("tests").getChildrenMap().values()) {
                    if (!testMap.containsKey(oNode.getKey().toString())) {
                        continue;
                    }

                    try {
                        Test test = testMap.get(oNode.getKey().toString()).newInstance();
                        if (!test.init(oNode)) {
                            continue;
                        }
                        tests.add(test);
                    } catch (InstantiationException e) {
                        Rewards.logger().error("Unable to instantiate a test.", e);
                    } catch (IllegalAccessException e) {
                        Rewards.logger().error("Don't have access to a test.", e);
                    }
                }

                if (tests.isEmpty()) {
                    Rewards.logger().warn("Invalid section. \"tests\" either has invalid tests or doesn't contain anything. Section: {}",
                            node.getKey().toString());
                    continue;
                }

                normalRewards.add(new NormalReward(tests, rewards));

            } else {
                //Parse Triggers

                List<Trigger> triggers = new LinkedList<>();

                for (ConfigurationNode oNode : node.getNode("triggers").getChildrenMap().values()) {
                    if (!testMap.containsKey(oNode.getKey().toString())) {
                        continue;
                    }

                    try {
                        Trigger trigger = triggerMap.get(oNode.getKey().toString()).newInstance();
                        if (!trigger.init(oNode)) {
                            continue;
                        }
                        triggers.add(trigger);
                    } catch (InstantiationException e) {
                        Rewards.logger().error("Unable to instantiate a trigger.", e);
                    } catch (IllegalAccessException e) {
                        Rewards.logger().error("Don't have access to a trigger.", e);
                    }
                }

                if (triggers.isEmpty()) {
                    Rewards.logger().warn("Invalid section. \"triggers\" either has invalid triggers or doesn't contain anything. Section: {}",
                            node.getKey().toString());
                    continue;
                }

                triggerRewards.add(new TriggerReward(triggers, rewards));
            }
        }
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
