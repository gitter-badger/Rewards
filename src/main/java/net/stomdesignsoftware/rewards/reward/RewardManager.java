package net.stomdesignsoftware.rewards.reward;

import com.google.common.base.Objects;
import net.stomdesignsoftware.rewards.Rewards;
import net.stomdesignsoftware.rewards.api.Reward;
import net.stomdesignsoftware.rewards.api.Test;
import net.stomdesignsoftware.rewards.api.Trigger;
import net.stomdesignsoftware.rewards.event.RewardTaskTickEvent;
import ninja.leaping.configurate.ConfigurationNode;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scheduler.Task;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class RewardManager implements Consumer<Task> {

    private Map<String, Class<? extends Reward>> rewardMap;
    private Map<String, Class<? extends Test>> testMap;
    private Map<String, Class<? extends Trigger>> triggerMap;

    private LinkedList<NormalReward> normalRewards;
    private List<TriggerReward> triggerRewards;
    private Task task;

    private boolean loaded = false;

    public RewardManager() {
        this.rewardMap = new HashMap<>();
        this.testMap = new HashMap<>();
        this.triggerMap = new HashMap<>();
        this.normalRewards = new LinkedList<>();
        this.triggerRewards = new LinkedList<>();
    }

    public void submit(Object plugin, long interval) {
        if(loaded)
            task = Sponge.getScheduler().createTaskBuilder().execute(this).interval(interval, TimeUnit.MINUTES).submit(plugin);
        else
            Rewards.logger().warn("Can't start RewardTask because it is not completely loaded or it doesn't have any normal rewards.");
    }

    public void cancel() {
        if(task == null)
            return;

        task.cancel();
    }

    public void loadConfig(ConfigurationNode rootNode) {
        this.normalRewards.clear();
        this.triggerRewards.clear();
        this.loaded = false;

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

                //Create TriggerReward and register it and all Triggers as listeners
                triggers.forEach(trigger -> Sponge.getEventManager().registerListeners(Rewards.instance, trigger));
                TriggerReward triggerReward = new TriggerReward(triggers, rewards);
                Sponge.getEventManager().registerListeners(Rewards.instance, triggerReward);

                triggerRewards.add(triggerReward);
            }
        }
    }

    public LinkedList<NormalReward> getNormalRewards() {
        return normalRewards;
    }

    public List<TriggerReward> getTriggerRewards() {
        return triggerRewards;
    }

    public void registerReward(String name, Class<? extends Reward> rewardClass) {
        if (rewardMap.containsKey(name)) {
            Rewards.logger().error("Name {} is already a Reward", name, new RuntimeException());
        }

        rewardMap.put(name, rewardClass);
        Rewards.debug("Registered {} rewards.", name);
    }

    public void registerTest(String name, Class<? extends Test> testClass) {
        if (testMap.containsKey(name)) {
            Rewards.logger().error("Name {} is already a Reward", name, new RuntimeException());
        }

        testMap.put(name, testClass);
        Rewards.debug("Registered {} test.", name);
    }

    public void registerTrigger(String name, Class<? extends Trigger> triggerClass) {
        if (triggerMap.containsKey(name)) {
            Rewards.logger().error("Name {} is already a Reward", name, new RuntimeException());
        }

        triggerMap.put(name, triggerClass);
        Rewards.debug("Registered {} trigger.", name);
    }

    //NormalRewards task
    @Override public void accept(Task task) {
        Rewards.debug("Running rewards task...");
        //Run event
        Sponge.getEventManager().post(new RewardTaskTickEvent(task.getName(), TimeUnit.MINUTES.convert(
            task.getInterval(), TimeUnit.MILLISECONDS)));

        //Circulate NormalRewards list
        NormalReward reward = normalRewards.pop();
        normalRewards.add(reward);

        Sponge.getServer().getOnlinePlayers().forEach(reward::test);
        Rewards.debug("Rewards task finished.");
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("Normal Rewards", normalRewards).add("Trigger Rewards", triggerRewards).toString();
    }
}
