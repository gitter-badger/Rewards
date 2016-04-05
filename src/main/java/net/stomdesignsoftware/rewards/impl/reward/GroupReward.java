package net.stomdesignsoftware.rewards.impl.reward;

import com.google.common.base.Objects;
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
    private boolean setGroup;
    private Subject group;

    private static PermissionService permissions() {
        if (permissionService == null)
            permissionService = Sponge.getServiceManager().provide(PermissionService.class)
                    .orElseThrow(() -> new RuntimeException("Couldn't get a permission service"));
        return permissionService;
    }

    @Override
    public boolean init(ConfigurationNode node) {
        boolean subNode = false;

        //Group
        if (!node.getNode("group").isVirtual()) {
            subNode = true;
        }

        Object value;

        if (subNode) {
            value = node.getNode("group").getValue();
        } else {
            value = node.getNode();
        }

        if (!(value instanceof String))
            return false;

        groupName = (String) value;
        if (!permissions().getGroupSubjects().hasRegistered(groupName)) {
            Rewards.logger().warn("Group {} doesn't exist", groupName);
            return false;
        }

        group = permissions().getGroupSubjects().get(groupName);

        //Set Group
        if (node.getNode("setgroup").isVirtual()) {
            subNode = false;
        }

        if (subNode) {
            value = node.getNode("setgroup").getValue();

            if (!(value instanceof Boolean))
                return false;

            setGroup = (Boolean) value;
        } else {
            setGroup = false;
        }

        return true;
    }

    @Override
    public boolean reward(Player player) {
        if (player.getSubjectData().getParents(SubjectData.GLOBAL_CONTEXT).contains(group)) {
            Rewards.debug("{} is already in {} group.", player.getName(), groupName);
            return false;
        }

        if (setGroup) {
            permissions().getGroupSubjects().getAllSubjects().forEach(subject ->
                    player.getSubjectData().removeParent(SubjectData.GLOBAL_CONTEXT, subject));
            player.getSubjectData().addParent(SubjectData.GLOBAL_CONTEXT, group);
            Rewards.debug("{} is now only in {} group.", player.getName(), groupName);
        } else {
            player.getSubjectData().addParent(SubjectData.GLOBAL_CONTEXT, group);
            Rewards.debug("{} is now in {} group.", player.getName(), groupName);
        }

        return true;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("Group", groupName).toString();
    }
}
