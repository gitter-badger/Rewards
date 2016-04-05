package net.stomdesignsoftware.rewards.impl.reward;

import com.google.common.base.Objects;
import net.stomdesignsoftware.rewards.Rewards;
import net.stomdesignsoftware.rewards.api.Reward;
import ninja.leaping.configurate.ConfigurationNode;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.permission.SubjectData;
import org.spongepowered.api.util.Tristate;

public class PermissionReward implements Reward {

    private String permission;
    private boolean value;

    @Override public boolean init(ConfigurationNode node) {
        boolean subNode = true;

        if (node.getNode("permission").isVirtual()) {
            subNode = false;
        }

        if (subNode) {
            permission = node.getNode("permission").getString();
        } else {
            permission = node.getString();
        }

        if(permission == null)
            return false;

        if(node.getNode("value").isVirtual()) {
            subNode = false;
        }

        if (subNode) {
            value = node.getNode("value").getBoolean();
        } else {
            value = true;
        }

        return true;
    }

    @Override public boolean reward(Player player) {
        if (player.getSubjectData().getPermissions(SubjectData.GLOBAL_CONTEXT).containsKey(permission)) {
            if (player.getSubjectData().getPermissions(SubjectData.GLOBAL_CONTEXT).get(permission) == value) {
                Rewards.debug("{} already has {} permission", player.getName(), permission);
                return false;
            }
        }

        player.getSubjectData().setPermission(SubjectData.GLOBAL_CONTEXT, permission, Tristate.fromBoolean(value));
        Rewards.debug("{} now has {} permission.", player.getName(), permission);
        return true;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("Permission", permission).add("Value", value).toString();
    }
}
