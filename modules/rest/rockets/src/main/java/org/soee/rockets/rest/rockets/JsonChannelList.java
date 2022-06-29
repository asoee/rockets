package org.soee.rockets.rest.rockets;


import java.util.List;

public class JsonChannelList {
    List<String> channels;

    public JsonChannelList(List<String> channels) {
        this.channels = channels;
    }

    public List<String> getChannels() {
        return channels;
    }

    public JsonChannelList setChannels(List<String> channels) {
        this.channels = channels;
        return this;
    }
}
