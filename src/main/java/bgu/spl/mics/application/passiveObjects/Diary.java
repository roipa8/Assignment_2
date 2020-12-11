package bgu.spl.mics.application.passiveObjects;


/**
 * Passive data-object representing a Diary - in which the flow of the battle is recorded.
 * We are going to compare your recordings with the expected recordings, and make sure that your output makes sense.
 * <p>
 * Do not add to this class nothing but a single constructor, getters and setters.
 */
public class Diary {
    private int totalAttacks;
    private long HanSoloFinish;
    private long C3POFinish;
    private long R2D2Deactivate;
    private long LeiaTerminate;
    private long HanSoloTerminate;
    private long C3PoTerminate;
    private long R2D2Terminate;
    private long LandoTerminate;
    private static class SingletonHolder{
        private static Diary instance =new Diary();
    }
    private Diary(){}
    public static Diary getInstance(){
        return SingletonHolder.instance;
    }
    public void setR2D2Deactivate(long R2D2Deactivate){
        this.R2D2Deactivate=R2D2Deactivate;
    }
    public void setC3POFinish(long c3POFinish) {
        C3POFinish = c3POFinish;
    }
    public void setHanSoloFinish(long hanSoloFinish) {
        HanSoloFinish = hanSoloFinish;
    }

    public void setC3PoTerminate(long c3PoTerminate) {
        C3PoTerminate = c3PoTerminate;
    }

    public void setHanSoloTerminate(long hanSoloTerminate) {
        HanSoloTerminate = hanSoloTerminate;
    }

    public void setLandoTerminate(long landoTerminate) {
        LandoTerminate = landoTerminate;
    }

    public void setLeiaTerminate(long leiaTerminate) {
        LeiaTerminate = leiaTerminate;
    }

    public void setR2D2Terminate(long r2D2Terminate) {
        R2D2Terminate = r2D2Terminate;
    }

    public void setTotalAttacks(int totalAttacks) {
        this.totalAttacks = totalAttacks;
    }
}
