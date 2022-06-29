package org.soee.rockets.services;

import java.time.Instant;

public class MessageMetadata {

    private String channel;
    private Instant timestamp;
    private int sequenceNumber;

    private String messageType;

    public String getChannel() {
        return channel;
    }

    public MessageMetadata setChannel(String channel) {
        this.channel = channel;
        return this;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public MessageMetadata setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public MessageMetadata setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
        return this;
    }

    public String getMessageType() {
        return messageType;
    }

    public MessageMetadata setMessageType(String messageType) {
        this.messageType = messageType;
        return this;
    }
}
