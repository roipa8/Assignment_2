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
 * LeiaMicroservices Initialized with Attack objects, and sends them as  {@link AttackEvent}.
 * This class may not hold references for objects which it is not responsible for:
 * {@link AttackEvent}.
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

    @Override
    protected void initialize() throws InterruptedException {
        subscribeBroadcast(TerminationBroadcast.class,(TerminationBroadcast terminationBroadcast) -> {
            terminate();
            Diary diary=Diary.getInstance();
            diary.setLeiaTerminate(System.currentTimeMillis());
            System.out.println("Leia Time:"+System.currentTimeMillis());
        });
        Thread.sleep(10);
//        register(this);
        List<Future<Boolean>> futureAttackList=new ArrayList<>();
        for (int i = 0; i < attacks.length; i++) {
            AttackEvent attackEvent = new AttackEvent(attacks[i].getDuration(), attacks[i].getSerials());
            Future <Boolean> future = sendEvent(attackEvent);
            futureAttackList.add(future);
        }
        int numOfResolvedFutures=0;
        boolean moveToDeactivation=false;
        while (!moveToDeactivation){
            if(futureAttackList.get(numOfResolvedFutures).get()){
                numOfResolvedFutures=numOfResolvedFutures+1;
            }
            if(numOfResolvedFutures==futureAttackList.size()){
                moveToDeactivation=true;
            }
        }
//        for(Future<?> future: futureAttackList){
//            if(!future.isDone()){
//                future.get();
//            }
//        }
        Future<Boolean> future= sendEvent(new DeactivationEvent());
        if(future.get()){
            sendEvent(new BombDestroyerEvent());
        }
//        future.get();
//        BombDestroyerEvent bombDestroyerEvent=new BombDestroyerEvent();
//        Future<?> future2= sendEvent(bombDestroyerEvent);
//        future2.get();

    }


}
