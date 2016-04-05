package net.stomdesignsoftware.rewards;

import com.google.inject.Inject;
import net.stomdesignsoftware.rewards.impl.reward.CommandReward;
import net.stomdesignsoftware.rewards.impl.reward.GroupReward;
import net.stomdesignsoftware.rewards.impl.reward.MessageReward;
import net.stomdesignsoftware.rewards.impl.reward.PermissionReward;
import net.stomdesignsoftware.rewards.impl.test.PlaytimeTest;
import net.stomdesignsoftware.rewards.impl.trigger.MobKillTrigger;
import net.stomdesignsoftware.rewards.reward.RewardManager;
import net.stomdesignsoftware.rewards.util.Config;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameConstructionEvent;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GameLoadCompleteEvent;
import org.spongepowered.api.plugin.Plugin;

import java.nio.file.Path;


//TODO Debug for Rewards
//TODO Figure out a way to reward players once
@Plugin(id = "stormdesignsoftware.rewards", name = "Rewards", version = "1.0.0", authors = {"tacticalsk8er"})
public class Rewards {

    public static Rewards instance;

    @ConfigDir(sharedRoot = false) @Inject Path configDir;
    @Inject private Logger logger;

    private Config settingsConfig;
    private Config rewardsConfig;
    private Settings settings;
    private RewardManager rewardManager;

    public static Logger logger() {
        return instance.logger;
    }

    public static void debug(String format, Object... args) {
        if(instance.settings.DEBUG)
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

        debug("Initializing Reward Manager");
        //Init Reward Manager
        this.rewardManager = new RewardManager();

        debug("Registering default rewards");
        //Register Rewards
        this.rewardManager.registerReward("group", GroupReward.class);
        this.rewardManager.registerReward("permission", PermissionReward.class);
        this.rewardManager.registerReward("message", MessageReward.class);
        this.rewardManager.registerReward("hotbarmessage", MessageReward.class);
        this.rewardManager.registerReward("command", CommandReward.class);

        debug("Registering default tests");
        //Register Tests
        this.rewardManager.registerTest("playtime", PlaytimeTest.class);

        debug("Registering default triggers");
        //Register Trigger
        this.rewardManager.registerTrigger("mobkill", MobKillTrigger.class);
        debug("Initialization complete");
    }

    @Listener public void onLoad(GameLoadCompleteEvent event) {
        debug("Loading Rewards...");
        //Start Reward Manager
        this.rewardManager.loadConfig(rewardsConfig.getRoot());
        this.rewardManager.submit(this, settings.INTERVAL);
        debug("Rewards loaded: {}", rewardManager);
    }

    public Path getConfigDir() {
        return configDir;
    }

    public Settings getSettings() {
        return settings;
    }

    public RewardManager getRewardManager() {
        return rewardManager;
    }

    public void reload() {
        this.rewardManager.cancel();
        this.rewardsConfig.reload();
        this.settingsConfig.reload();
        this.settings = settingsConfig.repopulate(Settings.MAPPER, settings).orElseGet(() -> {
            Rewards.logger().error("Unable to repopulate Settings. Going to defaults.");
            return new Settings();
        });
        this.rewardManager.loadConfig(rewardsConfig.getRoot());
        this.rewardManager.submit(this, settings.INTERVAL);
    }
}
