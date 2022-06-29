package org.soee.rockets.services;

import org.soee.rockets.persistence.model.*;
import org.soee.rockets.persistence.repository.RocketMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Exposes state information about rockets, by consolidating messages on the fly.
 */
@Service
@Transactional
public class RocketService {

    private final RocketMessageRepository messageRepository;

    @Autowired
    public RocketService(RocketMessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    /**
     * Lists all channel ids of known rockets
     */
    public List<String> findAllChannels() {
        return messageRepository.findDistinctChannelIds();
    }

    /**
     * get the current state of a specific rocket, based on channel id.
     * The state is consolidated based on the known messages from that channel
     */
    public RocketState getRocketState(String channelId) {
        List<RocketMessage> messages = messageRepository.findByChannelOrderBySequenceNumber(channelId);

        RocketState rocketState = new RocketState();
        for (RocketMessage message : messages) {
            updateRocketState(rocketState, message);
        }
        return rocketState;

    }

    private void updateRocketState(RocketState rocketState, RocketMessage message) {
        if (message instanceof RocketLaunched launched) {
            applyRocketLaunched(rocketState, launched);
        } else if (message instanceof RocketSpeedDecreased decreased) {
            applyRocketSpeedDecreased(rocketState, decreased);
        } else if (message instanceof RocketSpeedIncreased increased) {
            applyRocketSpeedIncreased(rocketState, increased);
        } else if (message instanceof RocketMissionChanged missionChanged) {
            applyRocketMissionChanged(rocketState, missionChanged);
        } else if (message instanceof RocketExploded exploded) {
            applyRocketExploded(rocketState, exploded);
        }
    }

    private void applyRocketExploded(RocketState rocketState, RocketExploded exploded) {
        rocketState.setExploded(true);
        rocketState.setExplosionReason(exploded.getReason());
    }

    private void applyRocketMissionChanged(RocketState rocketState, RocketMissionChanged missionChanged) {
        rocketState.setMission(missionChanged.getMission());
    }

    private void applyRocketSpeedIncreased(RocketState rocketState, RocketSpeedIncreased increased) {
        rocketState.setSpeed(rocketState.getSpeed() + increased.getSpeedChange());

    }

    private void applyRocketSpeedDecreased(RocketState rocketState, RocketSpeedDecreased decreased) {
        rocketState.setSpeed(rocketState.getSpeed() - decreased.getSpeedChange());
    }

    private void applyRocketLaunched(RocketState rocketState, RocketLaunched message) {
        rocketState.setLaunchTime(message.getMessageTimestamp());
        rocketState.setMission(message.getMission());
        rocketState.setChannel(message.getChannel());
        rocketState.setSpeed(message.getLaunchSpeed());
        rocketState.setType(message.getRocketType());

    }


}
