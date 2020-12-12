package bgu.spl.mics.application.passiveObjects;


import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Passive object representing the resource manager.
 * <p>
 * This class must be implemented as a thread-safe singleton.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You can add ONLY private methods and fields to this class.
 */

public class Ewoks {
    private Ewok[] EwoksArr;
    private AtomicInteger count;
    private int TotalAttacks;
    private static Ewoks instance = null;
    private static class SingletonHolder{
        private static Ewoks instance =new Ewoks();
    }
    private Ewoks(){
        count=new AtomicInteger();
    }
    public void initialize(int ewoksSize){
        EwoksArr=new Ewok[ewoksSize];
        for(int i=0; i<ewoksSize; i++){
            EwoksArr[i]=new Ewok(i+1,true);
        }
    }
    public Ewok[] getEwoksArr(){
        return EwoksArr;
    }


//    public synchronized void getResources(List<Integer> list){
//
//    }
    public void getResources(List<Integer> list) throws InterruptedException {
        Collections.sort(list);
        for(int i = 0;i<list.size();i++){
            EwoksArr[list.get(i)-1].acquire();
            count.incrementAndGet();
        }
    }

    public int getCount(){
        return count.get();
    }

    public void releaseResources(List<Integer> list){
        for(int i=0; i<list.size(); i++){
            EwoksArr[list.get(i)-1].release();
        }

    }

    public int getTotalAttacks() {
        return TotalAttacks;
    }

    public void setTotalAttacks(int totalAttacks) {
        TotalAttacks = totalAttacks;
    }

    //    public boolean isAvailable(Ewok ewok){
//        return ewok.available;
//    }
//    public int getSerialNumber(Ewok ewok){
//        return ewok.serialNumber;
//    }
    public static Ewoks getInstance(){
        return SingletonHolder.instance;
    }

}
