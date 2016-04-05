package net.stomdesignsoftware.rewards.data;

import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.manipulator.mutable.common.AbstractMappedData;
import org.spongepowered.api.data.manipulator.mutable.common.AbstractSingleData;
import org.spongepowered.api.data.merge.MergeFunction;
import org.spongepowered.api.data.value.BaseValue;
import org.spongepowered.api.data.value.mutable.Value;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class RewardDataManipulator extends AbstractMappedData<String, Integer, RewardDataManipulator, ImmutableRewardDataManipulator> {


    protected RewardDataManipulator(Map<String, Integer> value, Key<? extends BaseValue<Map<String, Integer>>> usedKey) {
        super(value, usedKey);
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
    public Optional<Integer> get(String key) {
        return null;
    }

    @Override
    public Set<String> getMapKeys() {
        return null;
    }

    @Override
    public RewardDataManipulator put(String key, Integer value) {
        return null;
    }

    @Override
    public RewardDataManipulator putAll(Map<? extends String, ? extends Integer> map) {
        return null;
    }

    @Override
    public RewardDataManipulator remove(String key) {
        return null;
    }

    @Override
    public int getContentVersion() {
        return 0;
    }
}
