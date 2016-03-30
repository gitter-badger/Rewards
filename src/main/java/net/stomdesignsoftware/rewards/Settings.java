package net.stomdesignsoftware.rewards;

import ninja.leaping.configurate.objectmapping.ObjectMapper;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

public class Settings {

    public static final ObjectMapper<Settings> MAPPER;

    static {
        try {
            MAPPER = ObjectMapper.forClass(Settings.class);
        } catch (ObjectMappingException e) {
            throw new ExceptionInInitializerError("Unable to initialize Mapper for Settings.");
        }
    }

}
