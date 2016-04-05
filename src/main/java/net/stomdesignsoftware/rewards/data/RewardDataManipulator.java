package net.stomdesignsoftware.rewards.data;

import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.manipulator.mutable.common.AbstractSingleData;
import org.spongepowered.api.data.merge.MergeFunction;
import org.spongepowered.api.data.value.BaseValue;
import org.spongepowered.api.data.value.mutable.Value;

import java.util.Optional;

public class RewardDataManipulator extends AbstractSingleData<RewardData, RewardDataManipulator, ImmutableRewardDataManipulator> {

    protected RewardDataManipulator(RewardData value, Key<? extends BaseValue<RewardData>> usedKey) {
        super(value, usedKey);
    }

    @Override
    protected Value<?> getValueGetter() {
        return null;
    }

    @Override
    public Optional<RewardDataManipulator> fill(DataHolder dataHolder, MergeFunction overlap) {
        return null;
    }

    @Override
    public Optional<RewardDataManipulator> from(DataContainer container) {
        return null;
    }

    @Override
    public RewardDataManipulator copy() {
        return null;
    }

    @Override
    public ImmutableRewardDataManipulator asImmutable() {
        return null;
    }

    @Override
    public int compareTo(RewardDataManipulator o) {
        return 0;
    }

    @Override
    public int getContentVersion() {
        return 0;
    }
}
