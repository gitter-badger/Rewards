package net.stomdesignsoftware.rewards.data;

import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableMappedData;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableSingleData;
import org.spongepowered.api.data.value.BaseValue;
import org.spongepowered.api.data.value.immutable.ImmutableValue;

import java.util.Map;

public class ImmutableRewardDataManipulator extends AbstractImmutableMappedData<String, Integer, ImmutableRewardDataManipulator, RewardDataManipulator> {

    protected ImmutableRewardDataManipulator(Map<String, Integer> value, Key<? extends BaseValue<Map<String, Integer>>> usedKey) {
        super(value, usedKey);
    }

    @Override
    public RewardDataManipulator asMutable() {
        return null;
    }

    @Override
    public int getContentVersion() {
        return 0;
    }
}
