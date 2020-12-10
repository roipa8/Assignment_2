package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;

public class BombDestroyerEvent implements Event <Boolean> {
    long duration;
    public BombDestroyerEvent(long duration){
        this.duration=duration;
    }
}
