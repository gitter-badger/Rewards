package net.stomdesignsoftware.rewards.data;

import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.persistence.DataBuilder;
import org.spongepowered.api.data.persistence.InvalidDataException;

import java.util.Optional;

/**
 * Created by tacticalsk8er on 4/4/16.
 */
public class RewardDataBuilder implements DataBuilder<RewardData> {
    @Override
    public Optional<RewardData> build(DataView container) throws InvalidDataException {
        return null;
    }

    @Override
    public DataBuilder<RewardData> reset() {
        return null;
    }

    @Override
    public DataBuilder<RewardData> from(RewardData value) {
        return null;
    }
}
