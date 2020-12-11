package bgu.spl.mics.application.services;

import java.util.ArrayList;
import java.util.List;

import bgu.spl.mics.Event;
import bgu.spl.mics.Future;
import bgu.spl.mics.MessageBusImpl;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.AttackEvent;
import bgu.spl.mics.application.messages.BombDestroyerEvent;
import bgu.spl.mics.application.messages.DeactivationEvent;
import bgu.spl.mics.application.messages.TerminationBroadcast;
import bgu.spl.mics.application.passiveObjects.Attack;
import bgu.spl.mics.application.passiveObjects.Diary;

/**
 * LeiaMicroservices Initialized with Attack objects, and sends them as  {@link AttackEvents}.
 * This class may not hold references for objects which it is not responsible for:
 * {@link AttackEvents}.
 * <p>
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class LeiaMicroservice extends MicroService {
    private Attack[] attacks;

    public LeiaMicroservice(Attack[] attacks) {
        super("Leia");
        this.attacks = attacks;
    }
    public Attack[] getAttacks(){
        return attacks;
    }

    @Override
    protected void initialize() throws InterruptedException {
        Thread.sleep(100);
//        register(this);
        List<Future<?>> futureAttackList=new ArrayList<>();
        for (int i = 0; i < attacks.length; i++) {
            AttackEvent attackEvent = new AttackEvent(attacks[i].getDuration(), attacks[i].getSerials());
            Future <?> future = sendEvent(attackEvent);
            futureAttackList.add(future);
        }
        for(Future<?> future: futureAttackList){
            if(!future.isDone()){
                future.get();
            }
        }
        DeactivationEvent deactivationEvent=new DeactivationEvent();
        Future<?> future= sendEvent(deactivationEvent);
        future.get();
        BombDestroyerEvent bombDestroyerEvent=new BombDestroyerEvent();
        Future<?> future2= sendEvent(deactivationEvent);
        subscribeBroadcast(TerminationBroadcast.class,(TerminationBroadcast terminationBroadcast) -> {
            terminate();
            Diary diary=Diary.getInstance();
            diary.setLeiaTerminate(System.currentTimeMillis());
        });

    }


}
