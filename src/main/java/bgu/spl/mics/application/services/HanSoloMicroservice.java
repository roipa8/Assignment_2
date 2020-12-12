package bgu.spl.mics.application.services;


import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.AttackEvent;
import bgu.spl.mics.application.messages.TerminationBroadcast;
import bgu.spl.mics.application.passiveObjects.Diary;
import bgu.spl.mics.application.passiveObjects.Ewoks;

///**
// * HanSoloMicroservices is in charge of the handling {@link AttackEvents}.
// * This class may not hold references for objects which it is not responsible for:
// * {@link AttackEvents}.
// *
// * You can add private fields and public methods to this class.
// * You MAY change constructor signatures and even add new public constructors.
// */
public class HanSoloMicroservice extends MicroService {

    public HanSoloMicroservice() {
        super("Han");
    }


    @Override
    protected void initialize() {
        subscribeEvent(AttackEvent.class, (AttackEvent event)->{
            Ewoks ewoks = Ewoks.getInstance();
            ewoks.getResources(event.getSerials());
            Thread.sleep(event.getDuration());
            complete(event, true);
            Diary diary=Diary.getInstance();
            diary.setHanSoloFinish(System.currentTimeMillis());
            ewoks.releaseResources(event.getSerials());
            if(ewoks.getCount()==ewoks.getTotalAttacks()){
                diary.setTotalAttacks(ewoks.getCount());
                System.out.println("total attacks ->"+ewoks.getCount());
            }
        });
        subscribeBroadcast(TerminationBroadcast.class,(TerminationBroadcast terminationBroadcast) -> {
            terminate();
            Diary diary=Diary.getInstance();
            diary.setHanSoloTerminate(System.currentTimeMillis());
            System.out.println("Han Solo Time:"+System.currentTimeMillis());
        });
    }




}
