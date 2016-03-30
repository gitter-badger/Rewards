package net.stomdesignsoftware.rewards.util;

import net.stomdesignsoftware.rewards.Rewards;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMapper;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

public class Config {

    private ConfigurationLoader<CommentedConfigurationNode> loader;
    private CommentedConfigurationNode root;

    public Config(Path configDir, String name) {
        this(configDir, name, ConfigurationOptions.defaults());
    }

    public Config(Path configDir, String name, ConfigurationOptions options) {
        Path configFile = configDir.resolve(name + ".conf");

        //Make directories if they don't exist.
        if (!configFile.getParent().toFile().exists()) {
            configDir.toFile().mkdirs();
        }

        //Create File if it doesn't exist.
        if (!configFile.toFile().exists()) {
            try {
                configFile.toFile().createNewFile();
            } catch (IOException e) {
                Rewards.logger().error("Couldn't create file {}.", configFile.toString(), e);
            }
        }

        this.loader = HoconConfigurationLoader.builder().setPath(configFile).setDefaultOptions(options).build();

        try {
            this.root = loader.load();
        } catch (IOException e) {
            Rewards.logger().error("Unable to load config file.", e);
        }
    }

    public Object get(Object... path) {
        return root.getNode(path).getValue();
    }

    public ConfigurationNode getNode(Object... path) {
        return root.getNode(path);
    }

    public CommentedConfigurationNode getRoot() {
        return root;
    }

    public void set(Object value, Object... path) {
        this.root.getNode(path).setValue(value);
    }

    public <T> Optional<T> populate(ObjectMapper<T> mapper) {
        try {
            return Optional.of(mapper.bindToNew().populate(root));
        } catch (ObjectMappingException e) {
            Rewards.logger().warn("Unable to populate Mapper with config.", e);
        }

        return Optional.empty();
    }

    public <T> Optional<T> repopulate(ObjectMapper<T> mapper, T object) {
        this.reload();
        try {
            return Optional.of(mapper.bind(object).populate(root));
        } catch (ObjectMappingException e) {
            Rewards.logger().warn("Unable to repopulate Mapper with config.", e);
        }

        return Optional.empty();
    }

    public <T> void serialize(ObjectMapper<T> mapper, T object) {
        try {
            mapper.bind(object).serialize(root);
        } catch (ObjectMappingException e) {
            Rewards.logger().warn("Unable to serialize Mapper into config.", e);
        }
    }

    public void reload() {
        try {
            root = loader.load();
        } catch (IOException e) {
            Rewards.logger().error("Unable to reload config.", e);
        }
    }

    public void save() {
        try {
            loader.save(root);
        } catch (IOException e) {
            Rewards.logger().error("Unable to save config.", e);
        }
    }
}
