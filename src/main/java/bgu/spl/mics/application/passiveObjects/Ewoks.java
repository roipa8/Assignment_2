package bgu.spl.mics.application.passiveObjects;


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
    private static Ewoks instance = null;
//    private static class SingletonHolder{
//        private static Ewoks instance =new Ewoks();
//    }
    public Ewoks(int ewoksSize){
        EwoksArr=new Ewok[ewoksSize];
        for(int i=0; i<ewoksSize; i++){
            EwoksArr[i]=new Ewok(i+1,true);
        }
    }
    public Ewok[] getEwoksArr(){
        return EwoksArr;
    }
//    public static Ewoks getInstance(){
//        return SingletonHolder.instance;
//    }

}
