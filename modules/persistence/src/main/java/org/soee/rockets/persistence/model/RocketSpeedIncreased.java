package org.soee.rockets.persistence.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("RocketSpeedIncreased")
public class RocketSpeedIncreased extends RocketMessage {

    @Basic
    @Column(name="speed_change")
    private long speedChange;

    public long getSpeedChange() {
        return speedChange;
    }

    public RocketSpeedIncreased setSpeedChange(long speedChange) {
        this.speedChange = speedChange;
        return this;
    }
}
