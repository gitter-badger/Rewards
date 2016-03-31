package net.stomdesignsoftware.rewards;

import com.google.inject.Inject;
import net.stomdesignsoftware.rewards.util.Config;
import org.slf4j.Logger;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameConstructionEvent;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.plugin.Plugin;

import java.nio.file.Path;

@Plugin(id = "stormdesignsoftware.rewards", name = "Rewards", version = "1.0.0", authors = {"tacticalsk8er"})
public class Rewards {

    public static Rewards instance;

    @ConfigDir(sharedRoot = false) @Inject Path configDir;
    @Inject private Logger logger;

    private Config settingsConfig;
    private Config rewardsConfig;
    private Settings settings;

    public static Logger logger() {
        return instance.logger;
    }

    public static void debug(String format, Object... args) {
        logger().info(format, args);
    }

    @Listener public void onConstruct(GameConstructionEvent event) {
        instance = this;
    }

    @Listener public void onInit(GameInitializationEvent event) {

        //Init Configs
        this.settingsConfig = new Config(configDir, "settings");
        this.rewardsConfig = new Config(configDir, "rewards");

        //Init Settings
        this.settings = settingsConfig.populate(Settings.MAPPER).orElseGet(() -> {
            Rewards.logger().error("Unable to populate Settings. Going to defaults.");
            return new Settings();
        });
        this.settingsConfig.serialize(Settings.MAPPER, settings);
        this.settingsConfig.save();


    }

    public Path getConfigDir() {
        return configDir;
    }

    public Settings getSettings() {
        return settings;
    }
}
