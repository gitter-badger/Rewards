package net.stomdesignsoftware.rewards.impl.reward;

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

        if(node.getNode("message").isVirtual()) {
            subNode = true;
        }

        Object value;

        if(subNode) {
            value = node.getNode("message").getValue();
        } else {
            value = node.getValue();
        }

        if(!(value instanceof String))
            return false;

        TextSerializer serializer = TextSerializers.TEXT_XML;

        if(node.getNode("type").isVirtual()) {
            subNode = false;
        }

        if(subNode) {
            value = node.getNode("type").getValue();

            if(!(value instanceof String))
                return false;
            try {
                serializer = TextType.valueOf((String) value).getSerializer();
            } catch (IllegalArgumentException e) {
                Rewards.logger().warn("Invalid text type {}. Valid types are {}", value, TextType.values());
                return false;
            }
        }

        try {
            this.message = serializer.deserialize((String) value);
        } catch (TextParseException e) {
            Rewards.logger().error("Unable to parse to text:\n{}", value, e);
            return false;
        }

        return true;
    }

    @Override public boolean reward(Player player) {
        player.sendMessage(message);
        return true;
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
