package bgu.spl.mics;

import bgu.spl.mics.application.messages.AttackEvent;
import bgu.spl.mics.application.messages.testEvent;
import bgu.spl.mics.application.messages.testBroadcast;
import bgu.spl.mics.application.services.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MessageBusImplTest {
    MessageBus messagebus;

    @Before
    public void setUp() throws Exception {
        messagebus=new MessageBusImpl();
    }

    @After
    public void tearDown() throws Exception {
        messagebus=null;
    }

    @Test
    public void subscribeEvent() {
    }

    @Test
    public void subscribeBroadcast() {
    }

    @Test
    public void complete() throws InterruptedException {
        testEvent e=new testEvent(2);
        MicroService m1=new TestMicroService();
        MicroService m2=new TestMicroService();
        messagebus.register(m1);
        messagebus.register(m2);
//        messagebus.subscribeEvent(testEvent.class,m1);
        messagebus.subscribeEvent(testEvent.class,m2);
        Future<Integer> f=m1.sendEvent(e);
        Message msg=messagebus.awaitMessage(m2);
        m2.complete((Event<Integer>)msg,2);
        int result=f.get();
        assertEquals(2,result);

    }

    @Test
    public void sendBroadcast() throws InterruptedException {
        Broadcast e = new testBroadcast(2);
        TestMicroService m1 = new TestMicroService();
        TestMicroService m2 = new TestMicroService();
        TestMicroService m3=new TestMicroService();
        messagebus.register(m1);
        messagebus.register(m2);
        messagebus.register(m3);
        m2.subscribeBroadcast(testBroadcast.class,null);
        m3.subscribeBroadcast(testBroadcast.class,null);
        m1.sendBroadcast(e);
        Message msg1=messagebus.awaitMessage(m2);
        Message msg2=messagebus.awaitMessage(m3);
        assertEquals(e,msg1);
        assertEquals(e,msg2);
    }

    @Test
    public void sendEvent() throws InterruptedException {
        Event<Integer> e = new testEvent(2);
        TestMicroService m1 = new TestMicroService();
        TestMicroService m2 = new TestMicroService();
        messagebus.register(m1);
        messagebus.register(m2);
        m2.subscribeEvent(testEvent.class,null);
        Future<Integer> f = m1.sendEvent(e);
        Message msg = messagebus.awaitMessage(m2);
        assertEquals((Event<Integer>) msg,e);
    }

    @Test
    public void register() {
    }

    @Test
    public void unregister() {
    }

    @Test
    public void awaitMessage() throws InterruptedException {
        Event<Integer> e = new testEvent(2);
        TestMicroService m1 = new TestMicroService();
        TestMicroService m2 = new TestMicroService();
        messagebus.register(m1);
        messagebus.register(m2);
        m2.subscribeEvent(testEvent.class,null);
        m1.sendEvent(e);
        Message msg = messagebus.awaitMessage(m2);
        assertEquals(msg, e);
    }
}