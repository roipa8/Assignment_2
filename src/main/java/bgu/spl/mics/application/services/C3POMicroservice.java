package bgu.spl.mics.application.services;

import bgu.spl.mics.Callback;
import bgu.spl.mics.Message;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.AttackEvent;
import bgu.spl.mics.application.messages.TerminationBroadcast;
import bgu.spl.mics.application.passiveObjects.Diary;
import bgu.spl.mics.application.passiveObjects.Ewok;
import bgu.spl.mics.application.passiveObjects.Ewoks;
import org.w3c.dom.events.Event;

import java.util.HashMap;
import java.util.Queue;


///**
// * C3POMicroservices is in charge of the handling {@link AttackEvents}.
// * This class may not hold references for objects which it is not responsible for:
// * {@link AttackEvents}.
// *
// * You can add private fields and public methods to this class.
// * You MAY change constructor signatures and even add new public constructors.
// */
public class C3POMicroservice extends MicroService {
    public C3POMicroservice() {
        super("C3PO");
    }


    @Override
    protected void initialize() {
//        register(this);
        subscribeEvent(AttackEvent.class, (AttackEvent event) -> {
            Ewoks ewoks = Ewoks.getInstance();
            int count = 0;
            for (int i = 0; i < event.getSerials().size(); i++) {
                while (!ewoks.getEwoksArr()[event.getSerials().get(i)].isAvailable()) {
                    wait();
                    ewoks.getEwoksArr()[event.getSerials().get(i)].acquire();
                }
                try {
                    Thread.sleep(event.getDuration());
                } catch (InterruptedException e) {
                }
            }
            complete(event, true);

            for (int i = 0; i < event.getSerials().size(); i++) {
                ewoks.getEwoksArr()[event.getSerials().get(i)].release();
            }
            notifyAll();
        });
        subscribeBroadcast(TerminationBroadcast.class,(TerminationBroadcast terminationBroadcast) -> {
            terminate();
            Diary diary=Diary.getInstance();
            diary.setC3PoTerminate(System.currentTimeMillis());
        });
    }
}


