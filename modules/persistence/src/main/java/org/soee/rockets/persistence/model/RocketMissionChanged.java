package org.soee.rockets.persistence.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("RocketMissionChanged")
public class RocketMissionChanged extends RocketMessage {

    @Basic
    @Column(name="mission")
    private String mission;

    public String getMission() {
        return mission;
    }

    public RocketMissionChanged setMission(String mission) {
        this.mission = mission;
        return this;
    }
}
