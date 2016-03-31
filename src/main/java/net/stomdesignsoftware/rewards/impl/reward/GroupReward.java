package net.stomdesignsoftware.rewards.impl.reward;

import net.stomdesignsoftware.rewards.Rewards;
import net.stomdesignsoftware.rewards.api.Reward;
import ninja.leaping.configurate.ConfigurationNode;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.permission.PermissionService;
import org.spongepowered.api.service.permission.Subject;
import org.spongepowered.api.service.permission.SubjectData;

public class GroupReward implements Reward {

    private static PermissionService permissionService;
    private String groupName;
    private Subject group;

    private static PermissionService permissions() {
        if (permissionService == null)
            permissionService = Sponge.getServiceManager().provide(PermissionService.class)
                .orElseThrow(() -> new RuntimeException("Couldn't get a permission service"));
        return permissionService;
    }

    @Override
    public boolean init(ConfigurationNode node) {
        Object value = node.getValue();
        if (!(value instanceof String))
            return false;

        groupName = (String) value;
        if (!permissions().getGroupSubjects().hasRegistered(groupName)) {
            Rewards.logger().warn("Group {} doesn't exist", groupName);
            return false;
        }

        group = permissions().getGroupSubjects().get(groupName);
        return true;
    }

    @Override
    public boolean reward(Player player) {
        if (player.getSubjectData().getParents(SubjectData.GLOBAL_CONTEXT).contains(group)) {
            Rewards.debug("{} is already in {} group.", player.getName(), groupName);
            return false;
        }

        player.getSubjectData().addParent(SubjectData.GLOBAL_CONTEXT, group);
        Rewards.debug("{} is now in {} group.", player.getName(), groupName);
        return true;
    }
}
