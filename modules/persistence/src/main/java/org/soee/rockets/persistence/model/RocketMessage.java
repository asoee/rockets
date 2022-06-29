package org.soee.rockets.persistence.model;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "message_type")
@Table(name="rocket_message")
public class RocketMessage {

    @Id()
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Basic
    @Column(name="message_timestamp")
    private Instant messageTimestamp;

    @Basic
    @Column(name="channel")
    private String channel;

    @Basic
    @Column(name="sequence_number")
    private int sequenceNumber;

    public Long getId() {
        return id;
    }

    public RocketMessage setId(Long id) {
        this.id = id;
        return this;
    }

    public Instant getMessageTimestamp() {
        return messageTimestamp;
    }

    public RocketMessage setMessageTimestamp(Instant messageTimestamp) {
        this.messageTimestamp = messageTimestamp;
        return this;
    }

    public String getChannel() {
        return channel;
    }

    public RocketMessage setChannel(String channel) {
        this.channel = channel;
        return this;
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public RocketMessage setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
        return this;
    }
}
