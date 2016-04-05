package net.stomdesignsoftware.rewards.impl.reward;

import com.google.common.base.Objects;
import net.stomdesignsoftware.rewards.Rewards;
import net.stomdesignsoftware.rewards.api.Reward;
import ninja.leaping.configurate.ConfigurationNode;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextParseException;
import org.spongepowered.api.text.serializer.TextSerializer;
import org.spongepowered.api.text.serializer.TextSerializers;

public class MessageReward implements Reward {

    protected Text message;

    @Override public boolean init(ConfigurationNode node) {
        boolean subNode = false;

        if(!node.getNode("message").isVirtual()) {
            subNode = true;
        }

        String temp;

        if(subNode) {
            temp = node.getNode("message").getString();
        } else {
            temp = node.getString();
        }

        if(temp == null)
            return false;

        TextSerializer serializer = TextSerializers.TEXT_XML;

        if(node.getNode("type").isVirtual()) {
            subNode = false;
        }

        String type;

        if(subNode) {
            type = node.getNode("type").getString();

            if(type == null)
                return false;

            try {
                serializer = TextType.valueOf(type).getSerializer();
            } catch (IllegalArgumentException e) {
                Rewards.logger().warn("Invalid text type {}. Valid types are {}", type, TextType.values());
                return false;
            }
        }

        try {
            this.message = serializer.deserialize(temp);
        } catch (TextParseException e) {
            Rewards.logger().error("Unable to parse to text:\n{}", temp, e);
            return false;
        }

        return true;
    }

    @Override public boolean reward(Player player) {
        player.sendMessage(message);
        return true;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).add("Message", message.toPlain()).toString();
    }

    private enum TextType {
        XML(TextSerializers.TEXT_XML),
        JSON(TextSerializers.JSON),
        CODES(TextSerializers.FORMATTING_CODE),
        PLAIN(TextSerializers.PLAIN);

        private TextSerializer serializer;

        TextType(TextSerializer serializer) {
            this.serializer = serializer;
        }

        public TextSerializer getSerializer() {
            return serializer;
        }
    }
}
