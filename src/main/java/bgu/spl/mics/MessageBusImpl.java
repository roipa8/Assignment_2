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
	private HashMap<String, LinkedBlockingQueue<Message>> HMQueue;
	private HashMap<Class<? extends Message>, LinkedList<String>> HMType;
	private HashMap<Class<? extends Event>, Future> HMFuture;



	private static class SingletonHolder{
		private static MessageBusImpl instance =new MessageBusImpl();
	}

	private MessageBusImpl(){
		HMQueue=new HashMap<>();
		HMType=new HashMap<>();
		HMFuture=new HashMap<>();
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
		HMFuture.get(e).resolve(result);

	}

	@Override
	public void sendBroadcast(Broadcast b) {
		if(HMType.containsKey(b.getClass())){
			if(HMType.get(b.getClass()).isEmpty()){
				throw new NullPointerException("No micros-service has subscribed to "+b.getClass().getName());
			}
			LinkedList<String> list=HMType.get(b.getClass());
			for(String s: list){
				if(!HMQueue.containsKey(s)){
					throw new NullPointerException("Micro-service "+s+" didn't register");
				}
				LinkedBlockingQueue <Message> queue=HMQueue.get(s);
				queue.add(b);
			}
		}

	}

	
	@Override
	public synchronized  <T> Future<T> sendEvent(Event<T> e) {
		Future<T> future=new Future<>();
		if(HMType.containsKey(e.getClass())){
			if(HMType.get(e.getClass()).isEmpty()){
				throw new NullPointerException("No micros-service has subscribed to "+e.getClass().getName());
			}
			LinkedList<String> list = HMType.get(e.getClass());
			String temp=list.get(0);
			list.removeFirst();
			if(!HMQueue.containsKey(temp)){
				throw new NullPointerException("Micro-service "+temp+" didn't register");
			}
//			HMQueue.putIfAbsent(temp,new LinkedBlockingQueue<>());
			LinkedBlockingQueue <Message> queue=HMQueue.get(temp);
			queue.add(e);
			list.addLast(temp);
			HMFuture.put(e.getClass(),future);
			notifyAll();
		}
        return future;
	}

	@Override
	public void register(MicroService m) {
		LinkedBlockingQueue <Message> q = new LinkedBlockingQueue<>();
		HMQueue.put(m.getName(),q);
	}

	@Override
	public void unregister(MicroService m) {
		HMQueue.remove(m.getName());
	}

	@Override
	public synchronized Message awaitMessage(MicroService m) throws InterruptedException {
		Message msg=null;
		if(HMQueue.containsKey(m.getName())){
			LinkedBlockingQueue<Message> q = HMQueue.get(m.getName());
			while (q.isEmpty()){
				wait();
			}
			msg=q.remove();
		}
		return msg;
	}
}
