package org.soee.rockets.rest.rockets;

import java.time.Instant;

public class JsonRocketState {

    private String channel;
    private Instant launchTime;
    private String type;
    private long speed = 0;
    private String mission;
    private boolean exploded = false;
    private String explosionReason;

    public Instant getLaunchTime() {
        return launchTime;
    }

    public String getChannel() {
        return channel;
    }

    public JsonRocketState setChannel(String channel) {
        this.channel = channel;
        return this;
    }

    public JsonRocketState setLaunchTime(Instant launchTime) {
        this.launchTime = launchTime;
        return this;
    }

    public String getType() {
        return type;
    }

    public JsonRocketState setType(String type) {
        this.type = type;
        return this;
    }

    public long getSpeed() {
        return speed;
    }

    public JsonRocketState setSpeed(long speed) {
        this.speed = speed;
        return this;
    }

    public String getMission() {
        return mission;
    }

    public JsonRocketState setMission(String mission) {
        this.mission = mission;
        return this;
    }

    public boolean isExploded() {
        return exploded;
    }

    public JsonRocketState setExploded(boolean exploded) {
        this.exploded = exploded;
        return this;
    }

    public JsonRocketState setExplosionReason(String explosionReason) {
        this.explosionReason = explosionReason;
        return this;
    }

    public String getExplosionReason() {
        return explosionReason;
    }
}
