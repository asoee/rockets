package org.soee.rockets.persistence.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("RocketLaunched")
public class RocketLaunched extends RocketMessage {

    @Basic
    @Column(name="rocket_type")
    private String rocketType;

    @Basic
    @Column(name="launch_speed")
    private long launchSpeed;

    @Basic
    @Column(name="mission")
    private String mission;

    public String getRocketType() {
        return rocketType;
    }

    public RocketLaunched setRocketType(String rocketType) {
        this.rocketType = rocketType;
        return this;
    }

    public long getLaunchSpeed() {
        return launchSpeed;
    }

    public RocketLaunched setLaunchSpeed(long launchSpeed) {
        this.launchSpeed = launchSpeed;
        return this;
    }

    public String getMission() {
        return mission;
    }

    public RocketLaunched setMission(String mission) {
        this.mission = mission;
        return this;
    }
}
