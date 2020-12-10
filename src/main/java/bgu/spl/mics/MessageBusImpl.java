package bgu.spl.mics;

import bgu.spl.mics.application.messages.AttackEvent;
import bgu.spl.mics.application.services.LeiaMicroservice;
import bgu.spl.mics.application.services.TestMicroService;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;


/**
 * The {@link MessageBusImpl class is the implementation of the MessageBus interface.
 * Write your implementation here!
 * Only private fields and methods can be added to this class.
 */
public class MessageBusImpl implements MessageBus {
	private HashMap<String, LinkedBlockingQueue> HMQueue;
	private HashMap<Class<? extends Message>, LinkedList<String>> HMType;


	private static class SingletonHolder{
		private static MessageBusImpl instance =new MessageBusImpl();
	}

	private MessageBusImpl(){
		HMQueue=new HashMap<>();
		HMType=new HashMap<>();
	}

	public static MessageBusImpl getInstance(){
		return SingletonHolder.instance;
	}
	
	
	@Override
	public <T> void subscribeEvent(Class<? extends Event<T>> type, MicroService m) {
		if(HMType.containsKey(type)){
			LinkedList<String> list=HMType.get(type);
			list.add(m.getName());
		}
		else{
			LinkedList<String> list=new LinkedList<>();
			list.add(m.getName());
			HMType.put(type,list);
		}

	}

	@Override
	public void subscribeBroadcast(Class<? extends Broadcast> type, MicroService m) {
		if(HMType.containsKey(type)){
			LinkedList<String> list=HMType.get(type);
			list.add(m.getName());
		}
		else{
			LinkedList<String> list=new LinkedList<>();
			list.add(m.getName());
			HMType.put(type,list);
		}

    }

	@Override
	public <T> void complete(Event<T> e, T result) {

	}

	@Override
	public void sendBroadcast(Broadcast b) {
		
	}

	
	@Override
	public <T> Future<T> sendEvent(Event<T> e) {
		Future<T> future;
        return null;
	}

	@Override
	public void register(MicroService m) {
		LinkedBlockingQueue q = new LinkedBlockingQueue();
		HMQueue.put(m.getName(),q);
	}

	@Override
	public void unregister(MicroService m) {
		
	}

	@Override
	public Message awaitMessage(MicroService m) throws InterruptedException {
		return null;
	}
}
