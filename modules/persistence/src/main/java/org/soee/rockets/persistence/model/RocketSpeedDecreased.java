package org.soee.rockets.persistence.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("RocketSpeedDecreased")
public class RocketSpeedDecreased extends RocketMessage {

    @Basic
    @Column(name="speed_change")
    private long speedChange;

    public long getSpeedChange() {
        return speedChange;
    }

    public RocketSpeedDecreased setSpeedChange(long speedChange) {
        this.speedChange = speedChange;
        return this;
    }
}
