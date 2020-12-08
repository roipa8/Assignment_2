package bgu.spl.mics.application.messages;
import bgu.spl.mics.Event;
import bgu.spl.mics.Future;

public class AttackEvent implements Event<Boolean> {
	public int duration;
	public int[] serial;
	private Future <Boolean> future;
	public AttackEvent(int duration,int[] serial){
	    this.duration = duration;
	    this.serial = serial;
    }
    public void setFuture(Future<Boolean> future){
		this.future=future;
	}
    public Future<Boolean> getFuture(){
		return future;
	}
}
