package net.stomdesignsoftware.rewards.impl.reward;

import com.google.common.base.Objects;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.chat.ChatTypes;

public class HotbarMessageReward extends MessageReward {

    @Override public boolean reward(Player player) {
        player.sendMessage(ChatTypes.ACTION_BAR, message);
        return true;
    }
}
