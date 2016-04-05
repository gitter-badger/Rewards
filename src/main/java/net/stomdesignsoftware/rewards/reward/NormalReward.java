package net.stomdesignsoftware.rewards.reward;

import com.google.common.base.Objects;
import net.stomdesignsoftware.rewards.api.Reward;
import net.stomdesignsoftware.rewards.api.Test;
import org.spongepowered.api.entity.living.player.Player;

import java.util.List;

public class NormalReward {

    private List<Test> tests;
    private List<Reward> rewards;

    public NormalReward(List<Test> tests, List<Reward> rewards) {
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
        }
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("Tests", tests).add("Rewards", rewards).toString();
    }
}
