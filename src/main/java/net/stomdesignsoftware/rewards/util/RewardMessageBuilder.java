package net.stomdesignsoftware.rewards.util;

import net.stomdesignsoftware.rewards.api.Reward;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

import java.util.Collection;

public class RewardMessageBuilder {

    private Text.Builder rewardText;

    public RewardMessageBuilder() {
        this.rewardText = Text.builder("You have been rewarded with: \n").color(TextColors.GOLD).style(TextStyles.BOLD);
    }

    public RewardMessageBuilder add(Reward reward) {
        rewardText.append(reward.getRewardText()).append(Text.of("\n"));
        return this;
    }

    public RewardMessageBuilder addAll(Reward... rewards) {
        for (Reward reward : rewards) {
            add(reward);
        }
        return this;
    }

    public RewardMessageBuilder addAll(Collection<Reward> rewards) {
        rewards.forEach(this::add);
        return this;
    }

    public Text build() {
        return rewardText.build();
    }
}
