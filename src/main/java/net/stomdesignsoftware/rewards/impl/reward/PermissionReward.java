package net.stomdesignsoftware.rewards.impl.reward;

import net.stomdesignsoftware.rewards.Rewards;
import net.stomdesignsoftware.rewards.api.Reward;
import ninja.leaping.configurate.ConfigurationNode;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.permission.SubjectData;
import org.spongepowered.api.util.Tristate;

public class PermissionReward implements Reward {

    private String permission;
    private boolean value;

    @Override
    public boolean init(ConfigurationNode node) {
        if (node.getNode("permission").isVirtual())
            return false;

        Object value = node.getNode("permission").getValue();
        if (!(value instanceof String))
            return false;

        permission = (String) value;

        if (node.getNode("value").isVirtual()) {
            this.value = true;
            return true;
        }

        value = node.getNode("value").getValue();
        if (!(value instanceof Boolean))
            return false;

        this.value = (Boolean) value;
        return true;
    }

    @Override
    public boolean reward(Player player) {
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
}
