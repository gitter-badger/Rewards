package net.stomdesignsoftware.rewards.data;

import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableSingleData;
import org.spongepowered.api.data.value.BaseValue;
import org.spongepowered.api.data.value.immutable.ImmutableValue;

public class ImmutableRewardDataManipulator extends AbstractImmutableSingleData<RewardData, ImmutableRewardDataManipulator, RewardDataManipulator> {

    protected ImmutableRewardDataManipulator(RewardData value, Key<? extends BaseValue<RewardData>> usedKey) {
        super(value, usedKey);
    }

    @Override
    protected ImmutableValue<?> getValueGetter() {
        return null;
    }

    @Override
    public RewardDataManipulator asMutable() {
        return null;
    }

    @Override
    public int compareTo(ImmutableRewardDataManipulator o) {
        return 0;
    }

    @Override
    public int getContentVersion() {
        return 0;
    }
}
