package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;

public class DeactivationEvent implements Event {
    long duration;
    public DeactivationEvent(long duration){
        this.duration=duration;
    }
}
