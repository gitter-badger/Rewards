package net.stomdesignsoftware.rewards.data;

import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.DataManipulatorBuilder;
import org.spongepowered.api.data.persistence.InvalidDataException;

import java.util.Optional;


public class RewardDataManipulatorBuilder implements DataManipulatorBuilder<RewardDataManipulator, ImmutableRewardDataManipulator> {
    @Override
    public RewardDataManipulator create() {
        return null;
    }

    @Override
    public Optional<RewardDataManipulator> createFrom(DataHolder dataHolder) {
        return null;
    }

    @Override
    public Optional<RewardDataManipulator> build(DataView container) throws InvalidDataException {
        return null;
    }
}
