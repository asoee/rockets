package org.soee.rockets.persistence.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("RocketExploded")
public class RocketExploded extends RocketMessage {

    @Basic
    @Column(name="reason")
    private String reason;

    public String getReason() {
        return reason;
    }

    public RocketExploded setReason(String reason) {
        this.reason = reason;
        return this;
    }
}
