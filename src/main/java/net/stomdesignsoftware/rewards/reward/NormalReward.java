package net.stomdesignsoftware.rewards.reward;

import com.google.common.base.Objects;
import net.stomdesignsoftware.rewards.Rewards;
import net.stomdesignsoftware.rewards.api.Reward;
import net.stomdesignsoftware.rewards.api.Test;
import org.spongepowered.api.entity.living.player.Player;

import java.util.List;

public class NormalReward {

    private String name;
    private List<Test> tests;
    private List<Reward> rewards;

    public NormalReward(String name, List<Test> tests, List<Reward> rewards) {
        this.name = name;
        this.tests = tests;
        this.rewards = rewards;
    }

    public void test(Player player) {
        boolean pass = true;

        for (Test test : tests) {
            if (!test.testPlayer(player)) {
                pass = false;
            }
        }

        if (pass) {
            rewards.forEach(reward -> reward.reward(player));
            Rewards.debug("Player: {} Passed Reward: {}", player.getName(), this.name);
        }
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("Name", name).add("Tests", tests).add("Rewards", rewards).toString();
    }
}
