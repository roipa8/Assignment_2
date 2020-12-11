package bgu.spl.mics.application.services;

import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.BombDestroyerEvent;
import bgu.spl.mics.application.messages.TerminationBroadcast;
import bgu.spl.mics.application.passiveObjects.Diary;

/**
 * LandoMicroservice
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class LandoMicroservice  extends MicroService {
    private long duration;
    public LandoMicroservice(long duration) {
        super("Lando");
        this.duration = duration;
    }

    @Override
    protected void initialize() {
//        register(this);
//        BombDestroyerEvent bombDestroyerEvent=new BombDestroyerEvent(duration);
        subscribeEvent(BombDestroyerEvent.class, (BombDestroyerEvent bombDestroyerEvent)->{
            try {
                Thread.sleep(duration);
            }
            catch (InterruptedException e){
            }
            TerminationBroadcast terminationBroadcast=new TerminationBroadcast();
            sendBroadcast(terminationBroadcast);
        });
        subscribeBroadcast(TerminationBroadcast.class,(TerminationBroadcast terminationBroadcast) -> {
            terminate();
            Diary diary=Diary.getInstance();
            diary.setLandoTerminate(System.currentTimeMillis());
        });

    }
}
