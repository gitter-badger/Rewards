package net.stomdesignsoftware.rewards.impl.reward;

import com.google.common.base.Objects;
import net.stomdesignsoftware.rewards.Rewards;
import net.stomdesignsoftware.rewards.api.Reward;
import ninja.leaping.configurate.ConfigurationNode;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.entity.living.player.Player;

public class CommandReward implements Reward {

    private String command;
    private String args;

    @Override
    public boolean init(ConfigurationNode node) {
        String temp = node.getString();

        if(temp == null)
            return false;

        //Get rid of '/' prefix if any
        if (temp.charAt(0) == '/')
            temp = temp.substring(1);

        this.command = temp.split(" ")[0];
        //+ 1 get rids of the space between the command and the arguments
        this.args = temp.substring(command.length() + 1);

        if (!Sponge.getCommandManager().get(command).isPresent()) {
            Rewards.logger().warn("Command {} doesn't exist", command);
            return false;
        }

        return true;
    }

    @Override
    public boolean reward(Player player) {
        try {
            Sponge.getCommandManager().get(command).get().getCallable()
                .process(Sponge.getServer().getConsole(), this.formatCommand(player));
            return true;
        } catch (CommandException e) {
            Rewards.logger()
                .warn("Something went wrong when trying to execute {} with these arguments {}", command, args);
        }

        return false;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("Command", command).add("Args", args).toString();
    }

    private String formatCommand(Player player) {
        return args.replace("@p", player.getName());
    }
}
