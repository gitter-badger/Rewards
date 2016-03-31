package net.stomdesignsoftware.rewards;

import ninja.leaping.configurate.objectmapping.ObjectMapper;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.Setting;

public class Settings {

    public static final ObjectMapper<Settings> MAPPER;

    static {
        try {
            MAPPER = ObjectMapper.forClass(Settings.class);
        } catch (ObjectMappingException e) {
            throw new ExceptionInInitializerError("Unable to initialize Mapper for Settings.");
        }
    }


    @Setting(value = "Interval", comment = "The amount of minutes it takes to check a normal reward.")
    public long INTERVAL = 5;

    @Setting(value = "Debug", comment = "Show debug messages.")
    public boolean DEBUG = false;
}
