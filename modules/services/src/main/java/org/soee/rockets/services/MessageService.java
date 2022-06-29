package org.soee.rockets.services;

import org.soee.rockets.persistence.model.*;
import org.soee.rockets.persistence.repository.RocketMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Handles receiving of rocket messages
 * if a message already exists, it will return silently.
 */
@Service
@Transactional
public class MessageService {

    private final RocketMessageRepository messageRepository;

    @Autowired
    public MessageService(RocketMessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public boolean messageExists(MessageMetadata messageMetadata) {
        return messageRepository.existsByChannelAndSequenceNumber(
                messageMetadata.getChannel(),
                messageMetadata.getSequenceNumber());
    }

    public void rocketLaunched(
            MessageMetadata messageMetadata,
            String rocketType,
            String mission,
            long launchSpeed
    ) {
        if (messageExists(messageMetadata)) {
            return;
        }
        RocketLaunched rocketLaunched = new RocketLaunched()
                .setRocketType(rocketType)
                .setLaunchSpeed(launchSpeed)
                .setMission(mission);
        setMetadata(messageMetadata, rocketLaunched);
        messageRepository.save(rocketLaunched);
    }

    public void rocketSpeedIncreased(
            MessageMetadata messageMetadata,
            long speedChange
    ) {
        if (messageExists(messageMetadata)) {
            return;
        }
        RocketSpeedIncreased rocketSpeedIncreased = new RocketSpeedIncreased()
                .setSpeedChange(speedChange);
        setMetadata(messageMetadata, rocketSpeedIncreased );
        messageRepository.save(rocketSpeedIncreased);

    }

    public void rocketSpeedDecreased(
            MessageMetadata messageMetadata,
            long speedChange
    ) {
        if (messageExists(messageMetadata)) {
            return;
        }
        RocketSpeedDecreased rocketSpeedDecreased = new RocketSpeedDecreased()
                .setSpeedChange(speedChange);
        setMetadata(messageMetadata, rocketSpeedDecreased );
        messageRepository.save(rocketSpeedDecreased);
    }

    private RocketMessage setMetadata(MessageMetadata messageMetadata, RocketMessage message) {
        return message
                .setChannel(messageMetadata.getChannel())
                .setMessageTimestamp(messageMetadata.getTimestamp())
                .setSequenceNumber(messageMetadata.getSequenceNumber());
    }

    public void rocketExploded(MessageMetadata messageMetadata, String reason) {
        if (messageExists(messageMetadata)) {
            return;
        }
        RocketExploded rocketExploded = new RocketExploded()
                .setReason(reason);
        setMetadata(messageMetadata, rocketExploded );
        messageRepository.save(rocketExploded);
    }

    public void rocketMissionChanged(MessageMetadata messageMetadata, String newMission) {
        if (messageExists(messageMetadata)) {
            return;
        }
        RocketMissionChanged rocketMissionChanged = new RocketMissionChanged()
                .setMission(newMission);
        setMetadata(messageMetadata, rocketMissionChanged );
        messageRepository.save(rocketMissionChanged);
    }
}
